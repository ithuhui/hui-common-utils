package pers.hui.common.utils;

import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.text.ParseException;
import java.util.Date;

/**
 * <code>TypeTransform</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/7 15:47.
 *
 * @author Gary.Hu
 */
@Slf4j
public enum TypeTransform {
    /**
     * String -> Java类型转换
     */
    INTEGER {
        @Override
        public Object doTransform(String val) {
            return Integer.valueOf(val);
        }
    },
    LONG {
        @Override
        public Object doTransform(String val) throws ParseException {
            if (val.contains(DATE_TIME_REMARK)) {
                Date date = DateUtils.parse(val);
                return date.getTime();
            }
            return Long.valueOf(val);
        }
    },
    STRING {
        @Override
        public Object doTransform(String val) {
            return val;
        }
    },
    DATE {
        @Override
        public Object doTransform(String val) throws ParseException {
            Date date = DateUtils.parse(val);
            return date;
        }
    },
    FLOAT {
        @Override
        public Object doTransform(String val) {
            return Float.valueOf(val);
        }
    },
    ARRAY {
        @Override
        public Object doTransform(String val) {
            String[] splitArray = val.split("\\|");
            return splitArray;
        }
    };

    private static final String DATE_TIME_REMARK = ":";

    abstract Object doTransform(String val) throws Exception;

    public Object transform(String val) {
        try {
            //validate data
            if (null == val || val.equals("")) {
                return null;
            }
            return doTransform(val);
        } catch (Exception e) {
            log.error("类型转换错误,返回null值,msg:{}", e.getMessage(), e);
            return null;
        }
    }
}
