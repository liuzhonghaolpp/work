package com.liu.Service;

import com.liu.Entity.Page;
import com.liu.Entity.Products;

public interface ProductsService {

	/**
	 * 保存商品
	 * @param entity
	 */
	public String saveProducts(Products entity);
	
	/**
	 * 删除商品
	 * @param entity
	 */
	public void deleteProducts(Products entity);
	
	/**
	 * 更新商品数据
	 * @param entity
	 */
	public void updateProducts(Products entity);
	
	/**
	 * 通过id号查商品条目
	 * @param id
	 * @return
	 */
	public Products findById(String id);
	/**
	 * 分页查询
	 * @param id businessId
	 * @param currentPage 当前页号
	 * @param pageSize 每页显示的记录条数
	 * @return 封闭了分页信息(包括记录集list)的Bean
	 */
	public Page<Products> findByBusForPage(String id,int currentPage,int pageSize);
	/**
	 * 所有商品的分页查询
	 * @param currentPage
	 * @param PageSize
	 * @return
	 */
	public Page<Products> findAllForPage(int currentPage,int PageSize);
}
