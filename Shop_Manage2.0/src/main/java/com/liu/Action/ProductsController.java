package com.liu.Action;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.liu.Entity.Business;
import com.liu.Entity.Order;
import com.liu.Entity.Page;
import com.liu.Entity.Products;
import com.liu.Service.BusinessService;
import com.liu.Service.OrderService;
import com.liu.Service.ProductsService;

@Controller
@RequestMapping("/Products")
public class ProductsController {

	/**
	 * 声明所需的Service层
	 */
	@Resource
	private ProductsService productsService;
	@Resource
	private BusinessService businessService;
	@Resource
	private OrderService orderService;
	
	/**
	 * 处理：商家的货物管理
	 * 查询数据库 分页展示该商家的货物
	 * @param request
	 * @param model
	 * @param session	商家会话信息
	 * @return	跳转至商家货物管理页面
	 */
	@RequestMapping(value="/businessProlist")
	public String prolist(HttpServletRequest request,ModelMap model,HttpSession session){
		String pageNo = request.getParameter("pageNo");
		Business bus = (Business)session.getAttribute("business");
		String id = bus.getId();
		if(pageNo == null){
			pageNo = "1";
		}
		Page<Products> page = productsService.findByBusForPage(id,Integer.valueOf(pageNo),6);
		List<Products> list = page.getList();
		model.put("list", list);
		request.setAttribute("page", page);
		return "BusinessClient/productManage"; //返回商家浏览商品页
	}
	
	/**
	 * 处理：买家的购买列表
	 * 查询数据库 分页展示买家可购买的商品
	 * @param request
	 * @param model
	 * @return	跳转至买家浏览商品页
	 */
	@RequestMapping(value="/customerProList")
	public String customerProList(HttpServletRequest request,ModelMap model){
		String pageNo = request.getParameter("pageNo");
		if(pageNo == null){
			pageNo = "1";
		}
		Page<Products> page = productsService.findAllForPage(Integer.valueOf(pageNo),6);
		List<Products> list = page.getList();
		model.put("list", list);
		request.setAttribute("page", page);
		return "CustomerClient/shopList"; //返回买家浏览商品页
	}
	
	/**
	 * 处理：商家货品页面上架商品
	 * @param model
	 * @param name
	 * @param type
	 * @param price
	 * @param memo
	 * @param instock
	 * @param file
	 * @param request
	 * @param session
	 * @return	跳转至商家货物管理页面
	 * @throws Exception
	 */
	@RequestMapping(value="/add")
	public String add(ModelMap model,String name,String type,double price,String memo,int instock,@RequestParam(value="file",required=false) MultipartFile file,
			HttpServletRequest request,HttpSession session)throws Exception{
		Products products = new Products();
		Business business = (Business)session.getAttribute("business");
		Business bus = businessService.findById(business.getId());
		products.setName(name);
		products.setType(type);
		products.setPrice(price);
		products.setMemo(memo);
		products.setInstock(instock);
		products.setBusiness(bus);
		products.setSelled(0);
		products.setOnselldate(new Date());
		
		String pathRoot = request.getSession().getServletContext().getRealPath("");  
        String path="";
        if(!file.isEmpty()){  
            //生成uuid作为文件名称  
            String uuid = UUID.randomUUID().toString().replaceAll("-","");  
            //获得文件类型（可以判断如果不是图片，禁止上传）  
            String contentType=file.getContentType();  
            //获得文件后缀名称  
            String imageName=contentType.substring(contentType.indexOf("/")+1);  
            path="/upload/products"+uuid+"."+imageName;  
            file.transferTo(new File(pathRoot+path));
            products.setImgurl(path);
        }
        productsService.saveProducts(products);
        return "forward:businessProlist.do";
	}
	
	/**
	 * 处理：商家货物管理页面下架商品
	 * @param id
	 * @param request
	 * @return	跳转至商家货物管理页面
	 */
	@RequestMapping(value="/delete")
	public String delete(String id,HttpServletRequest request){
		Products products = productsService.findById(id);
		productsService.deleteProducts(products);
		return "forward:businessProlist.do";
	}
	
	/**
	 * 处理：商家货物管理页面修改商品信息
	 * @param id
	 * @param model
	 * @return	跳转至商品信息修改页面
	 */
	@RequestMapping(value="/editUI")
	public String editUI(String id,ModelMap model){
		Products products = productsService.findById(id);
		model.put("product", products);
		return "BusinessClient/productEdit";
	}
	
	/**
	 * 处理：商品信息修改页面修改商品信息
	 * @param id
	 * @param model
	 * @param name
	 * @param type
	 * @param price
	 * @param memo
	 * @param instock
	 * @param file
	 * @param request
	 * @return	跳转至商家货物管理页面
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public String edit(String id,ModelMap model,String name,String type,double price,String memo,int instock,@RequestParam(value="file",required=false) MultipartFile file,
			HttpServletRequest request)throws Exception{
		Products product = productsService.findById(id);
		product.setName(name);
		product.setType(type);
		product.setPrice(price);
		product.setMemo(memo);
		product.setInstock(instock);
		String pathRoot = request.getSession().getServletContext().getRealPath("");  
        String path="";
		if(!file.isEmpty()){  
            //生成uuid作为文件名称  
            String uuid = UUID.randomUUID().toString().replaceAll("-","");  
            //获得文件类型（可以判断如果不是图片，禁止上传）  
            String contentType=file.getContentType();  
            //获得文件后缀名称  
            String imageName=contentType.substring(contentType.indexOf("/")+1);  
            path="/upload/products"+uuid+"."+imageName;  
            file.transferTo(new File(pathRoot+path));
            product.setImgurl(path);
        }
        productsService.updateProducts(product);
        return "forward:businessProlist.do";
	}
	
	/**
	 * 查询商品信息
	 * 根据session获取role 判断不同的跳转页面
	 * @param session
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/searchProInfo")
	public String searchProInfo(HttpSession session,String id,ModelMap model){
		String role = (String) session.getAttribute("role");
		Products product = productsService.findById(id);
		model.put("product", product);
		Set<Order> orders = product.getOrders();
		model.put("orders", orders);
		if("cus".equals(role)){
			return "CustomerClient/productInfo";
		}
		else if("bus".equals(role)){
			return "BusinessClient/productInfo";
		}
		else{
			return "";
		}
	}
	
	/**
	 * 处理：买家商品列表 点击购买
	 * @param id
	 * @param model
	 * @return	跳转至商品购买页面
	 */
	@RequestMapping(value="/customerBuy")
	public String customerBuy(String id,ModelMap model){
		Products product = productsService.findById(id);
		model.put("product", product);
		return "CustomerClient/buyProducts";
	}
	
	/**
	 * 创建订单更新货品信息
	 * @param number
	 * @param product
	 * @param model
	 * @return
	 */ 
	@RequestMapping(value="/updateInfoByOrder")
	public String updateInfoByOrder(String orderID,ModelMap model){
		Order order = new Order();
		order = orderService.findById(orderID);
		Products product = order.getProducts();									
		product.setInstock(product.getInstock() - order.getNumber());	//更新库存量
		Set<Order> orderSet = product.getOrders();						//更新订单表
		orderSet.add(order);
		product.setOrders(orderSet);
		product.setSelled(product.getSelled() + order.getNumber());		//更新已售数量
		productsService.updateProducts(product);
		product = productsService.findById(order.getProducts().getId());
		model.put("order",order);
		model.put("product", product);
		return "forward:/Products/customerProList.do";
	}
}
