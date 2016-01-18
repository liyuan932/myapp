package com.mycompany.myapp.common.enums;

public enum StatusEnum {
	enable(1,"启用"),disable(0,"禁用");
	
	private int index;
	private String text;
	private StatusEnum(int index,String text){
		this.index = index;
		this.text = text;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public static String getTextByIndex(int index){
		for(StatusEnum e : StatusEnum.values()){
			if(e.getIndex() == index){
				return e.getText();
			}
		}
		return null;
	}
	
}
