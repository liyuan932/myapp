package com.mycompany.myapp.enums.category;

/**
 * 状态枚举
 */
public enum YesOrNoEnum {
    enable(1, "是"), disable(0, "否");

    private int index;
    private String text;

    YesOrNoEnum(int index, String text) {
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
        for (YesOrNoEnum e : YesOrNoEnum.values()) {
            if (e.getIndex() == index) {
                return e.getText();
            }
        }
        return null;
    }
}
