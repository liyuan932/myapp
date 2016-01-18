package com.mycompany.myapp.base;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

public interface BaseDao<T,PK extends Serializable> {
	int insert(T db);
    int batchInsert(List<T> list);
    
    int update(T db);
    int batchUpdate(List<PK> list);
    
    int deleteById(PK id);
    int batchDelete(List<PK> list);
    
    T selectById(PK id);
    
    List<T> selectList(BaseQuery query);
    int count(BaseQuery query);
    
    PageList<T> pageList(BaseQuery query,PageBounds pb);
    
    int updateStatus(@Param("id")PK id,@Param("status")int status);
    int batchUpdateStatus(@Param("list")List<PK> list,@Param("status")int status);
}
