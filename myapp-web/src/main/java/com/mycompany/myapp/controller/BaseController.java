package com.mycompany.myapp.controller;

import com.mycompany.myapp.enums.msg.CommonMsgEnum;
import com.mycompany.myapp.service.common.BizException;
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

  /**
   *异常处理
   */
  @ExceptionHandler
  public String exception(HttpServletRequest request, Exception ex) {

    request.setAttribute("ex", ex);
    if (ex instanceof BizException) {
      return "error/error-business";
    } else if (ex instanceof DataAccessException) {
      log.error(CommonMsgEnum.FAIL_BIZ_DB_ERROR.getMsg(), ex);
      return "error/error-db";
    } else {
      log.error(CommonMsgEnum.FAIL_BIZ_SYSTEM_ERROR.getMsg(), ex);
      return "error/error";
    }
  }

  protected <T> Result<T> success(T model) {
    return new Result<>(model);
  }

  protected Result<?> success() {
    return new Result<>();
  }

  protected Result<?> fail(CommonMsgEnum commonMsgEnum, Exception ex) {
    log.error(commonMsgEnum.getMsg(), ex);
    return new Result<>(commonMsgEnum.getCode(), commonMsgEnum.getMsg());
  }

  protected Result<?> fail(String code, String msg) {
    return new Result<>(code, msg);
  }

  protected Result<?> fail(Exception ex) {


    if (ex instanceof BizException) {
      return fail(((BizException) ex).getCode(), ex.getMessage());
    } else if (ex instanceof DataAccessException) {
      return fail(CommonMsgEnum.FAIL_BIZ_DB_ERROR, ex);
    } else {
      return fail(CommonMsgEnum.FAIL_BIZ_SYSTEM_ERROR, ex);
    }
  }
}
