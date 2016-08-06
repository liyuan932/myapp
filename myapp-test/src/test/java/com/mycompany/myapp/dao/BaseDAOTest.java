package com.mycompany.myapp.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.google.common.collect.Lists;
import com.mycompany.myapp.base.BaseDAO;
import com.mycompany.myapp.base.BaseDO;
import com.mycompany.myapp.daoobject.OperationLog;
import com.mycompany.myapp.daoobject.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.test.annotation.Rollback;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiringInspection")
public class BaseDAOTest extends BaseTest {

  @Resource
  private UserDAO userDAO;
  @Resource
  private OperationLogDAO operationLogDAO;

  @DataProvider(name = "data")
  public Object[][] data() {
    return new Object[][]{
        {userDAO, User.class},
        {operationLogDAO, OperationLog.class}
    };
  }

  @Rollback
  @Test(dataProvider = "data")
  public void testCURD(BaseDAO dao, Class<?> clz) throws Exception {
    BaseDO db = initDB(clz);
    dao.insert(db);

    Long id = db.getId();
    Assert.assertNotNull(id);

    db = (BaseDO) dao.getById(id);
    Assert.assertNotNull(db);

    db.setGmtCreate(new Date());
    Assert.assertEquals(dao.update(db), 1);
    Assert.assertEquals(dao.delete(id), 1);

    List<BaseDO> list = Lists.newArrayList();
    for (int i = 0; i < 10; i++) {
      BaseDO db2 = initDB(clz);
      list.add(db2);
    }
    Assert.assertEquals(dao.batchInsert(list), 10);

    BaseDO query = (BaseDO) clz.newInstance();
    List<BaseDO> dbs = dao.queryList(query);
    Assert.assertEquals(dbs.size(), 10);
    Assert.assertEquals(dao.count(query), 10);
    Assert.assertEquals(dao.queryPage(query, new PageBounds(2, 3)).getPaginator().getTotalCount(), 10);

    List<Long> ids = getIds(dbs);
    Assert.assertEquals(dao.queryByIds(ids).size(), 10);
    Assert.assertEquals(dao.batchDelete(ids), 10);
  }

  private BaseDO initDB(Class<?> clz) throws Exception {
    BaseDO db = (BaseDO) clz.newInstance();
    for (Field f : clz.getDeclaredFields()) {

      Object arg = null;
      Class<?> type = f.getType();
      if (String.class == type) {
        arg = RandomStringUtils.random(5, "abcdefghijklmnopqrstuvwxyz");
      } else if (Date.class == type) {
        arg = new Date();
      } else if (Integer.class == type) {
        arg = RandomUtils.nextInt(0, 100);
      } else if (Double.class == type) {
        arg = RandomUtils.nextDouble(0, 100);
      } else if (Long.class == type) {
        arg = RandomUtils.nextLong(0, 100);
      }

      new PropertyDescriptor(f.getName(), clz).getWriteMethod().invoke(db, arg);
    }
    return db;
  }

  public static void main(String[] args) throws Exception {
    System.out.println(new BaseDAOTest().initDB(User.class));
  }

  private List<Long> getIds(List<BaseDO> users) {

    List<Long> ids = Lists.newArrayList();
    for (BaseDO db : users) {
      ids.add(db.getId());
    }
    return ids;
  }
}
