package com.liu.Dao;

import java.util.List;

import com.liu.Entity.Products;

public interface ProductsDao extends BaseDao<Products, String> {

	//�������Բ���
	public List<Products> findByParam(String hql,Object[] param);
	/**
	 * ��ҳ��ѯ������Ʒ
	 * @param offset ��ʼ��¼
	 * @param length һ�β�ѯ����
	 * @return ���ز�ѯ��¼����
	 */
	public List<Products> findAllForPage(int offset,int length);
	/**
	 * 
	 * @param id businessId
	 * @param offset  ��ʼ��¼
	 * @param length  һ�β�ѯ����
	 * @return  ���ز�ѯ���
	 */
	public List<Products> findByBusIdForPage(String id,int offset,int length);
	/**
	 * ��ѯһ�������Ʒ��¼����
	 * @return
	 */
	public int findRowCount(String id);
	/**
	 * ��ѯ������Ʒ��¼����
	 */
	public int findALlRowCount();
}
