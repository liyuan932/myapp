package com.mycompany.myapp.dao;

import com.google.common.collect.Lists;

import com.mycompany.myapp.daoobject.User;
import com.mycompany.myapp.query.UserQuery;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

public class UserDaoTest extends BaseTest {


	@Resource
	private UserDao userDao;

	@Test
	public void testCURD() {
		User db = new User();
		db.setAccount("test");
		db.setPassword("1234");
		db.setUsername("test");
		db.setStatus(1);
		db.setType(1);
		userDao.insert(db);

		Long id = db.getId();
		Assert.assertNotNull(id);

		db = userDao.getById(id);
		Assert.assertNotNull(db);

		db.setGmtModified(new Date());
		Assert.assertEquals(userDao.update(db),1);
		Assert.assertEquals(userDao.updateStatus(db.getId(), 0),1);
		Assert.assertEquals(userDao.delete(id),1);
	}

	@Test
	public void testBatch(){
		List<User> uList = Lists.newArrayList();
		for(int i=11;i<=20;i++){
			User db = new User();
			db.setAccount("test"+i);
			db.setPassword("1234");
			db.setUsername("test"+i);
			db.setStatus(1);
			db.setType(1);
			uList.add(db);
		}
		Assert.assertEquals(userDao.batchInsert(uList),10);

		List<User> users = userDao.queryList(new UserQuery());
		Assert.assertEquals(users.size(),10);
		Assert.assertEquals(userDao.count(new UserQuery()),10);
		Assert.assertEquals(userDao.queryPage(new UserQuery(),new PageBounds(2,3)).getPaginator().getTotalCount(),10);

		List<Long> ids = getIds(users);
		Assert.assertEquals(userDao.queryByIds(ids).size(),10);
		Assert.assertEquals(userDao.batchUpdateStatus(ids, 0),10);
		Assert.assertEquals(userDao.batchDelete(ids),10);
	}


	private List<Long> getIds(List<User> users){

		List<Long> ids = Lists.newArrayList();
		for(User u : users){
			ids.add(u.getId());
		}
		return ids;
	}
}
