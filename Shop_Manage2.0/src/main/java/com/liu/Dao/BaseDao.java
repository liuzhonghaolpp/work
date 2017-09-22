package com.liu.Dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T ,pk extends Serializable> {

	//新增
	public String save(T entity);
	//更新
	public void update(T entity);
	//删除
	public void delete(pk id);
	//查找
	public T findObjectById(pk id);
	//查找列表
	public List<T> findObjects();
}
