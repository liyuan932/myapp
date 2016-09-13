package com.mycompany.myapp.enums.category;

/**
 * 状态枚举
 */
public enum OperationTypeEnum {
    BASIC_DATA(1, "基础数据"), BIZ_DATA(2, "业务数据");

    private int index;
    private String text;

    OperationTypeEnum(int index, String text) {
        this.index = index;
        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public String getText() {
        return text;
    }

    /**
     * 根据索引获取状态文本
     */
    public static String getTextByIndex(int index) {
        for (OperationTypeEnum e : OperationTypeEnum.values()) {
            if (e.getIndex() == index) {
                return e.getText();
            }
        }
        return null;
    }
}
