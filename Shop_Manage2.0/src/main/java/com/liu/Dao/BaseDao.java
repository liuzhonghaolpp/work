package com.liu.Dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDao<T ,pk extends Serializable> {

	//����
	public String save(T entity);
	//����
	public void update(T entity);
	//ɾ��
	public void delete(pk id);
	//����
	public T findObjectById(pk id);
	//�����б�
	public List<T> findObjects();
}
