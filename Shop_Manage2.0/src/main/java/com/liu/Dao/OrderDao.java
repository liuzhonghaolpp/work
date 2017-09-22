package com.liu.Dao;

import java.util.List;

import com.liu.Entity.Order;

public interface OrderDao extends BaseDao<Order, String> {

	//根据属性查找
	public List<Order> findByParam(String hql,Object[] param);
	/**
	 * 按商家id分页查询订单
	 * @param id
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Order> findByBusForPage(String id,int offset,int length);
	/**
	 * 按商家id查询订单总数用于分页
	 * @param id
	 */
	public int findBusRowCount(String id);
	/**
	 * 按买家id分页查询订单
	 * @param id
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Order> findByCusForPage(String id,int offset,int length);
	/**
	 * 按买家id分页查询订单
	 * @param id
	 * @return
	 */
	public int findCusRowCount(String id);
}