package com.liu.Service;

import com.liu.Entity.Order;
import com.liu.Entity.Page;

public interface OrderService {

	/**
	 * 保存订单
	 * @param entity
	 */
	public String saveOrder(Order entity);
	
	/**
	 * 删除订单
	 * @param entity
	 */
	public void deleteOrder(Order entity);
	
	/**
	 * 更新订单数据
	 * @param entity
	 */
	public void updateOrder(Order entity);
	
	/**
	 * 通过id号查订单条目
	 * @param id
	 * @return
	 */
	public Order findById(String id);
	/**
	 * 按商家id分页查询
	 * @param id
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Page<Order> findByBusForPage(String id,int currentPage,int pageSize);
	/**
	 * 按买家id分页查询
	 * @param id
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public Page<Order> findByCusForPage(String id,int currentPage,int pageSize);
}