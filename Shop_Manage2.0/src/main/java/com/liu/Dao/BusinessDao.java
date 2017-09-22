package com.liu.Dao;

import java.util.List;

import com.liu.Entity.Business;

public interface BusinessDao extends BaseDao<Business,String>{

	//�����û������������
	public List<Business> findByAccountAndPassword(String username,String password);
	
	//�������Բ���
	public List<Business> findByParam(String hql,Object[] param);
}
