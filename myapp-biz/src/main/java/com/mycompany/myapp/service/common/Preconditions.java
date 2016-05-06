package com.mycompany.myapp.service.common;

import com.mycompany.myapp.enums.msg.CommonMsgEnum;

public class Preconditions {

  /**
   * 确保多个表达式的正确性，如果为否则抛ServiceException异常.
   */
  public static void checkArgument(boolean... expressions) {
    for (boolean expression : expressions) {
      if (!expression) {
        throw new ServiceException(CommonMsgEnum.FAIL_BIZ_PARAM_ERROR);
      }
    }
  }

  /**
   * 确保一个表达式的正确性，如果为否则抛ServiceException异常.
   */
  public static void checkArgument(boolean expression, Object errorMessage) {
    if (!expression) {
      throw new ServiceException(String.valueOf(errorMessage),
          CommonMsgEnum.FAIL_BIZ_PARAM_ERROR.getCode());
    }

  }

  /**
   * 确保多个参数不为空，如果有一个为空则抛ServiceException异常.
   */
  public static void checkNotNull(Object... args) {
    for (Object o : args) {
      if (o == null) {
        throw new ServiceException(CommonMsgEnum.FAIL_BIZ_PARAM_ERROR);
      }
    }
  }

  /**
   * 确保单个参数不为空，如果为空则抛ServiceException异常.
   */
  public static <T> T checkNotNull(T reference, Object errorMessage) {
    if (reference == null) {
      throw new ServiceException(String.valueOf(errorMessage),
          CommonMsgEnum.FAIL_BIZ_PARAM_ERROR.getCode());
    }
    return reference;
  }
}
