package com.mycompany.myapp.enums.category;

public enum UserTypeEnum {
    NORMAL(1, "普通用户"), ADMIN(2, "管理员");

    private int index;
    private String text;

    UserTypeEnum(int index, String text) {
        this.index = index;
        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public String getText() {
        return text;
    }

    public static UserTypeEnum getByIndex(int index) {
        for (UserTypeEnum e : UserTypeEnum.values()) {
            if (e.getIndex() == index) {
                return e;
            }
        }
        return null;
    }

    public static String getTextByIndex(int index) {
        for (UserTypeEnum e : UserTypeEnum.values()) {
            if (e.getIndex() == index) {
                return e.getText();
            }
        }
        return null;
    }
}
