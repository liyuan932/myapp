package com.mycompany.myapp.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.google.common.collect.Lists;
import com.mycompany.myapp.base.BaseDAO;
import com.mycompany.myapp.daoobject.BaseDO;
import com.mycompany.myapp.daoobject.OperationLogDO;
import com.mycompany.myapp.daoobject.UserDO;
import com.mycompany.myapp.utils.MybatisMappingUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiringInspection")
public class BaseDAOTest extends BaseTest {

    @Resource
    private UserDAO userDAO;
    @Resource
    private OperationLogDAO operationLogDAO;

    private static String projectPackageName;
    private static String baseJavaDir;

    @Test
    public void testCURD() throws Exception {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-mybatis.xml");

        String curPackageName = MybatisMappingUtils.class.getPackage().getName();
        projectPackageName = curPackageName.substring(0, curPackageName.lastIndexOf("."));

        baseJavaDir = getProjectRoot() + "/myapp-dal/src/main/java/";
        File file = new File(baseJavaDir + getProjectPathName(projectPackageName) + "/dao/");
        if (file.isDirectory() && file.list() != null) {
            for (String filename : file.list()) {
                String daoBeanName = StringUtils.uncapitalize(filename.substring(0, filename.indexOf(".")));
                String doClzName = projectPackageName + ".daoobject." + filename.substring(0, filename.indexOf("DAO"
                    + "."))+"DO";
                CURD((BaseDAO) ctx.getBean(daoBeanName), Class.forName(doClzName));
            }
        }
    }

    private String getProjectPathName(String projectPackageName) {
        return projectPackageName.replaceAll("\\.", "/");
    }

    private String getProjectRoot() {
        String userDir = System.getProperty("user.dir");
        return userDir.substring(0, userDir.lastIndexOf("\\"));
    }

    @DataProvider(name = "data")
    public Object[][] data() {
        return new Object[][] {{userDAO, UserDO.class}, {operationLogDAO, OperationLogDO.class}};
    }

    @Rollback
    @Test(dataProvider = "data")
    public void testCURD(BaseDAO dao, Class<?> clz) throws Exception {
        CURD(dao, clz);
    }


    private void CURD(BaseDAO dao, Class<?> clz) throws Exception {
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

            if(Modifier.isStatic(f.getModifiers())){
                continue;
            }

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

    private List<Long> getIds(List<BaseDO> users) {

        List<Long> ids = Lists.newArrayList();
        for (BaseDO db : users) {
            ids.add(db.getId());
        }
        return ids;
    }
}
