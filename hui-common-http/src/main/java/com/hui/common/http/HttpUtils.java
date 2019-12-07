package com.hui.common.http;

import lombok.Getter;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * <code>HttpUtils</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/7 15:51.
 *
 * @author Gary.Hu
 */
public enum HttpUtils {
    /**
     * init single
     */
    INSTANCE;
    /**
     * 连接超时时间
     */
    private static final int CONNECTION_TIME_OUT = 10000;
    /**
     * 读写超时时间
     */
    private static final int SOCKET_TIME_OUT = 15000;
    /**
     * 空闲连接数
     */
    private static final int MAX_IDLE_CONNECTIONS = 30;
    /**
     * 保持连接时间
     */
    private static final long KEEP_ALIVE_TIME = 60000L;

    @Getter
    private OkHttpClient okHttpClient;

    HttpUtils() {
        ConnectionPool connectionPool = new ConnectionPool(MAX_IDLE_CONNECTIONS, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS);
        this.okHttpClient = new OkHttpClient()
                .newBuilder()
                .readTimeout(SOCKET_TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(SOCKET_TIME_OUT, TimeUnit.MILLISECONDS)
                .connectionPool(connectionPool)
                //自动重连设置为false
                .retryOnConnectionFailure(true)
                .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.MILLISECONDS)
                //重试拦截器3次
                .addInterceptor(new RetryIntercepter(5))
                .build();
    }

}
