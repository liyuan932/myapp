package com.mycompany.myapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;



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


	@Test(enabled=true)
	public void test() throws Exception {

	}

}
