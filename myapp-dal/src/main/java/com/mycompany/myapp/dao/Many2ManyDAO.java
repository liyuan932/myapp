package com.mycompany.myapp.dao;

import java.util.List;

/**
 * 多对多
 */
public interface Many2ManyDAO<T> {

  int batchInsert(List<T> list);

  List<T> queryList();

  List<T> queryByMainId(Long mainId);

  List<T> queryByMainIds(List<Long> mainIds);

  List<T> queryBySecondaryId(Long secondaryId);

  List<T> queryBySecondaryIds(List<Long> secondaryIds);

  int deleteByMainId(Long mainId);

  int deleteBySecondaryId(Long secondaryId);
}
