package com.liu.Action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.liu.Entity.Business;
import com.liu.Entity.Customer;
import com.liu.Entity.Order;
import com.liu.Entity.Page;
import com.liu.Entity.Products;
import com.liu.Service.CustomerService;
import com.liu.Service.OrderService;
import com.liu.Service.ProductsService;

@Controller
@RequestMapping("/Order")
public class OrderController {
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductsService productsService;
	
	/**
	* ����鿴������Ϣ������
	* ���̼������򷵻��̼Ҷ�������ҳ��
	* ����������򷵻������ʷ����ҳ��
	* ʵ���˷�ҳ��ѯ
	**/
	@RequestMapping(value="/orderList")
	public String orderlist(HttpSession session,ModelMap model,HttpServletRequest request){
		String role = (String) session.getAttribute("role");
		String pageNo = request.getParameter("pageNo");
		System.out.println(pageNo);
		if(role.equals("bus")){
			Business bus = (Business) session.getAttribute("business");
			String id = bus.getId();
			if(pageNo == null){
				pageNo = "1";
			}
			Page<Order> page = orderService.findByBusForPage(id, Integer.valueOf(pageNo), 6);
			List<Order> list = page.getList();
			model.put("list", list);
			request.setAttribute("page", page);
			return "BusinessClient/orderManage";
		}else{
			Customer cus = (Customer) session.getAttribute("customer");
			String id = cus.getId();
			if(pageNo == null){
				pageNo = "1";
			}
			Page<Order> page = orderService.findByCusForPage(id, Integer.valueOf(pageNo), 6);
			List<Order> list = page.getList();
			model.put("list", list);
			request.setAttribute("page", page);
			return "CustomerClient/orderManage";
		}
	}
	
	/**
	* ������ҹ�����Ʒ������
	* �����µĶ���
	**/
	@RequestMapping(value="/create")
	public String create(HttpSession session,String id,String number){
		Products product = productsService.findById(id);
		Customer customer = (Customer)session.getAttribute("customer");
		Customer cust = customerService.findById(customer.getId());
		Order order = new Order();
		order.setBusiness(product.getBusiness());
		order.setComment("");
		order.setCustomer(cust);
		order.setCustomer_1(cust.getNickname());
		order.setDate(new Date());
		order.setNumber(Integer.parseInt(number));
		order.setProduct(product.getName());
		order.setProducts(product);
		order.setTotalprice(Integer.parseInt(number)*product.getPrice());
		order.setState("δ����");
		String orderID = orderService.saveOrder(order);
		return "forward:/Products/updateInfoByOrder.do?orderID=" + orderID;
	}
	
	/**
	* ����������۶���������
	* ���������ʷ����ҳ��
	**/
	@RequestMapping(value="/addComment")
	public String addComment(String id,String comment){
		Order order = orderService.findById(id);
		order.setComment(comment);
		order.setState("�������");
		orderService.updateOrder(order);
		return "forward:/Order/orderList.do";
	}
	
	/**
	* �������ȷ���ջ�������
	* ���������ʷ����ҳ��
	**/
	@RequestMapping(value="confirmOrder")
	public String confirmOrder(String id,ModelMap model){
		Order order = orderService.findById(id);
		model.put("order", order);
		return "CustomerClient/confirmOrder";
	}
	
	/**
	* ������Ҳ鿴��ʷ������Ϣ������
	* ���������ʷ����ҳ��
	**/
	@RequestMapping(value="searchOrderInfo")
	public String searchOrderInfo(String id,ModelMap model){
		Order order = orderService.findById(id);
		model.put("order", order);
		return "CustomerClient/orderInfo";
	}
	/**
	* �����̼ҷ���������
	* �����̼ҹ�����ҳ��
	**/
	@RequestMapping("/sendProduct")
	public String sendproduct(String id){
		Order order = orderService.findById(id);
		order.setState("�ѷ���");
		orderService.updateOrder(order);
		return "forward:orderList.do";
	}
	
}