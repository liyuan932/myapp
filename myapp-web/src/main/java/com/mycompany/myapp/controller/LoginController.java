package com.mycompany.myapp.controller;

import com.mycompany.myapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/")
public class LoginController extends BaseController {

    @Resource
    private UserService userService;



    /**
     * 用户登录
     */
    @RequestMapping("/login")
    @ResponseBody
    public Object login(@RequestParam String account, String password) {
        try {
            return success(userService.login(account, password));
        } catch (Exception ex) {
            return fail(ex);
        }
    }
}
