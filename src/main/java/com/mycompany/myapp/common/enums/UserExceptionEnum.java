package com.mycompany.myapp.common.enums;

public enum UserExceptionEnum {
	FAIL_BIZ_USER_NOT_EXIST("FAIL_BIZ_USER_NOT_EXIST", "用户不存在"), 
	FAIL_BIZ_LOGIN_ERROR("FAIL_BIZ_LOGIN_ERROR", "账号或者密码错误");
	
	private String code;
	private String msg;
	UserExceptionEnum(String code,String msg){
		this.setCode(code);
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
