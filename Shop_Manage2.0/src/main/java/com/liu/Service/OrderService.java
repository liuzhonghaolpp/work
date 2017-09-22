package com.liu.Service;

import com.liu.Entity.Order;
import com.liu.Entity.Page;

public interface OrderService {

	/**
	 * ���涩��
	 * @param entity
	 */
	public String saveOrder(Order entity);
	
	/**
	 * ɾ������
	 * @param entity
	 */
	public void deleteOrder(Order entity);
	
	/**
	 * ���¶�������
	 * @param entity
	 */
	public void updateOrder(Order entity);
	
	/**
	 * ͨ��id�Ų鶩����Ŀ
	 * @param id
	 * @return
	 */
	public Order findById(String id);
	/**
	 * ���̼�id��ҳ��ѯ
	 * @param id
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Page<Order> findByBusForPage(String id,int currentPage,int pageSize);
	/**
	 * �����id��ҳ��ѯ
	 * @param id
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Page<Order> findByCusForPage(String id,int currentPage,int pageSize);
}