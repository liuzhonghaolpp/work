package com.liu.Dao;

import java.util.List;

import com.liu.Entity.Customer;

public interface CustomerDao extends BaseDao<Customer,String>{

	//根据用户名和密码查找
	public List<Customer> findByAccountAndPassword(String username,String password);
	
	//根据属性查找
	public List<Customer> findByParam(String hql,Object[] param);
}
