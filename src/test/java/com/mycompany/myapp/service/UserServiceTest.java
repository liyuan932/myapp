package com.mycompany.myapp.service;



import javax.annotation.Resource;

import org.testng.annotations.Test;

import com.mycompany.myapp.BaseTest;
import com.mycompany.myapp.domain.UserDO;

public class UserServiceTest extends BaseTest {

	@Resource
	UserService userService;
	
	@Test
	public void testAddUser() throws Exception{
		userService.addUser(new UserDO());
	}

	/*@Test
	public void testCURD() {
		UserDO db = new UserDO("test","1234","test");
		int num = userService.insert(db);
		Assert.assertEquals(num,1);
		
		Long id = db.getId();
		Assert.assertNotNull(id);
		
		db = userService.selectById(id);
		Assert.assertNotNull(db);
		
		db.setAccount("test2");
		num = userService.update(db);
		Assert.assertEquals(num,1);
		
		num = userService.deleteById(id);
		Assert.assertEquals(num,1);
	}
	
	@Test
	public void testBatchInsert(){
		List<UserDO> uList = Lists.newArrayList();
		for(int i=100;i<=109;i++){
			uList.add(new UserDO("test"+i,"1234","test"+i));
		}
		
		Assert.assertEquals(10,userService.batchInsert(uList));
	}
	
	@Test
	public void testBatchDelete(){
		Assert.assertEquals(userService.batchDelete(Arrays.asList(38L,39L,40L)),3);
	}
	
	@Test
	public void testSelectList(){
		Assert.assertEquals(userService.selectList(new UserQuery("test9", "test9")).size(),1);
		Assert.assertEquals(userService.selectList(new UserQuery("test", null)).size(),10);
		Assert.assertEquals(userService.selectList(new UserQuery("test32", null)).size(),0);
	}
	
	@Test
	public void testPageList(){
		PageList<UserDO> pageList = userService.pageList(new UserQuery("test", null), new PageBounds(3, 3,Order.formString("id.asc")));
		Assert.assertEquals(pageList.size(), 3);
		Assert.assertEquals(pageList.getPaginator().getTotalCount(),10);
		Assert.assertEquals(pageList.getPaginator().getTotalPages(), 4);
	}
	
	@Test
	public void testCount(){
		Assert.assertEquals(userService.count(new UserQuery("test", null)),10);
	}
	
	@Test
	public void testUpdateStatus(){
		Assert.assertEquals(userService.updateStatus(38L, 0),1);
		Assert.assertEquals(userService.selectById(38L).getStatus().intValue(),0);
	}
	
	@Test
	public void testBatchUpdateStatus(){
		Assert.assertEquals(userService.batchUpdateStatus(Arrays.asList(38L,39L,40L), 0),3);
	}
	
	
	@Test
	public void testCheckAccountPassword() {
		Assert.assertNotNull(userService.checkAccountPassword("test1","1234"));
	}*/
}
