package com.mycompany.mapp.result;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class BaseResult<T> implements Serializable {
    private static final long serialVersionUID = -3015467376359700226L;
    /**
     * 错误代码
     */
    private String code;
    /**
     * 错误信息
     */
    private String msg;

    /**
     * 返回结果
     */
    private T model;
    /**
     * 成功标志
     */
    private Boolean success = true;

    public BaseResult(String code, String msg) {
        this.success = false;
        this.msg = msg;
        this.code = code;
    }

    public BaseResult() {
    }

    public BaseResult(T model) {
        this.model = model;
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

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
