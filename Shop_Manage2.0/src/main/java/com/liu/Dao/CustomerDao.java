package com.liu.Dao;

import java.util.List;

import com.liu.Entity.Customer;

public interface CustomerDao extends BaseDao<Customer,String>{

	//�����û������������
	public List<Customer> findByAccountAndPassword(String username,String password);
	
	//�������Բ���
	public List<Customer> findByParam(String hql,Object[] param);
}
