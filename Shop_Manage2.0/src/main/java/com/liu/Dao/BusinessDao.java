package com.liu.Dao;

import java.util.List;

import com.liu.Entity.Business;

public interface BusinessDao extends BaseDao<Business,String>{

	//根据用户名和密码查找
	public List<Business> findByAccountAndPassword(String username,String password);
	
	//根据属性查找
	public List<Business> findByParam(String hql,Object[] param);
}
