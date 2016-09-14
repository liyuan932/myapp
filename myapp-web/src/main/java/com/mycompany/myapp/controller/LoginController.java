package com.mycompany.myapp.controller;

import com.mycompany.myapp.daoobject.UserDO;
import com.mycompany.myapp.service.UserService;
import com.mycompany.myapp.utils.CookieUtils;
import com.mycompany.myapp.utils.TokenUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
    public Object login(HttpServletResponse res,String account, String password) {
        try {
            UserDO login = userService.login(account, password);
            CookieUtils.add(res,"token", TokenUtil.generateToken(login.getId(),login.getAccount()));

            return success(login);
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
