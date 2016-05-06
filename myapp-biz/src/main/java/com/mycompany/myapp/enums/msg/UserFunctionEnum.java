package com.mycompany.myapp.enums.msg;

/**
 * 用户功能枚举
 *
 * @author Administrator
 * @date 2016-05-07
 */
public enum UserFunctionEnum implements SecondaryFunctionEnum{
  ADD_USER("add user"),UPDATE_USER("update user"),DELETE_USER("delete user");

  private String msg;
  UserFunctionEnum(String msg) {
    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }
}
