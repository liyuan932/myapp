package com.mycompany.myapp.controller;


import com.mycompany.myapp.query.UserQuery;
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

    @RequestMapping("/queryUser")
    @ResponseBody
    public Object queryUser(UserQuery query){
        try {
            return success(userService.queryUser(query));
        }catch (Exception e){
            return fail(e);
        }
    }


}
