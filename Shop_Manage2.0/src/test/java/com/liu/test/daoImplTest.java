package com.liu.test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.liu.Dao.BusinessDao;
import com.liu.Dao.ProductsDao;
import com.liu.Entity.Business;
import com.liu.Entity.Products;
import com.liu.Service.BusinessService;
import com.liu.Service.ProductsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/applicationContext.xml")
public class daoImplTest {

	@Autowired
	private BusinessDao businessDao;
	@Autowired
	private BusinessService businessService;
	@Autowired
	private ProductsService productsService;
	@Autowired
	private ProductsDao productsDao;

	@Test
	public void Test() {
		//Business bus = businessService.findByUsername("liuzhonghao").get(0);
		/*bus.setImgurl("12345");
		bus.setNickName("12345");
		bus.setOpenDate(new Date());
		bus.setPassword("12345");
		bus.setUsername("12345");
		bus.setTelephone("123");
		
		businessDao.save(bus);*/
		/*bus = businessDao.findByParam("from Business b where b.nickName = ? and b.imgurl = ?",new Object[]{"12345","12345"}).get(0);*/
		
		/*bus = businessService.findBusByNameAndPasssword("12345", "12345");
		System.out.println("telephone:"+bus.getTelephone());//hql语句 b.xxx xxx要与实体类中的属性对应，不与表中colomn名对应*/
		/*Products pro = new Products();
		pro.setName("蒙药心脑方");
		pro.setInstock(100);
		pro.setMemo("gajiucai");
		pro.setOnselldate(new Date());
		pro.setSelled(0);
		pro.setPrice(12);
		pro.setType("medicine");
		pro.setBusiness(bus);
		pro.setImgurl("ewrwerwerw");
		productsService.saveProducts(pro);*/
		
		/*List<Products> products= productsDao.findByBusIdForPage("4028b8815d3f2cfa015d3f2d45d20000", 0, 3);
		for(Products p : products){
			System.out.println(p.getName());
		}*/
		
		int i = productsDao.findRowCount("4028b8815d3f2cfa015d3f2d45d20000");
		System.out.println(i);
		
	}
}
