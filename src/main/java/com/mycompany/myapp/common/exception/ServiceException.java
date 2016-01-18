package com.mycompany.myapp.common.exception;

import com.mycompany.myapp.common.enums.CommonExceptionEnum;
import com.mycompany.myapp.common.enums.UserExceptionEnum;

public class ServiceException extends RuntimeException{
	
	private static final long serialVersionUID = 2515955074355075103L;

	private String code;
	
	public ServiceException(){
		super();
	}
	
	public ServiceException(String msg){
		super(msg);
	}
	
	public ServiceException(String msg,String code){
		super(msg);
		this.code = code;
	}
	
	public ServiceException(String msg,Throwable cause){
		super(msg,cause);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public ServiceException(UserExceptionEnum userExceptionEnum){
		 this(userExceptionEnum.getMsg(),userExceptionEnum.getCode());
	}

	public ServiceException(CommonExceptionEnum commonExceptionEnum) {
		this(commonExceptionEnum.getMsg(),commonExceptionEnum.getCode());
	}

}
