package com.mycompany.myapp.enums.category;

/**
 * 状态枚举
 */
public enum LogOperationTypeEnum {
    BASIC_DATA(1, "基础数据"), BIZ_DATA(2, "业务数据");

    private Integer index;
    private String text;

    LogOperationTypeEnum(Integer index, String text) {
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
        for (LogOperationTypeEnum e : LogOperationTypeEnum.values()) {
            if (e.getIndex().equals(index)) {
                return e.getText();
            }
        }
        return null;
    }
}
