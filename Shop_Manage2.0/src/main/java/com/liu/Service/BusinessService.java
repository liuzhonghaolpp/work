package com.liu.Service;

import java.util.List;

import com.liu.Entity.Business;

public interface BusinessService {

	/**
	 * ����������Ϣ
	 * @param entity
	 * @return
	 */
	public String saveBusiness(Business entity);
	
	/**
	 * ɾ��������Ϣ
	 * @param entity
	 */
	public void deleteBusiness(Business entity);
	
	/**
	 * ����������Ϣ
	 * @param entity
	 */
	public void updateBusiness(Business entity);
	
	/**
	 * ͨ���û��� ���� ����������Ϣ
	 * @param username
	 * @param password
	 * @return
	 */
	public Business findBusByNameAndPasssword(String username,String password);
	
	/**
	 * ͨ��ID��������Ϣ
	 * @param id
	 * @return
	 */
	public Business findById(String id);
	
	/**
	 * ͨ���û�������������Ϣ
	 * @param username
	 * @return
	 */
	public List<Business> findByUsername(String username);
}
