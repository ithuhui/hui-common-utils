package pers.hui.common.beetl.test;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import pers.hui.common.beetl.FunType;
import pers.hui.common.beetl.SqlParseInfo;
import pers.hui.common.beetl.SqlParseUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * <code>BeetlTest</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/3/17 14:28.
 *
 * @author Gary.Hu
 */
public class BeetlTest {
    @Test
    public void testGet() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("03-test-v2.txt");
        assert inputStream != null;
        String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        Map<FunType, SqlParseInfo> funValMap = SqlParseUtils.getFunValMap(content);
        String s = JSON.toJSONString(funValMap);
        System.out.println(s);
    }
}
