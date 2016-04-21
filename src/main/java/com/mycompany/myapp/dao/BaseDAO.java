package com.mycompany.myapp.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.mycompany.myapp.daoobject.BaseDO;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

public interface BaseDAO<T,PK extends Serializable> {
    void insert(T db);
    int batchInsert(List<T> list);

    int update(T db);

    int delete(PK id);
    int batchDelete(List<PK> list);

    T getById(PK id);
    List<T> queryByIds(List<PK> list);

    List<T> queryList(BaseDO query);
    int count(BaseDO query);

    PageList<T> queryPage(BaseDO query, PageBounds pb);

    int updateStatus(@Param("id")PK id,@Param("status")int status);
    int batchUpdateStatus(@Param("list")List<PK> list,@Param("status")int status);
}
