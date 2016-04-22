package com.mycompany.myapp.controller;

import com.mycompany.myapp.enums.msg.CommonMsgEnum;
import com.mycompany.myapp.service.common.ServiceException;
import com.mycompany.myapp.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BaseController {
	@Resource
	protected HttpServletRequest request;
	@Resource
	protected HttpSession session;

	private static final Logger log = LoggerFactory.getLogger(BaseController.class);

	@ExceptionHandler
	public String exception(HttpServletRequest request, Exception e ) {
		request.setAttribute("ex", e);
		if (e instanceof ServiceException) {
			return "error/error-business";
		}if (e instanceof IllegalArgumentException) {
			log.error(CommonMsgEnum.FAIL_BIZ_PARAM_ERROR.getMsg(),e);
			return "error/error-parameter";
		} else if (e instanceof DataAccessException) {
			log.error(CommonMsgEnum.FAIL_BIZ_DB_ERROR.getMsg(),e);
			return "error/error-db";
		} else {
			log.error(CommonMsgEnum.FAIL_BIZ_SYSTEM_ERROR.getMsg(),e);
			return "error/error";
		}
	}

	protected <T> Result<T> success(T model) {
		return  new Result<>(model);
	}

	protected Result<?> success() {
		return  new Result<>();
	}

	protected Result<?> fail(CommonMsgEnum commonMsgEnum, Exception e) {
		log.error(commonMsgEnum.getMsg(),e);
		return new Result<>(commonMsgEnum.getCode(),commonMsgEnum.getMsg());
	}

	protected Result<?> fail(String code, String msg) {
		return new Result<>(code, msg);
	}

	protected Result<?> fail(Exception e) {
		if (e instanceof ServiceException) {
			return fail(((ServiceException)e).getCode(),e.getMessage());
		}if (e instanceof IllegalArgumentException) {
			return fail(CommonMsgEnum.FAIL_BIZ_PARAM_ERROR,e);
		} else if (e instanceof DataAccessException) {
			return fail(CommonMsgEnum.FAIL_BIZ_DB_ERROR,e);
		} else {
			return fail(CommonMsgEnum.FAIL_BIZ_SYSTEM_ERROR,e);
		}
	}
}
