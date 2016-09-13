package com.mycompany.myapp.service;

import com.mycompany.myapp.daoobject.UserDO;
import org.springframework.test.annotation.Rollback;
import org.testng.annotations.Test;

import javax.annotation.Resource;

public class UserServiceTest extends BaseTest {

    @Resource
    UserService userService;

    @Test
    @Rollback(false)
    public void getUserById() {
        UserDO user1 = userService.getById(284L);
        userService.update(user1);
        UserDO user2 = userService.getById(284L);
    }
}
