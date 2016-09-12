package com.mycompany.myapp.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil {

    /**
     * 日期转换成字段串，按照格式：yyyy-MM-dd HH:mm:ss
     *
     * @param date Date对象
     */
    public static String parseDate2Str(Date date) {

        return date != null ? new SimpleDateFormat(DatePatternEnum.DATETIME.getText()).format(date) : null;
    }

    /**
     * 日期转换成字段串，按照指定的格式
     *
     * @param date            Date对象
     * @param datePatternEnum 日期格式枚举
     */
    public static String parseDate2Str(Date date, DatePatternEnum datePatternEnum) {

        return date != null ? new SimpleDateFormat(datePatternEnum.getText()).format(date) : null;
    }

    /**
     * 字段串转换成日期，按照格式：yyyy-MM-dd HH:mm:ss
     *
     * @param ds 日期字段串
     */
    public static Date parseStr2Date(String ds) throws Exception {

        return StringUtils.isNotBlank(ds) ? new SimpleDateFormat(DatePatternEnum.DATETIME.getText()).parse(ds) : null;
    }

    /**
     * 字段串转换成日期，按照指定的格式
     *
     * @param ds              日期字段串
     * @param datePatternEnum 日期格式枚举
     */
    public static Date parseStr2Date(String ds, DatePatternEnum datePatternEnum) throws Exception {

        return StringUtils.isNotBlank(ds) ? new SimpleDateFormat(datePatternEnum.getText()).parse(ds) : null;
    }

    public enum DatePatternEnum {
        DATETIME("yyyy-MM-dd HH:mm:ss"), //
        DATE("yyyy-MM-dd"),  //
        TIME("HH:mm:ss"),  //
        DATE_DOT("yyyy.MM.dd"), //
        DATE_INCLINE("yyyy/MM/dd"), //
        DATE_JOIN("yyyyMMdd"), //
        TIME_SHOT("HH:mm"), //
        DATE_DOT_TIME("yyyy.MM.dd HH:mm:ss"), //
        DATE_INCLINE_TIME("yyyy/MM/dd HH:mm:ss"), //
        DATE_TIME_SHOT("yyyy-MM-dd HH:mm"),   //
        DATE_DOT_TIME_SHOT("yyyy.MM.dd HH:mm"), //
        DATE_JOIN_TIME_JOIN("yyyyMMddHHmmss");

        private String text;

        DatePatternEnum(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
