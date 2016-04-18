package com.mycompany.myapp.base;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T,PK extends Serializable> {
    void insert(T db);
    int batchInsert(List<T> list);

    int update(T db);
    int batchUpdate(List<PK> list);

    int delete(PK id);
    int batchDelete(List<PK> list);

    T getById(PK id);
    List<T> queryByIds(List<PK> list);

    List<T> queryList(BaseQuery query);
    int count(BaseQuery query);

    PageList<T> queryPage(BaseQuery query, PageBounds pb);

    int updateStatus(@Param("id")PK id,@Param("status")int status);
    int batchUpdateStatus(@Param("list")List<PK> list,@Param("status")int status);
}
