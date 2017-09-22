package com.liu.Dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.liu.Entity.Business;
import com.liu.Entity.Order;

@Repository("orderDao")
public class OrderDaoImpl extends BaseDaoImpl<Order, String> implements OrderDao {

	@SuppressWarnings("unchecked")
	public List<Order> findByParam(String hql, Object[] param) {
		Query q=this.getCurrentSession().createQuery(hql);
		if(param!=null && param.length>0){
			for(int i = 0;i<param.length;i++){
				q.setParameter(i, param[i]);
			}
		}
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Order> findByBusForPage(String id,int offset,int length){
		Session session = this.getCurrentSession();
		String hql = "from Order o where  businessid = ? order by date asc";
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		query.setFirstResult(offset);
		query.setMaxResults(length);
		List<Order> orders = query.list();
		return orders;
	}
	
	public int findBusRowCount(String id){
		Session session = this.getCurrentSession();
		Query query = session.createQuery("select count(*) from Order o where businessid = ?");
		query.setParameter(0, id);
		int count = ((Long)query.uniqueResult()).intValue();
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public List<Order> findByCusForPage(String id,int offset,int length){
		Session session = this.getCurrentSession();
		String hql = "from Order o where cosid = ? order by date asc";
		Query query = session.createQuery(hql);
		query.setParameter(0, id);
		query.setFirstResult(offset);
		query.setMaxResults(length);
		List<Order> orders = query.list();
		return orders;
	}
	
	public int findCusRowCount(String id){
		Session session = this.getCurrentSession();
		Query query = session.createQuery("select count(*) from Order o where cosid = ?");
		query.setParameter(0, id);
		int count = ((Long)query.uniqueResult()).intValue();
		return count;
	}

}