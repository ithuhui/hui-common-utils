package com.hui.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <code>DateUtils</code>
 * <desc>
 * 描述：
 * <desc/>
 * Creation Time: 2019/12/7 14:17.
 *
 * @author Gary.Hu
 */
public class DateUtils {

    private static final String DATE_TIMESTAMP = "yyyyMMddHHmmss";

    private static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    private static final String DATE_DAY = "yyyyMMdd";

    private static final String DATE_MIN = "yyyyMMddHHmm";

    private static ThreadLocal<Map<String, SimpleDateFormat>> threadLocalMap = new ThreadLocal<Map<String, SimpleDateFormat>>() {
        @Override
        protected Map<String, SimpleDateFormat> initialValue() {
            Map<String, SimpleDateFormat> dateFormatMap = new HashMap<>();
            dateFormatMap.put(DATE_TIMESTAMP, new SimpleDateFormat(DATE_TIMESTAMP));
            dateFormatMap.put(DATE_TIME, new SimpleDateFormat(DATE_TIME));
            dateFormatMap.put(DATE_DAY, new SimpleDateFormat(DATE_DAY));
            dateFormatMap.put(DATE_MIN, new SimpleDateFormat(DATE_MIN));
            return dateFormatMap;
        }
    };


    public static Date parse(String dateStr) throws ParseException {
        return parse(dateStr, DATE_TIMESTAMP);
    }


    public static Date parse(String dateStr, String format) throws ParseException {
        return threadLocalMap.get().get(format).parse(dateStr);
    }

    public static String format(Date date) {
        return format(date, DATE_TIMESTAMP);
    }


    public static String format(Date date, String format) {
        return threadLocalMap.get().get(format).format(date);
    }

    public static BigDecimal subDate(Date star, Date end) {
        long hourTime = 60 * 60 * 1000L;
        long starTime = star.getTime();
        long endTime = end.getTime();
        BigDecimal starDecimal = new BigDecimal(starTime);
        BigDecimal endDecimal = new BigDecimal(endTime);
        BigDecimal subtract = endDecimal.subtract(starDecimal);
        BigDecimal divide = subtract.divide(new BigDecimal(hourTime), 2, BigDecimal.ROUND_HALF_UP);
        return divide;
    }

    public static int hour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

}
