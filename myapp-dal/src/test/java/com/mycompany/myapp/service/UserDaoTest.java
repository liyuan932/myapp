package com.mycompany.myapp.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.google.common.collect.Lists;
import com.mycompany.myapp.dao.UserDAO;
import com.mycompany.myapp.daoobject.UserDO;
import com.mycompany.myapp.query.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;

public class UserDAOTest extends BaseTest {

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private UserDAO userDAO;

	@Test
	public void testCURD() {
		UserDO db = new UserDO();
		db.setAccount("test");
		db.setPassword("1234");
		db.setUsername("test");
		db.setStatus(1);
		db.setType(1);
		userDAO.insert(db);

		Long id = db.getId();
		Assert.assertNotNull(id);

		db = userDAO.getById(id);
		Assert.assertNotNull(db);

		db.setGmtModified(new Date());
		Assert.assertEquals(userDAO.update(db),1);
		Assert.assertEquals(userDAO.updateStatus(db.getId(), 0),1);
		Assert.assertEquals(userDAO.delete(id),1);
	}

	@Test
	public void testBatch(){
		List<UserDO> uList = Lists.newArrayList();
		for(int i=11;i<=20;i++){
			UserDO db = new UserDO();
			db.setAccount("test"+i);
			db.setPassword("1234");
			db.setUsername("test"+i);
			db.setStatus(1);
			db.setType(1);
			uList.add(db);
		}
		Assert.assertEquals(userDAO.batchInsert(uList),10);

		List<UserDO> userDOs = userDAO.queryList(new UserQuery());
		Assert.assertEquals(userDOs.size(),10);
		Assert.assertEquals(userDAO.count(new UserQuery()),10);
		Assert.assertEquals(userDAO.queryPage(new UserQuery(),new PageBounds(2,3)).getPaginator().getTotalCount(),10);

		List<Long> ids = getIds(userDOs);
		Assert.assertEquals(userDAO.queryByIds(ids).size(),10);
		Assert.assertEquals(userDAO.batchUpdateStatus(ids, 0),10);
		Assert.assertEquals(userDAO.batchDelete(ids),10);
	}


	private List<Long> getIds(List<UserDO> userDOs){

		List<Long> ids = Lists.newArrayList();
		for(UserDO u : userDOs){
			ids.add(u.getId());
		}
		return ids;
	}
}
