package com.mycompany.myapp.dao;



import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.google.common.collect.Lists;
import com.mycompany.myapp.BaseTest;
import com.mycompany.myapp.domain.UserDO;
import com.mycompany.myapp.query.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class UserDaoTest extends BaseTest {

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private UserDao userDao;

	@Test
	public void testCURD() {
		UserDO db = new UserDO("test","1234","test",1,1);
		userDao.insert(db);

		Long id = db.getId();
		Assert.assertNotNull(id);

		db = userDao.getById(id);
		Assert.assertNotNull(db);

		db.setAccount("test2");
		int num = userDao.update(db);
		Assert.assertEquals(num,1);

		num = userDao.delete(id);
		Assert.assertEquals(num,1);
	}

	@Test
	public void testBatchInsert(){
		List<UserDO> uList = Lists.newArrayList();
		for(int i=100;i<=109;i++){
			uList.add(new UserDO("test"+i,"1234","test"+i,1,1));
		}

		Assert.assertEquals(10,userDao.batchInsert(uList));
	}

	@Test
	public void testBatchDelete(){
		Assert.assertEquals(userDao.batchDelete(Arrays.asList(38L,39L,40L)),3);
	}

	@Test
	public void testSelectList(){
		Assert.assertEquals(userDao.queryList(new UserQuery("test9", "test9")).size(),1);
		Assert.assertEquals(userDao.queryList(new UserQuery("test", null)).size(),10);
		Assert.assertEquals(userDao.queryList(new UserQuery("test32", null)).size(),0);
	}

	@Test
	public void testPageList(){
		PageList<UserDO> pageList = userDao.queryPage(new UserQuery("test", null), new PageBounds(3, 3,Order.formString("id.asc")));
		Assert.assertEquals(pageList.size(), 3);
		Assert.assertEquals(pageList.getPaginator().getTotalCount(),10);
		Assert.assertEquals(pageList.getPaginator().getTotalPages(), 4);
	}

	@Test
	public void testCount(){
		Assert.assertEquals(userDao.count(new UserQuery("test", null)),10);
	}

	@Test
	public void testUpdateStatus(){
		Assert.assertEquals(userDao.updateStatus(38L, 0),1);
		Assert.assertEquals(userDao.getById(38L).getStatus().intValue(),0);
	}

	@Test
	public void testBatchUpdateStatus(){
		Assert.assertEquals(userDao.batchUpdateStatus(Arrays.asList(38L,39L,40L), 0),3);
	}


	@Test
	public void testCheckAccountPassword() {
		Assert.assertNotNull(userDao.checkAccountPassword("test1","1234"));
	}
}
