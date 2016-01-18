package com.mycompany.myapp.common.enums;

public enum CommonExceptionEnum {
	FAIL_BIZ_DB_ERROR("FAIL_BIZ_DB_ERROR", "数据库操作失败"), 
	FAIL_BIZ_PARAM_ERROR("FAIL_BIZ_PARAM_ERROR", "业务参数异常"), 
	FAIL_BIZ_SYSTEM_ERROR("FAIL_BIZ_SYSTEM_ERROR", "后端系统异常");
	
	private String code;
	private String msg;
	CommonExceptionEnum(String code,String msg){
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
