package com.liu.Dao;

import java.util.List;

import com.liu.Entity.Order;

public interface OrderDao extends BaseDao<Order, String> {

	//�������Բ���
	public List<Order> findByParam(String hql,Object[] param);
	/**
	 * ���̼�id��ҳ��ѯ����
	 * @param id
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Order> findByBusForPage(String id,int offset,int length);
	/**
	 * ���̼�id��ѯ�����������ڷ�ҳ
	 * @param id
	 */
	public int findBusRowCount(String id);
	/**
	 * �����id��ҳ��ѯ����
	 * @param id
	 * @param offset
	 * @param length
	 * @return
	 */
	public List<Order> findByCusForPage(String id,int offset,int length);
	/**
	 * �����id��ҳ��ѯ����
	 * @param id
	 * @return
	 */
	public int findCusRowCount(String id);
}