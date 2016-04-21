package com.mycompany.myapp.enums.msg;

public enum UserMsgEnum implements BaseMsgEnum {
	FAIL_BIZ_USER_NOT_EXIST("用户不存在"),
	FAIL_BIZ_LOGIN_ERROR("账号或者密码错误");

	private String msg;
	UserMsgEnum(String msg){
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public String getCode(){
		return this.name();
	}
}
