package com.mycompany.myapp.service.common;

import com.mycompany.myapp.enums.msg.BaseMsgEnum;

public class BizException extends RuntimeException {

  private static final long serialVersionUID = 2515955074355075103L;

  private String code;
  private String msg;

  public String getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }

  public BizException(String msg, String code) {
    super(msg);
    this.msg = msg;
    this.code = code;
  }

  public BizException(BaseMsgEnum baseMsgEnum) {
    this(baseMsgEnum.getMsg(), baseMsgEnum.getCode());
  }
}
