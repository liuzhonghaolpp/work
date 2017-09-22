package com.liu.Dao;

import java.util.List;

import com.liu.Entity.Products;

public interface ProductsDao extends BaseDao<Products, String> {

	//根据属性查找
	public List<Products> findByParam(String hql,Object[] param);
	/**
	 * 分页查询所有商品
	 * @param offset 开始记录
	 * @param length 一次查询几条
	 * @return 返回查询记录集合
	 */
	public List<Products> findAllForPage(int offset,int length);
	/**
	 * 
	 * @param id businessId
	 * @param offset  开始记录
	 * @param length  一次查询几条
	 * @return  返回查询结果
	 */
	public List<Products> findByBusIdForPage(String id,int offset,int length);
	/**
	 * 查询一个店家商品记录总数
	 * @return
	 */
	public int findRowCount(String id);
	/**
	 * 查询所有商品记录总数
	 */
	public int findALlRowCount();
}
