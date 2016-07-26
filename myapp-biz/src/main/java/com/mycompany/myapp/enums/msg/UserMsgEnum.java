package com.mycompany.myapp.enums.msg;

public enum UserMsgEnum implements BaseMsgEnum {

	FAIL_BIZ_ACCOUNT_IS_NULL("用户名不能为空"), //
	FAIL_BIZ_PASSWORD_IS_NULL("密码不能为空"), //
	FAIL_BIZ_USER_NOT_EXIST("用户%s不存在"), //
  FAIL_BIZ_LOGIN_ERROR("账号或者密码错误"), //
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
