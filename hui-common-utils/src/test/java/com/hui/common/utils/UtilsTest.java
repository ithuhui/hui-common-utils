package com.hui.common.utils;

import okhttp3.Call;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

/**
 * <code>UtilsTest</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/7 15:03.
 *
 * @author Gary.Hu
 */
public class UtilsTest {

    @Test
    public void aesUtilsTest() throws Exception {
        String s = "admin";

        System.out.println("get msg:" + s);

        String s1 = AesUtils.encrypt(s, "admin");

        System.out.println("after encrypt: " + s1);

        System.out.println("after decrypt: " + AesUtils.decrypt(s1, "admin"));
    }

    @Test
    public void dateUtilsTest() throws Exception {
        String dateTime = DateUtils.format(new Date());
        System.out.println(dateTime);
    }


    @Test
    public void restUtilsTest() throws Exception {
        RestUtils.INSTANCE.httpGetAsync("http://www.baidu.com", new RestUtils.NetCallBack() {});

        RestUtils.INSTANCE.downloadFile("https://img.jinsom.cn/user_files/13515/publish/file/file-2019-03-05-14-38-18.gif", "D:/test/", "down.gif", new RestUtils.NetCallBack() {}, new RestUtils.ProcessCallBack(){});
        Thread.sleep(10000);
    }
}
