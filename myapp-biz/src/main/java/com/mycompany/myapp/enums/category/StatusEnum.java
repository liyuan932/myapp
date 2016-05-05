package com.mycompany.myapp.enums.category;

public enum StatusEnum {
  ENABLE(1, "启用"), DISABLE(0, "禁用");
  private int index;
  private String text;

  StatusEnum(int index, String text) {
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
   * 根据index获取enum.
   */
  public static StatusEnum getByIndex(int index) {
    for (StatusEnum e : StatusEnum.values()) {
      if (e.getIndex() == index) {
        return e;
      }
    }
    return null;
  }

  /**
   * 根据index获取text 阿斯蒂芬as  ads fas a sd asd sda asdf sdaf sadf sdf sdfsda fasd fasd fasd fsd fda fsda asdf asdfassadsada
   * dsfsdaf asdfsdaf asdfsdaf asda a asdf  asd sd asd asd a aa sd
   */
  public static String getTextByIndex(int index) {
    for (StatusEnum e : StatusEnum.values()) {
      if (e.getIndex() == index) {
        return e.getText();
      }
    }
    return null;
  }

}
