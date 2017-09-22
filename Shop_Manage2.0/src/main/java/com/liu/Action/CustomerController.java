package com.liu.Action;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.liu.Entity.Business;
import com.liu.Entity.Customer;
import com.liu.Service.CustomerService;

@Controller
@RequestMapping("/Customer")
@SessionAttributes({"customer","role"})
public class CustomerController {

	/**
	 * 声明所需的Service层
	 */
	@Autowired
	private CustomerService customerService;
	
	/**
	 * 处理：买家端买家登录 查询数据库比对用户名 密码
	 * @param customer	买家信息
	 * @param model		输出model
	 * @return	成功则跳转至买家主页
	 * 			失败则提示错误 要求重新输入
	 */
	@RequestMapping("/login")
	public String login(Customer customer,ModelMap model){
		Customer cus = customerService.findCusByNameAndPasssword(customer.getUsername(), customer.getPassword());
		if(cus!=null){
			model.put("customer", cus);
			model.put("role","cus");
			return "CustomerClient/main";
		}else{
			String result = "用户名密码错误";
			model.addAttribute("result", result);
			return "CustomerClient/login";
		}
	}
	
	/**
	 * 处理：买家端买家注册
	 * @param customer	买家信息
	 * @param model		输出model
	 * @return	注册完成 跳转至买家登录页面	
	 */
	@RequestMapping("/register")
	public String register(Customer customer,ModelMap model){
		customerService.saveCustomer(customer);
		Customer cus = customerService.findCusByNameAndPasssword(customer.getUsername(), customer.getPassword());
		model.put("customer", cus);
		return "CustomerClient/login";
	}
	
	/**
	 * 处理 ： 更新买家信息
	 * @param model		输出model
	 * @param id		传入id
	 * @param nickname	修改过的昵称
	 * @param telephone	修改过的手机号
	 * @param file		修改过的头像
	 * @param request
	 * @return	跳转至买家主页
	 * @throws Exception
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(ModelMap model,String id,String nickname,String telephone ,@RequestParam(value="file",required=false) MultipartFile file,  
            HttpServletRequest request)throws Exception{
		//System.out.println(business.getNickName());
        
        //获得物理路径webapp所在路径  
        String pathRoot = request.getSession().getServletContext().getRealPath("");  
        String path="";
        Customer cust = customerService.findById(id);
        if(!file.isEmpty()){  
            //生成uuid作为文件名称  
            String uuid = UUID.randomUUID().toString().replaceAll("-","");  
            //获得文件类型（可以判断如果不是图片，禁止上传）  
            String contentType=file.getContentType();  
            //获得文件后缀名称  
            String imageName=contentType.substring(contentType.indexOf("/")+1);  
            path="/upload/"+uuid+"."+imageName;  
            file.transferTo(new File(pathRoot+path));
            cust.setImgurl(path);
        }  
        cust.setNickname(nickname);
        cust.setTelephone(telephone);
        customerService.updateCustomer(cust);
        cust = customerService.findById(id);
        model.put("customer", cust);
        //bus.setMemo(memo);
        return "CustomerClient/main";  
	}
	
}
