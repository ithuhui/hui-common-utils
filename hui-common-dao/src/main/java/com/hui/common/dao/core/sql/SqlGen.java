package com.hui.common.dao.core.sql;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * <code>CommonSqlRunner</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/9 21:28.
 *
 * @author Gary.Hu
 */
public class SqlGen {

    private StringBuilder resultSQL;


    private SqlGen(StringBuilder resultSQL) {
        this.resultSQL = resultSQL;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String gen() {
        System.out.println(this.resultSQL);
        return this.resultSQL.toString();
    }

    public static class Builder {

        private StringBuilder resultSQL;
        private AtomicBoolean firstWhere;

        public Builder() {
            this.firstWhere = new AtomicBoolean(true);
            this.resultSQL = new StringBuilder();
        }

        public Builder select() {
            select("*");
            return this;
        }

        public Builder select(String... fields) {
            this.resultSQL.append(String.format("select %s ", String.join(",", fields)));
            return this;
        }

        public Builder from(String tableName) {
            this.resultSQL.append(String.format(" from %s ", tableName));
            return this;
        }

        public Builder selectCount() {
            this.resultSQL.append(FunSQL.COUNT.getFunSQL("1"));
            return this;
        }

        public Builder selectMax(String field) {
            this.resultSQL.append(FunSQL.MAX.getFunSQL(field));
            return this;
        }

        public Builder selectMin(String field) {
            this.resultSQL.append(FunSQL.MIN.getFunSQL(field));
            return this;
        }

        public Builder selectAvg(String field) {
            this.resultSQL.append(FunSQL.AVG.getFunSQL(field));
            return this;
        }

        public Builder selectSum(String field) {
            this.resultSQL.append(FunSQL.SUM.getFunSQL(field));
            return this;
        }

        public Builder join(String tableName, String condition) {
            this.resultSQL.append(String.format("join %s on %s", tableName, condition));
            return this;
        }

        public Builder join(String tableName, String condition, String joinType) {
            this.resultSQL.append(String.format("%s join %s on %s", joinType, tableName, condition));
            return this;
        }

        public Builder where(String expression) {
            if (firstWhere.get()) {
                this.resultSQL.append(String.format(" where %s ", expression));
                firstWhere.set(false);
            } else {
                this.resultSQL.append(String.format(" and %s ", expression));
            }
            return this;
        }

        public Builder where(String key, String val) {
            if (firstWhere.get()) {
                this.resultSQL.append(String.format("where %s = %s ", key, val));
                firstWhere.set(false);
            } else {
                this.resultSQL.append(String.format("and %s = %s ", key, val));
            }
            return this;
        }

        public Builder where(String key, String operator, String val) {
            if (firstWhere.get()) {
                this.resultSQL.append(String.format("where %s %s %s ", key, operator, val));
                firstWhere.set(false);
            } else {
                this.resultSQL.append(String.format("and %s %s %s ", key, operator, val));
            }
            return this;
        }

        public Builder where(Map<String, String> whereMap) {
            whereMap.entrySet().stream().forEach(data -> {
                if (firstWhere.get()) {
                    this.resultSQL.append(String.format("where %s %s", data.getKey(), data.getValue()));
                    firstWhere.set(false);
                } else {
                    this.resultSQL.append(String.format("and %s %s", data.getKey(), data.getValue()));
                }
            });
            return this;
        }

        public Builder or(String expression) {
            this.resultSQL.append(String.format("or %s", expression));
            return this;
        }

        public Builder or(String key, String val) {
            this.resultSQL.append(String.format("or %s = %s", key, val));
            return this;
        }

        public Builder in(String... params) {
            this.resultSQL.append(String.format("in (%s)", String.join(",", params)));
            return this;
        }

        public Builder notIn(String... params) {
            this.resultSQL.append(String.format("not in (%s)", String.join(",", params)));
            return this;
        }

        public Builder like(String param) {
            this.resultSQL.append(String.format("like '%%%s%%'", param));
            return this;
        }

        public Builder groupBy(String... params) {
            this.resultSQL.append(String.format("group by ", String.join(",", params)));
            return this;
        }

        public Builder orderBy(String key, String val) {
            this.resultSQL.append(String.format("order by %s %s", key, val));
            return this;
        }

        public Builder limit(int offset) {
            this.resultSQL.append(String.format("limit %s", offset));
            return this;
        }

        public Builder limit(int offset, int size) {
            this.resultSQL.append(String.format("limit %s,%s", offset, size));
            return this;
        }


        public Builder insert(String tableName, Map<String, String> dataMap) {
            this.resultSQL.append(
                    String.format("insert into `%s` (%s) values (%s)",
                            tableName,
                            String.join(",", dataMap.keySet()),
                            dataMap.values().stream().map(data -> "'" + data + "'")
                                    .collect(Collectors.joining(","))
                    )
            );
            return this;
        }

        public Builder insertBatch(String tableName, List<Map<String, String>> dataList) {
            if (dataList.size() < 1) {
                return this;
            }
            List<String> keyList = new ArrayList<>(dataList.get(0).keySet());
            String keys = String.join(",", keyList);
            String values = dataList.stream()
                    .map(
                            data -> keyList.stream().map(key -> data.get(key))
                                    .collect(Collectors.joining(","))
                    ).collect(Collectors.joining("),("));

            this.resultSQL.append(String.format("insert into %s (%s) values (%s)", tableName, keys, values));
            return this;
        }

        public Builder update(String tableName, Map<String, String> dataMap) {
            this.resultSQL.append(
                    String.format("update `%s` set %s ",
                            tableName,
                            dataMap.entrySet().stream()
                                    .map(entry -> entry.getKey() + "='" + entry.getValue() + "'")
                                    .collect(Collectors.joining(","))
                    )
            );
            return this;
        }

        public Builder updateBatch(String tableName, List<Map<String, String>> dataList) {
            if (dataList.size() < 1) {
                return this;
            }
            String fields = String.join(",", dataList.get(0).keySet());
            String valueList = dataList.stream()
                    .map(data -> "(" + String.join(",", data.values()) + ")")
                    .collect(Collectors.joining(","));

            String updateList = dataList.get(0).keySet().stream()
                    .map(data -> data + "=values(" + data + ")")
                    .collect(Collectors.joining(","));


            this.resultSQL.append(String.format("insert into %s (%s) values %s  on duplicate key update %s", tableName, fields, valueList, updateList));
            return this;
        }

        public Builder delete(String tableName) {
            this.resultSQL.append(String.format("delete from `%s` ", tableName));
            return this;
        }

        public Builder truncate(String tableName) {
            this.resultSQL.append(String.format("truncate `%s`", tableName));
            return this;
        }

        public SqlGen build() {
            return new SqlGen(this.resultSQL.append(";"));
        }
    }


}
