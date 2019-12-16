package com.hui.common.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import lombok.ToString;
import org.junit.Test;

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
        RestUtils.INSTANCE.httpGetAsync("http://www.baidu.com", new RestUtils.NetCallBack() {
        });

        RestUtils.INSTANCE.downloadFile("https://img.jinsom.cn/user_files/13515/publish/file/file-2019-03-05-14-38-18.gif", "D:/test/", "down.gif", new RestUtils.NetCallBack() {
        }, new RestUtils.ProcessCallBack() {
        });
        Thread.sleep(10000);
    }


    @Test
    public void configUtilsTest() {
        String username = ConfigUtils.INSTANCE.get("username");
        String password = ConfigUtils.INSTANCE.get("password");

        System.out.println(username);
        System.out.println(password);

        String s = ConfigUtils.INSTANCE.get("test1.cfg", "app_name", "app_name1");
        System.out.println(s);
    }

    @Test
    public void gsonStringNullTest() {
        String json = "{\n" +
                "  \"msg\": \"success\",\n" +
                "  \"code\": 200,\n" +
                "  \"data\": {\n" +
                "    \"name\": \"test\",\n" +
                "    \"age\": 18\n" +
                "  }\n" +
                "}";

        String json2 = "{\n" +
                "  \"msg\": \"fail\",\n" +
                "  \"code\": 500,\n" +
                "  \"data\": \"\"\n" +
                "}";
        Gson gson = GsonUtils.INSTANCE.getGson();
        ResponseDto responseDto =
                gson.fromJson(json2, new TypeToken<ResponseDto>() {
                }.getType());
        System.out.println(responseDto.toString());
        if (null == responseDto.getData() || "".equals(responseDto.getData())) {
            System.out.println(responseDto.getCode());
        } else {
            TestData testData = gson.fromJson(json2, new TypeToken<TestData>() {
            }.getType());
            System.out.println(testData);
        }

    }

    @Data
    @ToString
    static class ResponseDto<T> {
        private String msg;
        private T data;
        private int code;
    }

    @Data
    @ToString
    static class TestData {
        private String name;
        private int age;
    }
}
