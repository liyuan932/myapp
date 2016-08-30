package com.mycompany.myapp.base;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import java.util.List;

/**
 * 基本DAO
 */
public interface BaseDAO<T> {

  void insert(T db);

  int update(T db);

  int delete(Long id);

  T getById(Long id);

  int batchInsert(List<T> list);

  int batchDelete(List<Long> ids);

  List<T> queryByIds(List<Long> ids);

  List<T> queryList(BaseDO query);

  int count(BaseDO query);

  PageList<T> queryPage(BaseDO query, PageBounds pb);
}
