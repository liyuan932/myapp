package com.mycompany.myapp.service;

import com.mycompany.myapp.daoobject.User;

import org.testng.Assert;
import org.testng.annotations.Test;

import javax.annotation.Resource;

public class UserServiceTest extends BaseTest {
  @Resource
  private UserService userService;

  @Test
  public void addUser() {
    User user = new User();
    user.setAccount("test");
    user.setPassword("1234");
    user.setUsername("test");
    user.setStatus(1);
    user.setType(1);
    userService.add(user);

    Assert.assertNotNull(user.getId());
  }
}
