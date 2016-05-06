package com.mycompany.myapp.controller;

import com.mycompany.myapp.daoobject.User;
import com.mycompany.myapp.query.UserQuery;
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

  /**
   * 添加用户.
   */
  @RequestMapping("/add")
  @ResponseBody
  public Object addUser() {
    try {
      User user = new User();
      user.setAccount("test");
      user.setPassword("1234");
      user.setUsername("test");
      user.setStatus(1);
      user.setType(1);
      userService.addUser(user);

      return success();
    } catch (Exception ex) {
      return fail(ex);
    }
  }

  /**
   * 用户列表.
   */
  @RequestMapping("/list")
  @ResponseBody
  public Object listUser(UserQuery query) {
    try {
      return success(userService.queryUser(query));
    } catch (Exception ex) {
      return fail(ex);
    }
  }
}
