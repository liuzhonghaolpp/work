package com.liu.Service;

import java.util.List;

import com.liu.Entity.Business;

public interface BusinessService {

	/**
	 * 保存商人信息
	 * @param entity
	 * @return
	 */
	public String saveBusiness(Business entity);
	
	/**
	 * 删除商人信息
	 * @param entity
	 */
	public void deleteBusiness(Business entity);
	
	/**
	 * 更新商人信息
	 * @param entity
	 */
	public void updateBusiness(Business entity);
	
	/**
	 * 通过用户名 密码 查找商人信息
	 * @param username
	 * @param password
	 * @return
	 */
	public Business findBusByNameAndPasssword(String username,String password);
	
	/**
	 * 通过ID查找人信息
	 * @param id
	 * @return
	 */
	public Business findById(String id);
	
	/**
	 * 通过用户名查找商人信息
	 * @param username
	 * @return
	 */
	public List<Business> findByUsername(String username);
}
