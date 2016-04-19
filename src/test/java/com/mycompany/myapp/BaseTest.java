package com.mycompany.myapp;

import com.mycompany.myapp.service.BookService;
import com.mycompany.myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

@ContextConfiguration("classpath:spring-datasource.xml")
public class BaseTest extends AbstractTestNGSpringContextTests {
	
	@Autowired
	private DataSource dataSource;
	@Resource
	private UserService userService;
	@Resource
	private BookService bookService;
	
	@Test(enabled=true)
	public void testDataSource() throws SQLException {
		assertNotNull(dataSource);
		System.out.println(dataSource.getConnection());
		assertNotNull(userService);
		assertNotNull(bookService);
	}


	@Test(enabled=true)
	public void test() throws Exception {

	}

	@Test(description = "单个方法的事务传播机制")
	public void testAddBook()throws Exception{
		bookService.addBook();
	}

	@Test(description = "多个方法调用之间的事务传播机制的行为")
	public void testAddUser()throws Exception{
		userService.addUser();
	}

}
