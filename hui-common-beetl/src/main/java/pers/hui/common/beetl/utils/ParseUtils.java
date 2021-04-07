package pers.hui.common.beetl.utils;

import pers.hui.common.beetl.FunType;
import pers.hui.common.beetl.FunVal;

import java.util.UUID;

/**
 * <code>ParseUtils</code>
 * <desc>
 * 描述：
 * <desc/>
 * <b>Creation Time:</b> 2021/4/2 23:31.
 *
 * @author Ken.Hu
 */
public class ParseUtils {
    public static String keyGen(String code){
        return  UUID.nameUUIDFromBytes((code).getBytes()).toString();
    }

    public static String keyGen(String group,String code){
        String keyStr = group.concat("<!>").concat(code);
        return  UUID.nameUUIDFromBytes((keyStr).getBytes()).toString();
    }

    public static String keyGen(FunType funType, String code){
        String keyStr = funType.name().concat("<!>").concat(code);
        return  UUID.nameUUIDFromBytes((keyStr).getBytes()).toString();
    }

    public static String genOutPutVal(FunVal funVal) {
        String key = funVal.getGroup();
        return key.concat("_").concat(funVal.getCode()).concat("_").concat("out");
    }
}
