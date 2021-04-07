package pers.hui.common.beetl.test;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import pers.hui.common.beetl.BindingInfo;
import pers.hui.common.beetl.FunType;
import pers.hui.common.beetl.FunVal;
import pers.hui.common.beetl.SqlParseUtils;
import pers.hui.common.beetl.binding.Binding;
import pers.hui.common.beetl.binding.DimBinding;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
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
        Map<String, FunVal> funValMap = SqlParseUtils.getFunValMap(content);
        Gson gson = new Gson();
        String s = gson.toJson(funValMap);
        System.out.println(s);
    }

    @Test
    public void testRender() throws IOException {

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("03-test-v2.txt");
        assert inputStream != null;
        String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

        InputStream in = getClass().getClassLoader().getResourceAsStream("03-test-v2.json");
        assert in != null;
        String json = IOUtils.toString(in, StandardCharsets.UTF_8);
        BindingInfo binding = JSON.parseObject(json, BindingInfo.class);
        String result = SqlParseUtils.renderWithBinding(content, binding);
        System.out.println(result);
    }


    @Test
    public void gen() {
        DimBinding dimBinding = new DimBinding();
        dimBinding.setCode("1");
        dimBinding.setGroup("1");
        HashMap<FunType, Binding> map = new HashMap<>();
        map.put(FunType.DIM, dimBinding);
    }
}
