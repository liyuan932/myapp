package com.mycompany.myapp.enums.function;

/**
 * 次要的功能枚举
 *
 * @author Administrator
 * @date 2016-05-07
 */
public enum ActionEnum {
    DEFAULT(0,"默认"),//

    //用户行为
    USER_LOGIN(1,"登录"),USER_LOGOUT(2,"登出")//
    ;
    private Integer index;
    private String text;
    ActionEnum(Integer index, String text) {
        this.index = index;
        this.text = text;
    }

    public Integer getIndex() {
        return index;
    }

    public String getText() {
        return text;
    }

    public static String getTextByIndex(Integer index) {
        for (ActionEnum e : ActionEnum.values()) {
            if (e.getIndex().equals(index)) {
                return e.getText();
            }
        }
        return null;
    }
}

