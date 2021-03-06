package com.mycompany.myapp.dao;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

@TransactionConfiguration
@ContextConfiguration("classpath:spring-mybatis.xml")
public class BaseTest extends AbstractTransactionalTestNGSpringContextTests {

    @Resource
    private DataSource dataSource;

    @Test
    public void testDataSource() throws SQLException {
        Assert.assertNotNull(dataSource);
        System.out.println(dataSource.getConnection());
    }

    public static void main(String[] args) {
        Integer a = 273;
        Integer b = 273;
        System.out.println(a == b);
    }
}
