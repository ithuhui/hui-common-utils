package pers.hui.calcite;

import org.apache.calcite.sql.type.SqlTypeName;

import java.util.List;

/**
 * <code>SchemaInfo</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/2 16:08.
 *
 * @author Gary.Hu
 */
public class SchemaInfo {

    private String table;
    private List<ColumnInfo> columnInfoList;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<ColumnInfo> getColumnInfoList() {
        return columnInfoList;
    }

    public void setColumnInfoList(List<ColumnInfo> columnInfoList) {
        this.columnInfoList = columnInfoList;
    }


    public static class ColumnInfo{
        /**
         * 字段名
         */
        private String name;

        /**
         * 字段类型
         */
        private SqlTypeName columnType;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public SqlTypeName getColumnType() {
            return columnType;
        }

        public void setColumnType(SqlTypeName columnType) {
            this.columnType = columnType;
        }
    }
}
