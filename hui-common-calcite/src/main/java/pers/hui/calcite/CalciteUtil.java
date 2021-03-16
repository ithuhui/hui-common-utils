package pers.hui.calcite;

import org.apache.calcite.config.CalciteConnectionConfigImpl;
import org.apache.calcite.config.Lex;
import org.apache.calcite.jdbc.CalciteSchema;
import org.apache.calcite.plan.ConventionTraitDef;
import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.plan.hep.HepPlanner;
import org.apache.calcite.plan.hep.HepProgram;
import org.apache.calcite.prepare.CalciteCatalogReader;
import org.apache.calcite.rel.RelDistributionTraitDef;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.RelRoot;
import org.apache.calcite.rel.rel2sql.RelToSqlConverter;
import org.apache.calcite.rel.rel2sql.SqlImplementor;
import org.apache.calcite.rel.rules.CoreRules;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.rel.type.RelDataTypeSystem;
import org.apache.calcite.rel.type.RelDataTypeSystemImpl;
import org.apache.calcite.rex.RexBuilder;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.dialect.OracleSqlDialect;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.type.BasicSqlType;
import org.apache.calcite.sql.type.SqlTypeFactoryImpl;
import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.calcite.sql.validate.SqlValidator;
import org.apache.calcite.sql.validate.SqlValidatorUtil;
import org.apache.calcite.sql2rel.SqlToRelConverter;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;

import java.util.List;
import java.util.Properties;

/**
 * <code>SchemaInfo</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/2 15:53.
 *
 * @author Gary.Hu
 */
public class CalciteUtil {

    /**
     * 优化方式
     *
     * @param schemaInfos
     * @return
     */
    public static String optimization(List<SchemaInfo> schemaInfos, String originalSql, Lex lex) throws Exception {
        // 构造schema
        SchemaPlus rootSchema = genSchema(schemaInfos);
        // sql解析器
        SqlParser sqlParser = sqlParserInit(originalSql, lex);
        // sql类型工厂
        SqlTypeFactoryImpl factory = sqlTypeFactoryInit();

        // 构建表达式
        RexBuilder rexBuilder = createRexBuilder(factory);

        SqlNode sqlNode = sqlParser.parseQuery();

        final FrameworkConfig frameworkConfig = Frameworks.newConfigBuilder()
                .parserConfig(SqlParser.Config.DEFAULT)
                .defaultSchema(rootSchema)
                .traitDefs(ConventionTraitDef.INSTANCE, RelDistributionTraitDef.INSTANCE)
                .build();

        // 信息读取
        CalciteCatalogReader calciteCatalogReader = catalogReaderInit(rootSchema, factory);

        // 优化器
        HepPlanner hepPlanner = plannerInit();

        // planner 运行时的环境，保存上下文信息；
        final RelOptCluster cluster = RelOptCluster.create(hepPlanner, rexBuilder);


        // 校验器初始化
        SqlValidator sqlValidator = validatorInit(factory, calciteCatalogReader);

        SqlNode validatedSqlNode = sqlValidator.validate(sqlNode);

        final SqlToRelConverter.Config config = frameworkConfig.getSqlToRelConverterConfig();

        final SqlToRelConverter sqlToRelConverter = new SqlToRelConverter(new CommonViewExpanderImpl(),
                sqlValidator, calciteCatalogReader, cluster, frameworkConfig.getConvertletTable(), config);

        RelRoot root = sqlToRelConverter.convertQuery(validatedSqlNode, false, true);
        root = root.withRel(sqlToRelConverter.flattenTypes(root.rel, true));
        RelNode relRoot = root.rel;

        hepPlanner.setRoot(relRoot);
        RelNode relNode = hepPlanner.findBestExp();

        System.out.println("解析relNode");
        System.out.println(RelOptUtil.toString(relNode));
        System.out.println("===============================");

        RelToSqlConverter relToSqlConverter = new RelToSqlConverter(OracleSqlDialect.DEFAULT);
        SqlImplementor.Result result = relToSqlConverter.visit(relNode);

        return result.asSelect().toSqlString(OracleSqlDialect.DEFAULT).getSql();
    }

    public static SqlParser sqlParserInit(String originalSql, Lex lex) {
        return SqlParser.create(originalSql, SqlParser.Config.DEFAULT.withLex(lex));
    }

    public static HepPlanner plannerInit() {
        // 初始化规则优化器
        HepProgram hepProgram = HepProgram.builder()
//                .addRuleInstance(FilterJoinRule.FilterIntoJoinRule.FILTER_ON_JOIN)
//                .addRuleInstance(ReduceExpressionsRule.PROJECT_INSTANCE)
                .addRuleInstance(CoreRules.AGGREGATE_REMOVE)
                .addRuleInstance(CoreRules.PROJECT_FILTER_TRANSPOSE)
                .addRuleInstance(CoreRules.MULTI_JOIN_BOTH_PROJECT)
                .addRuleInstance(CoreRules.MULTI_JOIN_LEFT_PROJECT)
                .addRuleInstance(CoreRules.MULTI_JOIN_OPTIMIZE)
                .addRuleInstance(CoreRules.MULTI_JOIN_OPTIMIZE_BUSHY)
                .addMatchLimit(10)
                .build();
        return new HepPlanner(hepProgram);
    }

    public static SqlTypeFactoryImpl sqlTypeFactoryInit() {
        return new SqlTypeFactoryImpl(RelDataTypeSystem.DEFAULT);
    }

    public static CalciteCatalogReader catalogReaderInit(SchemaPlus rootSchema, SqlTypeFactoryImpl factory) {
        return new CalciteCatalogReader(
                CalciteSchema.from(rootSchema),
                CalciteSchema.from(rootSchema).path(null),
                factory,
                new CalciteConnectionConfigImpl(new Properties()));
    }

    public static RexBuilder createRexBuilder(RelDataTypeFactory typeFactory) {
        return new RexBuilder(typeFactory);
    }

    public static SqlValidator validatorInit(SqlTypeFactoryImpl factory, CalciteCatalogReader calciteCatalogReader) {
        return SqlValidatorUtil
                .newValidator(SqlStdOperatorTable.instance(), calciteCatalogReader,
                        factory, SqlValidator.Config.DEFAULT);
    }


    /**
     * schema生成
     *
     * @param schemaInfos
     * @return
     */
    public static SchemaPlus genSchema(List<SchemaInfo> schemaInfos) {
        SchemaPlus rootSchema = Frameworks.createRootSchema(true);
        for (SchemaInfo schemaInfo : schemaInfos) {
            String table = schemaInfo.getTable();
            List<SchemaInfo.ColumnInfo> columnInfoList = schemaInfo.getColumnInfoList();
            //note: add a table
            rootSchema.add(table, new AbstractTable() {
                @Override
                public RelDataType getRowType(final RelDataTypeFactory typeFactory) {
                    RelDataTypeFactory.Builder builder = typeFactory.builder();
                    for (SchemaInfo.ColumnInfo columnInfo : columnInfoList) {
                        String columnName = columnInfo.getName();
                        SqlTypeName columnType = columnInfo.getColumnType();
                        builder.add(columnName, new BasicSqlType(new RelDataTypeSystemImpl() {
                        }, columnType));
                    }
                    return builder.build();
                }
            });
        }
        return rootSchema;
    }
}
