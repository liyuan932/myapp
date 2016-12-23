package com.mycompany.myapp.service;

import com.mycompany.myapp.daoobject.UserDO;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.annotation.Resource;

public class UserServiceTest extends BaseTest {
    @Resource
    private UserService userService;

    @Test
    public void addUser() {
        UserDO user = new UserDO();
        user.setAccount("test1");
        user.setPassword("1234");
        user.setUsername("test1");
        user.setStatus(1);
        user.setType(1);
        userService.add(user);

        Assert.assertNotNull(user.getId());
    }
}
