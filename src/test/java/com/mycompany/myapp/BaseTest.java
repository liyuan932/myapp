package com.mycompany.myapp;

import static org.junit.Assert.*;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;



@ContextConfiguration("classpath:spring-mybatis.xml")
@Transactional
@TransactionConfiguration(defaultRollback=true)
public class BaseTest extends AbstractTransactionalTestNGSpringContextTests {
	
	@Autowired
	private DataSource dataSource;
	
	@Test(enabled=true)
	public void testDataSource() throws SQLException {
		assertNotNull(dataSource);
		System.out.println(dataSource.getConnection());
	}

}
