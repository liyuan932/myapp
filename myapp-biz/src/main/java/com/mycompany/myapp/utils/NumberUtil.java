package com.mycompany.myapp.utils;

import java.math.BigDecimal;

/**
 *数字工具类
 */
public final class NumberUtil {

  /**
   *默认构造器
   */
  private NumberUtil() {
  }

  /**
   *2个double值相减
   */
  public static double subtract(final double a, final double b) {
    return new BigDecimal(a + "").subtract(new BigDecimal(b + "")).doubleValue();
  }

  /**
   *2个double值相加
   */
  public static double add(final double a, final double b) {
    return new BigDecimal(a + "").add(new BigDecimal(b + "")).doubleValue();
  }

  /**
   *2个double值相除
   */
  public static double divide(final double a, final double b) {
    return new BigDecimal(a + "").divide(new BigDecimal(b + "")).doubleValue();
  }

  /**
   *2个double值相乘
   */
  public static double multiply(final double a, final double b) {
    return new BigDecimal(a + "").multiply(new BigDecimal(b + "")).doubleValue();
  }
}
