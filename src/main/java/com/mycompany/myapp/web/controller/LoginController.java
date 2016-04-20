package com.mycompany.myapp.web.controller;

import com.mycompany.myapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller  
@RequestMapping("/")
public class LoginController extends BaseController {

	@Resource
    private UserService userService;
    
	@RequestMapping("/login")
    @ResponseBody
    public Object login(String account,String password){
		return userService.login(account,password);
    }
}
