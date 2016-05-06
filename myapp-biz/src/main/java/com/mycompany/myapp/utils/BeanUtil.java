package com.mycompany.myapp.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class BeanUtil {
  private static final Logger log = LoggerFactory.getLogger(BeanUtil.class);

  /**
   * db转vo.
   */
  public static <D, V> V dbToVo(D db, Class<V> clz) {
    try {
      Preconditions.checkNotNull(db);
      Preconditions.checkNotNull(clz);

      V vo = clz.newInstance();
      BeanUtils.copyProperties(db, vo);

      return vo;
    } catch (Exception ex) {
      log.error("db to vo fail", ex);
      return null;
    }
  }

  /**
   * db转vo.
   */
  public static <D, V> V dbToVo(D db, Class<V> clz, Callback<D, V> callback) {
    try {
      Preconditions.checkNotNull(db);
      Preconditions.checkNotNull(clz);
      Preconditions.checkNotNull(callback);

      V vo = clz.newInstance();
      BeanUtils.copyProperties(db, vo);
      callback.execute(db, vo);

      return vo;
    } catch (Exception ex) {
      log.error("db to vo fail", ex);
      return null;
    }
  }

  /**
   * dbList转voList.
   */
  public static <D, V> List<V> dbToVo(List<D> dbList, Class<V> clz) {

    try {
      Preconditions.checkArgument(CollectionUtils.isNotEmpty(dbList));
      Preconditions.checkNotNull(clz);

      List<V> voList = Lists.newArrayList();
      for (D db : dbList) {
        V vo = clz.newInstance();
        BeanUtils.copyProperties(db, vo);
        voList.add(vo);
      }
      return voList;
    } catch (Exception ex) {
      log.error("dbList to voList fail", ex);
      return null;
    }
  }

  /**
   * dbList转voList.
   */
  public static <D, V> List<V> dbToVo(List<D> dbList, Class<V> clz, Callback<D, V> callback) {
    try {
      Preconditions.checkArgument(CollectionUtils.isNotEmpty(dbList));
      Preconditions.checkNotNull(clz);
      Preconditions.checkNotNull(callback);

      List<V> voList = Lists.newArrayList();
      for (D db : dbList) {
        V vo = clz.newInstance();
        BeanUtils.copyProperties(db, vo);
        callback.execute(db, vo);
        voList.add(vo);
      }

      return voList;
    } catch (Exception ex) {
      log.error("dbList to voList fail", ex);
      return null;
    }
  }

  /**
   * dbPageList转voPageList.
   */
  public static <D, V> PageList<V> dbToVo(PageList<D> dbList, Class<V> clz) {

    try {
      Preconditions.checkArgument(CollectionUtils.isNotEmpty(dbList));
      Preconditions.checkNotNull(clz);

      PageList<V> voList = new PageList<V>(dbList.getPaginator());
      for (D db : dbList) {
        V vo = clz.newInstance();
        BeanUtils.copyProperties(db, vo);
        voList.add(vo);
      }
      return voList;
    } catch (Exception ex) {
      log.error("dbPageList to voPageList fail", ex);
      return null;
    }
  }

  /**
   * dbPageList转voPageList.
   */
  public static <D, V> PageList<V> dbToVo(PageList<D> dbList, Class<V> clz, Callback<D, V> callback) {

    try {
      Preconditions.checkArgument(CollectionUtils.isNotEmpty(dbList));
      Preconditions.checkNotNull(clz);
      Preconditions.checkNotNull(callback);

      PageList<V> voList = new PageList<V>(dbList.getPaginator());
      for (D db : dbList) {
        V vo = clz.newInstance();
        BeanUtils.copyProperties(db, vo);
        callback.execute(db, vo);
        voList.add(vo);
      }
      return voList;
    } catch (Exception ex) {
      log.error("dbPageList to voPageList fail", ex);
      return null;
    }
  }

  public interface Callback<D, V> {
    void execute(D db, V vo);
  }
}
