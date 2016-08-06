package com.mycompany.myapp.base;

import org.apache.ibatis.annotations.Param;

import java.util.List;

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
