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
	public String exception(HttpServletRequest request, Exception e  ) {
		request.setAttribute("ex", e);
		if (e instanceof ServiceException) {
			return "error/error-business";
		}if (e instanceof IllegalArgumentException) {
			return "error/error-parameter";
		} else if (e instanceof DataAccessException) {
			return "error/error-db";
		} else {
			return "error/error";
		}
	}

	protected <T> Result<T> success(T model) {
		return  new Result<>(model);
	}

	protected Result<?> success() {
		return  new Result<>();
	}

	protected <T> Result<T> fail(CommonMsgEnum commonMsgEnum, Exception e) {
		log.error(commonMsgEnum.getCode(),e);
		return new Result<>(commonMsgEnum.getCode(),commonMsgEnum.getMsg());
	}

	private Result<?> fail(String code, String msg) {
		return new Result<>(code, msg);
	}

}
