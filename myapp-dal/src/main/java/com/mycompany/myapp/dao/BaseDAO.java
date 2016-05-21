package com.mycompany.myapp.dao;

import com.mycompany.myapp.daoobject.BaseDO;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseDAO<T> {

  void insert(T db);

  int batchInsert(List<T> list);

  int update(T db);

  int delete(Long id);

  int batchDelete(List<Long> list);

  T getById(Long id);

  List<T> queryByIds(List<Long> list);

  List<T> queryList(BaseDO query);

  int count(BaseDO query);

  PageList<T> queryPage(BaseDO query, PageBounds pb);

  int updateStatus(@Param("id") Long id, @Param("status") int status);

  int batchUpdateStatus(@Param("list") List<Long> list, @Param("status") int status);
}
