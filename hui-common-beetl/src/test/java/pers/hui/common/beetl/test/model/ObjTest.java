package pers.hui.common.beetl.test.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <code>ObjTest</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/4/13 21:23.
 *
 * @author _Ken.Hu
 */
public class ObjTest {
    @Test
    public void test(){
        ParseInfo<Bind> bindParseInfo = new ParseInfo<>();
        Map<String, List<Bind>> bindingMap = bindParseInfo.getBindingMap();
        bindingMap.put("", Collections.singletonList(new TestBind()));
        List<TestBind> testBinds = get(TestBind.class);
    }

    private <T extends Bind> List<T> get(Class<T> type){
        ParseInfo<Bind> bindParseInfo = new ParseInfo<>();
        Map<String, List<Bind>> bindingMap = bindParseInfo.getBindingMap();
        Object[] objects = bindingMap.get("").toArray();
        return Arrays.stream(objects).map(type::cast).collect(Collectors.toList());
    }
}
