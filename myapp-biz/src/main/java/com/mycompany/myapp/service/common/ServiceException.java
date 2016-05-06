package com.mycompany.myapp.service.common;

import com.mycompany.myapp.enums.msg.BaseMsgEnum;

public class ServiceException extends RuntimeException {
  private static final long serialVersionUID = 2515955074355075103L;

  private String code;

  public ServiceException(String msg, String code) {
    super(msg);
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public ServiceException(BaseMsgEnum baseMsgEnum) {
    this(baseMsgEnum.getMsg(), baseMsgEnum.getCode());
  }
}
