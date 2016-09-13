package com.mycompany.myapp.enums.category;

/**
 * 用户类型枚举
 */
public enum UserTypeEnum {
    NORMAL(1, "普通用户"), ADMIN(2, "管理员");

    private Integer index;
    private String text;

    UserTypeEnum(Integer index, String text) {
        this.index = index;
        this.text = text;
    }

    public Integer getIndex() {
        return index;
    }

    public String getText() {
        return text;
    }

    /**
     * 根据索引获取用户类型文本
     */
    public static String getTextByIndex(Integer index) {
        for (UserTypeEnum e : UserTypeEnum.values()) {
            if (e.getIndex().equals(index)) {
                return e.getText();
            }
        }
        return null;
    }
}