package pers.hui.common.spark.core;

import org.apache.spark.api.java.JavaSparkContext;

import java.io.Serializable;

/**
 * <code>SparkJob</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/26 23:12.
 *
 * @author Gary.Hu
 */
public abstract class BaseSparkJob implements Serializable {
    /**
     * Instantiates a new Spark job.
     */
    protected BaseSparkJob() {
    }

    /**
     * 带参数
     *
     * @param javaSparkContext the java spark context
     * @param args             the args
     */
    public void execute(JavaSparkContext javaSparkContext, String[] args) {
    }


    /**
     * 不带参数
     *
     * @param javaSparkContext the java spark context
     */
    public void execute(JavaSparkContext javaSparkContext) {
    }

}
