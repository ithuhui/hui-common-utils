package com.hui.common.utils;

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
}
