package com.hui.common.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * <code>RetryIntercepter</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/11/24 12:20.
 *
 * @author Gary.Hu
 */
@Slf4j
public class RetryInterceptor implements Interceptor {

    public int maxRetry = 3;
    private int retryNum = 0;

    public RetryInterceptor(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return retry(chain);
    }

    public Response retry(Chain chain) {
        Response response = null;
        Request request = chain.request();
        try {
            response = chain.proceed(request);
            while (!response.isSuccessful() && retryNum < maxRetry) {
                log.info("Okhttp remote access Fail !!! , Retry Now ", retryNum);
                retryNum++;
                response = retry(chain);
            }
        } catch (Exception e) {
            while (retryNum < maxRetry) {
                log.info("Okhttp remote access Fail !!! , Retry Now ", retryNum);
                retryNum++;
                response = retry(chain);
            }
        }
        return response;
    }
}
