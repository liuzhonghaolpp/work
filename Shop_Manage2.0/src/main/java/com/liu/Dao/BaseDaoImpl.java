package com.liu.Dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class BaseDaoImpl<T, pk extends Serializable> implements BaseDao<T, pk> {

	protected Class<T> clazz;
	
	@Resource
	SessionFactory sessionfactory;
	
	//获取到泛型对象
	@SuppressWarnings("unchecked")
	public BaseDaoImpl(){
		ParameterizedType pt=(ParameterizedType)this.getClass().getGenericSuperclass();  
        clazz=(Class<T>)pt.getActualTypeArguments()[0];
	}
	
	//获取session
	protected Session getCurrentSession(){
	    return sessionfactory.getCurrentSession();
	}
		
    public String save(Object entity){
	    return (String) this.getCurrentSession().save(entity);
	}
		
	public void update(Object entity){
		this.getCurrentSession().update(entity);
	}
		
	public void delete(Serializable id){
		this.getCurrentSession().delete(findObjectById(id));
	}
		
	@SuppressWarnings("unchecked")
	public T findObjectById(Serializable id){
		return (T) this.getCurrentSession().get(clazz, id);
	}
		
	@SuppressWarnings("unchecked")
	public List<T> findObjects(){
		Query query = this.getCurrentSession().createQuery("from"+clazz.getSimpleName());
		return query.list();
	}
}
