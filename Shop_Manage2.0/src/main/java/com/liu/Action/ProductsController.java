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
	 * ���������Service��
	 */
	@Resource
	private ProductsService productsService;
	@Resource
	private BusinessService businessService;
	@Resource
	private OrderService orderService;
	
	/**
	 * �����̼ҵĻ������
	 * ��ѯ���ݿ� ��ҳչʾ���̼ҵĻ���
	 * @param request
	 * @param model
	 * @param session	�̼һỰ��Ϣ
	 * @return	��ת���̼һ������ҳ��
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
		return "BusinessClient/productManage"; //�����̼������Ʒҳ
	}
	
	/**
	 * ������ҵĹ����б�
	 * ��ѯ���ݿ� ��ҳչʾ��ҿɹ������Ʒ
	 * @param request
	 * @param model
	 * @return	��ת����������Ʒҳ
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
		return "CustomerClient/shopList"; //������������Ʒҳ
	}
	
	/**
	 * �����̼һ�Ʒҳ���ϼ���Ʒ
	 * @param model
	 * @param name
	 * @param type
	 * @param price
	 * @param memo
	 * @param instock
	 * @param file
	 * @param request
	 * @param session
	 * @return	��ת���̼һ������ҳ��
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
            //����uuid��Ϊ�ļ�����  
            String uuid = UUID.randomUUID().toString().replaceAll("-","");  
            //����ļ����ͣ������ж��������ͼƬ����ֹ�ϴ���  
            String contentType=file.getContentType();  
            //����ļ���׺����  
            String imageName=contentType.substring(contentType.indexOf("/")+1);  
            path="/upload/products"+uuid+"."+imageName;  
            file.transferTo(new File(pathRoot+path));
            products.setImgurl(path);
        }
        productsService.saveProducts(products);
        return "forward:businessProlist.do";
	}
	
	/**
	 * �����̼һ������ҳ���¼���Ʒ
	 * @param id
	 * @param request
	 * @return	��ת���̼һ������ҳ��
	 */
	@RequestMapping(value="/delete")
	public String delete(String id,HttpServletRequest request){
		Products products = productsService.findById(id);
		productsService.deleteProducts(products);
		return "forward:businessProlist.do";
	}
	
	/**
	 * �����̼һ������ҳ���޸���Ʒ��Ϣ
	 * @param id
	 * @param model
	 * @return	��ת����Ʒ��Ϣ�޸�ҳ��
	 */
	@RequestMapping(value="/editUI")
	public String editUI(String id,ModelMap model){
		Products products = productsService.findById(id);
		model.put("product", products);
		return "BusinessClient/productEdit";
	}
	
	/**
	 * ������Ʒ��Ϣ�޸�ҳ���޸���Ʒ��Ϣ
	 * @param id
	 * @param model
	 * @param name
	 * @param type
	 * @param price
	 * @param memo
	 * @param instock
	 * @param file
	 * @param request
	 * @return	��ת���̼һ������ҳ��
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
            //����uuid��Ϊ�ļ�����  
            String uuid = UUID.randomUUID().toString().replaceAll("-","");  
            //����ļ����ͣ������ж��������ͼƬ����ֹ�ϴ���  
            String contentType=file.getContentType();  
            //����ļ���׺����  
            String imageName=contentType.substring(contentType.indexOf("/")+1);  
            path="/upload/products"+uuid+"."+imageName;  
            file.transferTo(new File(pathRoot+path));
            product.setImgurl(path);
        }
        productsService.updateProducts(product);
        return "forward:businessProlist.do";
	}
	
	/**
	 * ��ѯ��Ʒ��Ϣ
	 * ����session��ȡrole �жϲ�ͬ����תҳ��
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
	 * ���������Ʒ�б� �������
	 * @param id
	 * @param model
	 * @return	��ת����Ʒ����ҳ��
	 */
	@RequestMapping(value="/customerBuy")
	public String customerBuy(String id,ModelMap model){
		Products product = productsService.findById(id);
		model.put("product", product);
		return "CustomerClient/buyProducts";
	}
	
	/**
	 * �����������»�Ʒ��Ϣ
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
		product.setInstock(product.getInstock() - order.getNumber());	//���¿����
		Set<Order> orderSet = product.getOrders();						//���¶�����
		orderSet.add(order);
		product.setOrders(orderSet);
		product.setSelled(product.getSelled() + order.getNumber());		//������������
		productsService.updateProducts(product);
		product = productsService.findById(order.getProducts().getId());
		model.put("order",order);
		model.put("product", product);
		return "forward:/Products/customerProList.do";
	}
}
