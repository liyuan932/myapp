package com.mycompany.myapp;

import com.mycompany.myapp.config.DBConfig;
import com.mycompany.myapp.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.sql.DataSource;

/**
 * @author wb-liyuan.j
 * @date 2017-02-24
 */
@ContextConfiguration(classes = DBConfig.class)
public class JavaConfigTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDAO userDAO;

    @Test
    public void test(){
        System.out.println(userDAO.getByAccount("liyuan"));
    }
}
