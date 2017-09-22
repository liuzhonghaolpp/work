package com.liu.Action;

import java.io 

.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.liu.Service.BusinessService;
import com.liu.Entity.Business;

@Controller
@RequestMapping("/Business")
@SessionAttributes({"business","role"})
public class BusinessController {

	@Resource
	private BusinessService businessService;
	
	/**
	* 处理商家登陆页面传来的信息
	* 如果用户名正确则登陆成功
	* 若不正确返回错误信息
	**/
	@RequestMapping(value="/login")
	public String login(Business business,ModelMap model){
		System.out.println(business.getUsername()+business.getPassword());
		Business bus = businessService.findBusByNameAndPasssword(business.getUsername(), business.getPassword());
		if(bus!=null){
			model.put("business", bus);
			model.put("role", "bus");
			return "BusinessClient/main";
		}else{
			String result = "用户名密码错误";
			model.addAttribute("result", result);
			return "BusinessClient/login";
		}
	}
	
	/**
	* 处理商家注册页面传来的信息
	* 若注册成功则自动登录
	**/
	@RequestMapping(value="/register")
	public String register(Business business,ModelMap model){
		business.setOpenDate(new Date());
		businessService.saveBusiness(business);
		Business bus = businessService.findBusByNameAndPasssword(business.getUsername(), business.getPassword());
		return "BusinessClient/login";
	}

	
	/**
	* 处理商家个人信息修改页面传来的信息
	* 将修改信息保存到数据库并返回主页
	**/
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(ModelMap model,String id,String nickName,String telephone ,@RequestParam(value="file",required=false) MultipartFile file,  
            HttpServletRequest request)throws Exception{
		//System.out.println(business.getNickName());
        
        //获得物理路径webapp所在路径  
        String pathRoot = request.getSession().getServletContext().getRealPath("");  
        String path="";
        Business bus = businessService.findById(id);
        if(!file.isEmpty()){  
            //生成uuid作为文件名称  
            String uuid = UUID.randomUUID().toString().replaceAll("-","");  
            //获得文件类型（可以判断如果不是图片，禁止上传）  
            String contentType=file.getContentType();  
            //获得文件后缀名称  
            String imageName=contentType.substring(contentType.indexOf("/")+1);  
            path="/upload/"+uuid+"."+imageName;  
            file.transferTo(new File(pathRoot+path));
            bus.setImgurl(path);
        }  
        bus.setNickName(nickName);
        bus.setTelephone(telephone);
        businessService.updateBusiness(bus);
        bus = businessService.findById(id);
        model.put("business", bus);
        return "BusinessClient/main";  
	}
}