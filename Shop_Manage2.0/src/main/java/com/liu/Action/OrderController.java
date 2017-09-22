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
	* 处理查看订单信息的请求
	* 若商家请求则返回商家订单管理页面
	* 若买家请求则返回买家历史订单页面
	* 实现了分页查询
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
	* 处理买家购买商品的请求
	* 生成新的订单
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
		order.setState("未发货");
		String orderID = orderService.saveOrder(order);
		return "forward:/Products/updateInfoByOrder.do?orderID=" + orderID;
	}
	
	/**
	* 处理买家评论订单的请求
	* 返回买家历史订单页面
	**/
	@RequestMapping(value="/addComment")
	public String addComment(String id,String comment){
		Order order = orderService.findById(id);
		order.setComment(comment);
		order.setState("交易完成");
		orderService.updateOrder(order);
		return "forward:/Order/orderList.do";
	}
	
	/**
	* 处理买家确认收货的请求
	* 返回买家历史订单页面
	**/
	@RequestMapping(value="confirmOrder")
	public String confirmOrder(String id,ModelMap model){
		Order order = orderService.findById(id);
		model.put("order", order);
		return "CustomerClient/confirmOrder";
	}
	
	/**
	* 处理买家查看历史订单信息的请求
	* 返回买家历史订单页面
	**/
	@RequestMapping(value="searchOrderInfo")
	public String searchOrderInfo(String id,ModelMap model){
		Order order = orderService.findById(id);
		model.put("order", order);
		return "CustomerClient/orderInfo";
	}
	/**
	* 处理商家发货的请求
	* 返回商家管理订单页面
	**/
	@RequestMapping("/sendProduct")
	public String sendproduct(String id){
		Order order = orderService.findById(id);
		order.setState("已发货");
		orderService.updateOrder(order);
		return "forward:orderList.do";
	}
	
}