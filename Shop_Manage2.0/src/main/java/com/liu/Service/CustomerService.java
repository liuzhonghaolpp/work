package com.liu.Service;

import java.util.List;

import com.liu.Entity.Customer;

public interface CustomerService {

	/**
	 * ����˿�
	 * @param entity
	 */
	public String saveCustomer(Customer entity);
	
	/**
	 * ɾ���˿�
	 * @param entity
	 */
	public void deleteCustomer(Customer entity);
	
	/**
	 * ���¹˿�����
	 * @param entity
	 */
	public void updateCustomer(Customer entity);
	
	/**
	 * ͨ���ʺź������˿���Ŀ
	 * @param username
	 * @param password
	 * @return
	 */
	public Customer findCusByNameAndPasssword(String username,String password);
	
	/**
	 * ͨ��id�Ų�˿���Ŀ
	 * @param id
	 * @return
	 */
	public Customer findById(String id);
	
	/**
	 * ͨ���ʺŲ�˿���Ŀ
	 * ��List.get(0)��Ч
	 * @param username
	 * @return
	 */
	public List<Customer> findByUsername(String username);
}
