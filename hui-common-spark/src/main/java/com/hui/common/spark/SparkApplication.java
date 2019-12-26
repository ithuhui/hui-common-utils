package com.hui.common.spark;

import com.hui.common.spark.core.BaseSparkJob;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.util.Utils;

/**
 * <code>SparkApplication</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/26 23:18.
 *
 * @author Gary.Hu
 */
public class SparkApplication {

    public static void main(String[] args) {
        // 初始化Spark环境
        try {
            SparkConf sparkConf = new SparkConf()
                    .setAppName("")
                    .setMaster("");
            JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);
            String className = args[0];
            Class clazz = Utils.classForName(className);
            Object sparkJob = clazz.newInstance();
            if (sparkJob instanceof BaseSparkJob) {
                ((BaseSparkJob) sparkJob).execute(javaSparkContext);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
