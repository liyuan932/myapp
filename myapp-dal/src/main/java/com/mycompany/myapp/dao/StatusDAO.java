package com.mycompany.myapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 状态相关
 */
public interface StatusDAO<T> extends BaseDAO<T> {

  List<T> queryByStatus(int status);

  int updateStatus(@Param("id") Long id, @Param("status") int status);

  int deleteByStatus(int status);

  List<T> queryByStatuses(List<Integer> statuses);

  int batchUpdateStatus(@Param("list") List<Long> list, @Param("status") int status);

  int batchDeleteByStatuses(List<Integer> statuses);
}
