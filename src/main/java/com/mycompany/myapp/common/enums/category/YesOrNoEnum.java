package com.mycompany.myapp.common.enums.category;

public enum YesOrNoEnum {
	enable(1,"是"),disable(0,"否");

	private int index;
	private String text;

	YesOrNoEnum(int index, String text){
		this.index = index;
		this.text = text;
	}
	public int getIndex() {
		return index;
	}
	public String getText() {
		return text;
	}

	public static String getTextByIndex(int index){
		for(YesOrNoEnum e : YesOrNoEnum.values()){
			if(e.getIndex() == index){
				return e.getText();
			}
		}
		return null;
	}
	
}
