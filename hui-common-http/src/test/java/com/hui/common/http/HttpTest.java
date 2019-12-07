package com.hui.common.http;

import org.junit.Test;

/**
 * <code>HttpTest</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/7 15:57.
 *
 * @author Gary.Hu
 */
public class HttpTest {

    @Test
    public void httpUtilTest() throws Exception{
        System.out.println(HttpUtils.INSTANCE.getOkHttpClient());
        System.out.println(HttpUtils.INSTANCE.getOkHttpClient());
        System.out.println(HttpUtils.INSTANCE.getOkHttpClient());
    }
}
