package com.liu.Service;

import com.liu.Entity.Page;
import com.liu.Entity.Products;

public interface ProductsService {

	/**
	 * ������Ʒ
	 * @param entity
	 */
	public String saveProducts(Products entity);
	
	/**
	 * ɾ����Ʒ
	 * @param entity
	 */
	public void deleteProducts(Products entity);
	
	/**
	 * ������Ʒ����
	 * @param entity
	 */
	public void updateProducts(Products entity);
	
	/**
	 * ͨ��id�Ų���Ʒ��Ŀ
	 * @param id
	 * @return
	 */
	public Products findById(String id);
	/**
	 * ��ҳ��ѯ
	 * @param id businessId
	 * @param currentPage ��ǰҳ��
	 * @param pageSize ÿҳ��ʾ�ļ�¼����
	 * @return ����˷�ҳ��Ϣ(������¼��list)��Bean
	 */
	public Page<Products> findByBusForPage(String id,int currentPage,int pageSize);
	/**
	 * ������Ʒ�ķ�ҳ��ѯ
	 * @param currentPage
	 * @param PageSize
	 * @return
	 */
	public Page<Products> findAllForPage(int currentPage,int PageSize);
}
