package com.mycompany.myapp.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mycompany.myapp.common.enums.common.CommonMsgEnum;
import com.mycompany.myapp.common.enums.UserMsgEnum;

public class BaseService {
	private static final Logger log = LoggerFactory.getLogger(BaseService.class);
	
	protected <T> BaseResult<T> fail(String code,String msg){
		return new BaseResult<T>(code, msg);
	}
	
	protected <T> BaseResult<T> fail(CommonMsgEnum commonMsgEnum){
		return new BaseResult<T>(commonMsgEnum.getCode(), commonMsgEnum.getMsg());
	}
	
	protected <T> BaseResult<T> fail(CommonMsgEnum commonMsgEnum, Exception e){
		switch (commonMsgEnum) {
		case FAIL_BIZ_PARAM_ERROR:
			log.info(commonMsgEnum.getMsg(),e);break;
		case FAIL_BIZ_DB_ERROR:
			log.warn(commonMsgEnum.getMsg(),e);break;
		case FAIL_BIZ_SYSTEM_ERROR:
			log.error(commonMsgEnum.getMsg(),e);break;
		}
		
		return new BaseResult<T>(commonMsgEnum.getCode(), commonMsgEnum.getMsg());
	}
	
	protected <T> BaseResult<T> fail(UserMsgEnum userExceptionEnum){
		return new BaseResult<T>(userExceptionEnum.getCode(), userExceptionEnum.getMsg());
	}
	
	protected <T> BaseResult<T> success(T info){
		return new BaseResult<T>(info);
	}
}
