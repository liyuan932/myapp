package com.mycompany.myapp.common.enums.msg;

public enum CommonMsgEnum implements BaseMsgEnum {

    FAIL_BIZ_DB_ERROR("数据库操作失败"),
    FAIL_BIZ_PARAM_ERROR("业务参数异常"),
    FAIL_BIZ_SYSTEM_ERROR("后端系统异常");

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
