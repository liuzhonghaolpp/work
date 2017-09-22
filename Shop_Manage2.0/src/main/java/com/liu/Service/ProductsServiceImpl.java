package com.liu.Service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.liu.Dao.ProductsDao;
import com.liu.Entity.Page;
import com.liu.Entity.Products;

@Service("productsService")
public class ProductsServiceImpl implements ProductsService {

	@Resource
	private ProductsDao productsDao;
	
	public String saveProducts(Products entity) {
		
		return productsDao.save(entity);
	}

	public void deleteProducts(Products entity) {
		
		productsDao.delete(entity.getId());
	}

	public void updateProducts(Products entity) {
		
		productsDao.update(entity);
	}

	public Products findById(String id) {
		
		return productsDao.findObjectById(id);
	}
	public Page<Products> findByBusForPage(String id,int currentPage,int pageSize){
		Page<Products> page = new Page<Products>();
		//�ܼ�¼��
		int allRow = productsDao.findRowCount(id);
		//��ǰҳ��ʼ��¼
		int offset = page.countOffset(currentPage, pageSize);
		//��ҳ��ѯ�����
		List<Products> list = productsDao.findByBusIdForPage(id, offset, pageSize);
		page.setPageNo(currentPage);
		page.setPageSize(pageSize);
		page.setTotalRecords(allRow);
		page.setList(list);
		return page;
	}
	public Page<Products> findAllForPage(int currentPage,int pageSize){
		Page<Products> page = new Page<Products>();
		
		int allRow = productsDao.findALlRowCount();
		
		int offset = page.countOffset(currentPage, pageSize);
		
		List<Products> list = productsDao.findAllForPage(offset, pageSize);
		page.setPageNo(currentPage);
		page.setPageSize(pageSize);
		page.setTotalRecords(allRow);
		page.setList(list);
		return page;
	}
}
