package com.mycompany.myapp.base;

public class BaseResult<T> {
	private String msg;   //错误信息
	private int code;	  //状态码
	private T result;	  //返回结果
	private boolean success = true;   //成功标志
	
	public BaseResult(int code,String msg){
		this.msg = msg;
		this.code = code;
		this.success = false;
	}
	
	public BaseResult(T result){
		this.code = 200;
		this.result = result;
	}
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
}
