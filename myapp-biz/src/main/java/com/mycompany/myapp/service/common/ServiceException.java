package com.mycompany.myapp.service.common;

import com.mycompany.myapp.enums.msg.BaseMsgEnum;

public class ServiceException extends RuntimeException {

  private static final long serialVersionUID = 2515955074355075103L;

  private String code;
  private String msg;

  public String getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }

  public ServiceException(String msg, String code) {
    super(msg);
    this.msg = msg;
    this.code = code;
  }

  public ServiceException(BaseMsgEnum baseMsgEnum) {
    this(baseMsgEnum.getMsg(), baseMsgEnum.getCode());
  }
}
