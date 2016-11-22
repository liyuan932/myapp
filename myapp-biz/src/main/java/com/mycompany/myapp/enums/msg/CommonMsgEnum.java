package com.mycompany.myapp.enums.msg;

public enum CommonMsgEnum implements BaseMsgEnum {

    DB_ERROR("数据库异常"), //
    PARAM_ERROR("参数异常"), //
    SYSTEM_ERROR("后端系统异常"); //

    private String msg;

    CommonMsgEnum(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return this.name();
    }

    public String getMsg() {
        return msg;
    }
}
