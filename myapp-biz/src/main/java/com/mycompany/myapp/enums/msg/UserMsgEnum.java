package com.mycompany.myapp.enums.msg;

public enum UserMsgEnum implements BaseMsgEnum {

    USER_NOT_EXIST("用户%s不存在"), //
    ACCOUNT_OR_PASSWORD_ERROR("账号者密码错误"), //
    ADD_USER_ERROR("添加用户失败");

    private String msg;

    UserMsgEnum(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return this.name();
    }
}
