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
	* �����̼ҵ�½ҳ�洫������Ϣ
	* ����û�����ȷ���½�ɹ�
	* ������ȷ���ش�����Ϣ
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
			String result = "�û����������";
			model.addAttribute("result", result);
			return "BusinessClient/login";
		}
	}
	
	/**
	* �����̼�ע��ҳ�洫������Ϣ
	* ��ע��ɹ����Զ���¼
	**/
	@RequestMapping(value="/register")
	public String register(Business business,ModelMap model){
		business.setOpenDate(new Date());
		businessService.saveBusiness(business);
		Business bus = businessService.findBusByNameAndPasssword(business.getUsername(), business.getPassword());
		return "BusinessClient/login";
	}

	
	/**
	* �����̼Ҹ�����Ϣ�޸�ҳ�洫������Ϣ
	* ���޸���Ϣ���浽���ݿⲢ������ҳ
	**/
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(ModelMap model,String id,String nickName,String telephone ,@RequestParam(value="file",required=false) MultipartFile file,  
            HttpServletRequest request)throws Exception{
		//System.out.println(business.getNickName());
        
        //�������·��webapp����·��  
        String pathRoot = request.getSession().getServletContext().getRealPath("");  
        String path="";
        Business bus = businessService.findById(id);
        if(!file.isEmpty()){  
            //����uuid��Ϊ�ļ�����  
            String uuid = UUID.randomUUID().toString().replaceAll("-","");  
            //����ļ����ͣ������ж��������ͼƬ����ֹ�ϴ���  
            String contentType=file.getContentType();  
            //����ļ���׺����  
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