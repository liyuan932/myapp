package com.mycompany.myapp.dao;

import org.apache.ibatis.annotations.Param;

import com.mycompany.myapp.daoobject.BaseDO;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import java.io.Serializable;
import java.util.List;

public interface BaseDAO<T, K extends Serializable> {
  void insert(T db);

  int batchInsert(List<T> list);

  int update(T db);

  int delete(K id);

  int batchDelete(List<K> list);

  T getById(K id);

  List<T> queryByIds(List<K> list);

  List<T> queryList(BaseDO query);

  int count(BaseDO query);

  PageList<T> queryPage(BaseDO query, PageBounds pb);

  int updateStatus(@Param("id") K id, @Param("status") int status);

  int batchUpdateStatus(@Param("list") List<K> list, @Param("status") int status);
}
