package com.mycompany.myapp.service.common;

import com.mycompany.myapp.enums.msg.BaseMsgEnum;
import com.mycompany.myapp.enums.msg.CommonMsgEnum;

public class ServicePreconditions {

  public static void checkArgument(boolean expression) {
    if (!expression) {
      throw new ServiceException(CommonMsgEnum.FAIL_BIZ_PARAM_ERROR);
    }
  }

  public static void checkArgument(boolean expression, BaseMsgEnum msgEnum) {
    if (!expression) {
      throw new ServiceException(msgEnum.getMsg(), msgEnum.getCode());
    }
  }

  public static void checkArgument(boolean expression, BaseMsgEnum msgEnum, Object... args) {
    if (!expression) {
      throw new ServiceException(String.format(msgEnum.getMsg(), args), msgEnum.getCode());
    }
  }
}
