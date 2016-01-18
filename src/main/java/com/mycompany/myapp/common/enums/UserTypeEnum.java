package com.mycompany.myapp.common.enums;

public enum UserTypeEnum {
	normal(1,"普通用户"),admin(2,"管理员");
	
	private int index;
	private String text;
	private UserTypeEnum(int index,String text){
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
		for(UserTypeEnum e : UserTypeEnum.values()){
			if(e.getIndex() == index){
				return e.getText();
			}
		}
		return null;
	}
}
