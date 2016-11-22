package com.mycompany.myapp.controller;

import com.mycompany.myapp.enums.msg.CommonMsgEnum;
import com.mycompany.myapp.service.common.BizException;
import com.mycompany.myapp.utils.CookieUtils;
import com.mycompany.myapp.utils.TokenUtil;
import com.mycompany.myapp.vo.BaseResult;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class BaseController {
    @Resource
    protected HttpServletRequest req;

    public Long getUserId(){
        return TokenUtil.getUid(CookieUtils.getName(req, "token"));
    }

    public String getAccount(){
        return TokenUtil.getAccount(CookieUtils.getName(req, "token"));
    }

    protected <T> BaseResult<T> success(T model) {
        return new BaseResult<>(model);
    }

    protected BaseResult<?> success() {
        return new BaseResult<>();
    }

    private BaseResult<?> fail(CommonMsgEnum commonMsgEnum, Exception ex) {
        return new BaseResult<>(commonMsgEnum.getCode(), commonMsgEnum.getMsg());
    }

    private BaseResult<?> fail(String code, String msg) {
        return new BaseResult<>(code, msg);
    }

    protected BaseResult<?> fail(Exception ex) {
        if (ex instanceof BizException) {
            return fail(((BizException) ex).getCode(), ex.getMessage());
        } else if (ex instanceof DataAccessException) {
            return fail(CommonMsgEnum.DB_ERROR, ex);
        } else {
            return fail(CommonMsgEnum.SYSTEM_ERROR, ex);
        }
    }
    /**
     * 异常处理
     */
    @ExceptionHandler
    public String exception(HttpServletRequest request, Exception ex) {

        request.setAttribute("ex", ex);
        if (ex instanceof BizException) {
            return "error/error-business";
        } else if (ex instanceof DataAccessException) {
            return "error/error-db";
        } else {
            return "error/error";
        }
    }

}
