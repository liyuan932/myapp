package com.mycompany.myapp.dao;



import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.google.common.collect.Lists;
import com.mycompany.myapp.common.enums.category.StatusEnum;
import com.mycompany.myapp.daoobject.UserDO;
import com.mycompany.myapp.query.UserQuery;
import com.mycompany.myapp.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;

public class UserDaoTest extends BaseTest {

	@Autowired
	private UserDAO dao;

	@Test
	public void testCURD() {
		UserDO db = new UserDO();
		db.setAccount("test");
		db.setPassword("1234");
		db.setUsername("test");
		db.setStatus(1);
		db.setType(1);
		dao.insert(db);

		Long id = db.getId();
		Assert.assertNotNull(id);

		db = dao.getById(id);
		Assert.assertNotNull(db);

		db.setGmtModified(new Date());
		Assert.assertEquals(dao.update(db),1);
		Assert.assertEquals(dao.updateStatus(db.getId(), StatusEnum.ENABLE.getIndex()),1);
		Assert.assertEquals(dao.delete(id),1);
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
		Assert.assertEquals(dao.batchInsert(uList),10);

		List<UserDO> userDOs = dao.queryList(new UserQuery());
		Assert.assertEquals(userDOs.size(),10);
		Assert.assertEquals(dao.count(new UserQuery()),10);
		Assert.assertEquals(dao.queryPage(new UserQuery(),new PageBounds(2,3)).getPaginator().getTotalCount(),10);

		List<Long> ids = (List<Long>) BeanUtils.getIds(userDOs);
		Assert.assertEquals(dao.queryByIds(ids),10);
		Assert.assertEquals(dao.batchDelete(ids),10);
		Assert.assertEquals(dao.batchUpdateStatus(ids, StatusEnum.DISABLE.getIndex()),10);
	}
}
