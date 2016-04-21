package com.mycompany.myapp.utils;

import java.math.BigDecimal;

public class NumberUtils {

    /**
     * 2个double值相减
     *
     * @param minuend    被减数
     * @param subtractor 减数
     * @return
     */
    public static double subtract(double minuend, double subtractor) {

        BigDecimal result = new BigDecimal(minuend + "").subtract(new BigDecimal(subtractor + ""));

        return result.doubleValue();
    }

    /**
     * 2个double值相加
     *
     * @return
     */
    public static double add(double a, double b) {

        BigDecimal result = new BigDecimal(a + "").add(new BigDecimal(b + ""));

        return result.doubleValue();
    }

    /**
     * 2个double值相除
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @return
     */
    public static double divide(double dividend, double divisor) {

        BigDecimal result = new BigDecimal(dividend + "").divide(new BigDecimal(divisor + ""));

        return result.doubleValue();
    }

    /**
     * 2个double值相乘
     *
     * @param multiplier   被乘数
     * @param multiplicand 乘数
     * @return
     */
    public static double multiply(double multiplier, double multiplicand) {

        BigDecimal result = new BigDecimal(multiplier + "").multiply(new BigDecimal(multiplicand + ""));

        return result.doubleValue();
    }

    public static void main(String[] args) {
        System.out.println(multiply(0.23, 0.51));
    }
}
