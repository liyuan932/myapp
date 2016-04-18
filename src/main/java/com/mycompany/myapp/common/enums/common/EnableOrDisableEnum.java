package com.mycompany.myapp.common.enums.common;

public enum EnableOrDisableEnum {
	ENABLE(1,"启用"),DISABLE(0,"禁用");
	
	private int index;
	private String text;

	EnableOrDisableEnum(int index, String text){
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
		for(EnableOrDisableEnum e : EnableOrDisableEnum.values()){
			if(e.getIndex() == index){
				return e.getText();
			}
		}
		return null;
	}
	
}
