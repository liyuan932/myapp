package com.mycompany.myapp.query;

import com.mycompany.myapp.base.BaseQuery;

public class UserQuery extends BaseQuery {
	
	private String account;
	private String username;
	
	public UserQuery() {
	}
	
	public UserQuery(String account, String username) {
		this.account = account;
		this.username = username;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
