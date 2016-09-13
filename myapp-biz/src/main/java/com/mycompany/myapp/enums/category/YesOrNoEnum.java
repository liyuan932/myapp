package com.mycompany.myapp.enums.category;

/**
 * 状态枚举
 */
public enum YesOrNoEnum {
    Yes(1, "是"), NO(0, "否");

    private Integer index;
    private String text;

    YesOrNoEnum(Integer index, String text) {
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
        for (YesOrNoEnum e : YesOrNoEnum.values()) {
            if (e.getIndex().equals(index)) {
                return e.getText();
            }
        }
        return null;
    }
}
