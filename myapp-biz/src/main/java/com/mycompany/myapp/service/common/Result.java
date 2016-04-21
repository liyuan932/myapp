package com.mycompany.myapp.service.common;

public class Result<T> {
	private String msg;   //错误信息
	private String code;	  //状态码
	private T model;	  //返回结果
	private Boolean flag = true;   //成功标志

	public Result(String code, String msg){
		this.flag = false;
		this.msg = msg;
		this.code = code;
	}

	public Result(){
	}

	public Result(T model){
		this.model = model;
	}

	public boolean isSuccess() {
		return flag;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
