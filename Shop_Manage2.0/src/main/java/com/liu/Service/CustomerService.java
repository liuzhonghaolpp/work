package com.liu.Service;

import java.util.List;

import com.liu.Entity.Customer;

public interface CustomerService {

	/**
	 * 保存顾客
	 * @param entity
	 */
	public String saveCustomer(Customer entity);
	
	/**
	 * 删除顾客
	 * @param entity
	 */
	public void deleteCustomer(Customer entity);
	
	/**
	 * 更新顾客数据
	 * @param entity
	 */
	public void updateCustomer(Customer entity);
	
	/**
	 * 通过帐号和密码查顾客条目
	 * @param username
	 * @param password
	 * @return
	 */
	public Customer findCusByNameAndPasssword(String username,String password);
	
	/**
	 * 通过id号查顾客条目
	 * @param id
	 * @return
	 */
	public Customer findById(String id);
	
	/**
	 * 通过帐号查顾客条目
	 * 仅List.get(0)有效
	 * @param username
	 * @return
	 */
	public List<Customer> findByUsername(String username);
}
