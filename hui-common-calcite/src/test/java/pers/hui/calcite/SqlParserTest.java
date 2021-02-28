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
import org.apache.calcite.rel.rules.PruneEmptyRules;
import org.apache.calcite.rel.type.RelDataTypeSystem;
import org.apache.calcite.rex.RexBuilder;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.dialect.MysqlSqlDialect;
import org.apache.calcite.sql.dialect.OracleSqlDialect;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.type.SqlTypeFactoryImpl;
import org.apache.calcite.sql.util.SqlString;
import org.apache.calcite.sql.validate.SqlValidator;
import org.apache.calcite.sql.validate.SqlValidatorUtil;
import org.apache.calcite.sql2rel.SqlToRelConverter;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;
import org.junit.Test;

import java.util.Properties;

/**
 * <code>SqlParserTest</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/2/25 16:14.
 *
 * @author Gary.Hu
 */
public class SqlParserTest {
    @Test
    public void parserTest() throws SqlParseException {
        SqlParser sqlParser = SqlParser.create("select * from emps where id = 1 limit 10 "
                , SqlParser.Config.DEFAULT.withLex(Lex.ORACLE)
        );
        SqlNode sqlNode = sqlParser.parseQuery();
        SqlString oracleSql = sqlNode.toSqlString(MysqlSqlDialect.DEFAULT);
        System.out.println(oracleSql);
    }

    @Test
    public void optimizer() throws Exception {
        String testSql = "select u.id as user_id, u.name as user_name, j.company as user_company, u.age as user_age from users u"
                + " join jobs j on u.id=j.id where u.age > 30 and j.id>10 order by user_id limit 10";

        // sql解析器
        SqlParser sqlParser = SqlParser.create(testSql
                , SqlParser.Config.DEFAULT.withLex(Lex.ORACLE)
        );

        SqlNode sqlNode = sqlParser.parseQuery();

        SchemaPlus rootSchema = CalciteUtils.registerRootSchema();

        SqlTypeFactoryImpl factory = new SqlTypeFactoryImpl(RelDataTypeSystem.DEFAULT);


        final FrameworkConfig frameworkConfig = Frameworks.newConfigBuilder()
                .parserConfig(SqlParser.Config.DEFAULT)
                .defaultSchema(rootSchema)
                .traitDefs(ConventionTraitDef.INSTANCE, RelDistributionTraitDef.INSTANCE)
                .build();

        CalciteCatalogReader calciteCatalogReader = new CalciteCatalogReader(
                CalciteSchema.from(rootSchema),
                CalciteSchema.from(rootSchema).path(null),
                factory,
                new CalciteConnectionConfigImpl(new Properties()));


        // 初始化规则优化器
        HepProgram hepProgram = HepProgram.builder()
//                .addRuleInstance(FilterJoinRule.FilterIntoJoinRule.FILTER_ON_JOIN)
//                .addRuleInstance(ReduceExpressionsRule.PROJECT_INSTANCE)
                .addRuleInstance(CoreRules.AGGREGATE_REMOVE)
                .addRuleInstance(CoreRules.PROJECT_FILTER_TRANSPOSE)
                .addRuleInstance(PruneEmptyRules.PROJECT_INSTANCE)
                .addMatchLimit(10)
                .build();
        HepPlanner planner = new HepPlanner(hepProgram);


        SqlValidator validator = SqlValidatorUtil
                .newValidator(SqlStdOperatorTable.instance(), calciteCatalogReader,
                factory, SqlValidator.Config.DEFAULT);

        final RexBuilder rexBuilder = CalciteUtils.createRexBuilder(factory);
        final RelOptCluster cluster = RelOptCluster.create(planner, rexBuilder);

        SqlNode validated = validator.validate(sqlNode);

        final SqlToRelConverter.Config config = frameworkConfig.getSqlToRelConverterConfig();

        final SqlToRelConverter sqlToRelConverter = new SqlToRelConverter(new CalciteUtils.ViewExpanderImpl(),
                validator, calciteCatalogReader, cluster, frameworkConfig.getConvertletTable(), config);


        RelRoot root = sqlToRelConverter.convertQuery(validated, false, true);
        root = root.withRel(sqlToRelConverter.flattenTypes(root.rel, true));
        RelNode relRoot = root.rel;

        planner.setRoot(relRoot);
        RelNode relNode = planner.findBestExp();

        System.out.println("解析relNode");
        System.out.println(RelOptUtil.toString(relNode));
        System.out.println("===============================");

        RelToSqlConverter relToSqlConverter = new RelToSqlConverter(OracleSqlDialect.DEFAULT);
        SqlImplementor.Result result = relToSqlConverter.visitRoot(relNode);
        SqlString resSql = result.asSelect().toSqlString(OracleSqlDialect.DEFAULT);
        System.out.println(resSql);
    }
}
