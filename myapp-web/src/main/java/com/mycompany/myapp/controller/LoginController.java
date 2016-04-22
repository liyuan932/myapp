package com.mycompany.myapp.controller;

import com.mycompany.myapp.daoobject.UserDO;
import com.mycompany.myapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller  
@RequestMapping("/user")
public class LoginController extends BaseController {

	@Resource
    private UserService userService;
    
	@RequestMapping("/add")
    @ResponseBody
    public Object addUser(){
        try {
            UserDO user = new UserDO();
            user.setAccount("test");
            user.setPassword("1234");
            user.setUsername("test");
            user.setStatus(1);
            user.setType(1);
            userService.addUser(user);

            return success();
        }catch (Exception e){
            return fail(e);
        }

    }
}
