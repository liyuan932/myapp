package com.mycompany.myapp.service;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("userService")
public class UserService {
    public static final String ADD_USER="insert into user(id,name) values(1,'duck')";

    @Resource
    private BookService bs;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRED)
    public void addUser()throws Exception {
        this.jdbcTemplate.execute(ADD_USER);
        try {
            //this.bs.addBook();
        }catch (Exception e){
            e.printStackTrace();
        }
       this.bs.addBook();

        this.jdbcTemplate.execute(ADD_USER);
        //throw new Exception("跳出执行");
       // throw new BizException("跳出执行");
    }
}