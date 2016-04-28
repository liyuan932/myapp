package com.mycompany.myapp.utils;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.mycompany.myapp.daoobject.BaseDO;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

public class BeanUtil {
	private static final Logger log = LoggerFactory.getLogger(BeanUtil.class);

	public static <D, V> V dbToVo(D db, Class<V> clz) {
		try {
			Preconditions.checkNotNull(db);
			Preconditions.checkNotNull(clz);

			V vo = clz.newInstance();
			BeanUtils.copyProperties(db, vo);

			return vo;
		} catch (Exception e) {
			log.error("db to vo fail", e);
			return null;
		}
	}

	public static <D, V> V dbToVo(D db, Class<V> clz,Callback<D, V> callback) {
		try {
			Preconditions.checkNotNull(db);
			Preconditions.checkNotNull(clz);
			Preconditions.checkNotNull(callback);

			V vo = clz.newInstance();
			BeanUtils.copyProperties(db, vo);
			callback.execute(db, vo);

			return vo;
		} catch (Exception e) {
			log.error("db to vo fail", e);
			return null;
		}
	}

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
		} catch (Exception e) {
			log.error("dbList to voList fail", e);
			return null;
		}
	}

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
		} catch (Exception e) {
			log.error("dbList to voList fail", e);
			return null;
		}
	}

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
		} catch (Exception e) {
			log.error("dbPageList to voPageList fail", e);
			return null;
		}
	}

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
		} catch (Exception e) {
			log.error("dbPageList to voPageList fail", e);
			return null;
		}
	}

	public interface Callback<D, V> {
		void execute(D db, V vo);
	}

	public  static List<? extends Serializable> getIds(List<? extends BaseDO> list){
		return Lists.transform(list, new Function<BaseDO, Serializable>() {
			@Override
			public Serializable apply(BaseDO input) {
				return input.getId();
			}
		});
	}
}
