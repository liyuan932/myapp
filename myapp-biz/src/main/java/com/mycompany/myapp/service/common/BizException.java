package com.mycompany.myapp.service.common;

public class BizException extends RuntimeException {

    private static final long serialVersionUID = 2515955074355075103L;

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public BizException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
