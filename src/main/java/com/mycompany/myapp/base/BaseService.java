package com.mycompany.myapp.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycompany.myapp.common.enums.CommonExceptionEnum;
import com.mycompany.myapp.common.enums.UserExceptionEnum;

public class BaseService {
	private static final Logger log = LoggerFactory.getLogger(BaseService.class);
	
	protected <T> Result<T> fail(String code,String msg){
		return new Result<T>(code, msg);
	}
	
	protected <T> Result<T> fail(CommonExceptionEnum commonExceptionEnum){
		return new Result<T>(commonExceptionEnum.getCode(), commonExceptionEnum.getMsg());
	}
	
	protected <T> Result<T> fail(CommonExceptionEnum commonExceptionEnum,Exception e){
		switch (commonExceptionEnum) {
		case FAIL_BIZ_PARAM_ERROR:
			log.info(commonExceptionEnum.getMsg(),e);break;
		case FAIL_BIZ_DB_ERROR:
			log.warn(commonExceptionEnum.getMsg(),e);break;
		case FAIL_BIZ_SYSTEM_ERROR:
			log.error(commonExceptionEnum.getMsg(),e);break;
		}
		
		return new Result<T>(commonExceptionEnum.getCode(), commonExceptionEnum.getMsg());
	}
	
	protected <T> Result<T> fail(UserExceptionEnum userExceptionEnum){
		return new Result<T>(userExceptionEnum.getCode(), userExceptionEnum.getMsg());
	}
	
	protected <T> Result<T> success(T info){
		return new Result<T>(info);
	}
}
