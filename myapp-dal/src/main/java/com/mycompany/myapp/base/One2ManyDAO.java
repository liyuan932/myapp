package com.mycompany.myapp.base;

import java.util.List;

/**
 * 一对多通过DAO
 */
public interface One2ManyDAO<T> {

    int batchInsert(List<T> list);

    List<T> queryList();

    List<T> queryByMainId(Long mainId);

    List<T> queryByMainIds(List<Long> mainIds);

    T queryBySecondaryId(Long secondaryId);

    List<T> queryBySecondaryIds(List<Long> secondaryIds);

    int deleteByMainId(Long mainId);

    int deleteBySecondaryId(Long secondaryId);
}
