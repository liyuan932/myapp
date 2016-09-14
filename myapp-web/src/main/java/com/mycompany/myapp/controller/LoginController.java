package com.mycompany.myapp.controller;

import com.mycompany.mapp.dto.LoginDTO;
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

    /**
     * 用户登录
     */
    @RequestMapping("/login")
    @ResponseBody
    public Object login(String account, String password) {
        try {
            return success(userService.login(account, password));
        } catch (Exception ex) {
            return fail(ex);
        }
    }

    @RequestMapping("/login2")
    @ResponseBody
    public Object login2(String account, String password) {
        try {
            LoginDTO dto = new LoginDTO();
            dto.setAccount(account);
            dto.setPassword(password);
            userService.login(dto);
            return success();
        } catch (Exception ex) {
            return fail(ex);
        }
    }

    /**
     * 用户登出
     */
    @RequestMapping("/logout")
    @ResponseBody
    public Object logout(String account) {
        try {
            userService.logout(account);
            return success();
        } catch (Exception ex) {
            return fail(ex);
        }
    }
}
