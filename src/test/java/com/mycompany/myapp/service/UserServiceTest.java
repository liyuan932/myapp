package com.mycompany.myapp.service;



import com.mycompany.myapp.dao.BaseTest;
import org.testng.annotations.Test;

import javax.annotation.Resource;

public class UserServiceTest extends BaseTest {

	@Resource
	UserService userService;
	
	@Test
	public void login() throws Exception{
		userService.login("test","1234");
	}

}
