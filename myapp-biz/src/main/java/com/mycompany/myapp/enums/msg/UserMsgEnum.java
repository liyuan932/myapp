package com.mycompany.myapp.enums.msg;

public enum UserMsgEnum implements BaseMsgEnum {

  FAIL_BIZ_NO_USER("用户不存在"),   //
  FAIL_BIZ_LOGIN("账号或者密码错误"), //
  FAIL_BIZ_ADD_USER("添加用户失败");

  private String msg;

  UserMsgEnum(String msg) {
    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }

  public String getCode() {
    return this.name();
  }
}
