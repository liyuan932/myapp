package com.mycompany.myapp.common.exception;

import com.mycompany.myapp.base.BaseMsgEnum;

public class BizException extends RuntimeException{
	private static final long serialVersionUID = 2515955074355075103L;

	private String code;

	public BizException(String msg){
		super(msg);
	}

	public BizException(String msg, String code){
		super(msg);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public BizException(BaseMsgEnum baseMsgEnum){
		 this(baseMsgEnum.getMsg(),baseMsgEnum.getCode());
	}
}
