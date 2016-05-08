package com.mycompany.myapp.utils;

import java.math.BigDecimal;

/**
 * 数字工具类
 */
public final class NumberUtil {

  private NumberUtil() {}

  /** 2个double值相减 */
  public static double subtract(final double minuend, final double subtrahend) {
    return new BigDecimal(minuend + "").subtract(new BigDecimal(subtrahend + "")).doubleValue();
  }

  /** 2个double值相加 */
  public static double add(final double augend, final double addend) {
    return new BigDecimal(augend + "").add(new BigDecimal(addend + "")).doubleValue();
  }

  /** 2个double值相除 */
  public static double divide(final double dividend, final double divisor) {
    return new BigDecimal(dividend + "").divide(new BigDecimal(divisor + "")).doubleValue();
  }

  /** 2个double值相乘 */
  public static double multiply(final double multiplicand, final double multiplier) {
    return new BigDecimal(multiplicand + "").multiply(new BigDecimal(multiplier + "")).doubleValue();
  }
}
