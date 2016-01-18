package com.mycompany.myapp.controller;

import com.mycompany.myapp.base.BaseController;
import com.mycompany.myapp.domain.UserDO;
import com.mycompany.myapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller  
@RequestMapping("/user")
public class UserController extends BaseController{

	@Resource
    private UserService userService;
    
	@RequestMapping("/addUser")  
    @ResponseBody
    public Object addUser() throws Exception{
		return userService.addUser(new UserDO());
    }
}
