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
	 * ���������Service��
	 */
	@Autowired
	private CustomerService customerService;
	
	/**
	 * ������Ҷ���ҵ�¼ ��ѯ���ݿ�ȶ��û��� ����
	 * @param customer	�����Ϣ
	 * @param model		���model
	 * @return	�ɹ�����ת�������ҳ
	 * 			ʧ������ʾ���� Ҫ����������
	 */
	@RequestMapping("/login")
	public String login(Customer customer,ModelMap model){
		Customer cus = customerService.findCusByNameAndPasssword(customer.getUsername(), customer.getPassword());
		if(cus!=null){
			model.put("customer", cus);
			model.put("role","cus");
			return "CustomerClient/main";
		}else{
			String result = "�û����������";
			model.addAttribute("result", result);
			return "CustomerClient/login";
		}
	}
	
	/**
	 * ������Ҷ����ע��
	 * @param customer	�����Ϣ
	 * @param model		���model
	 * @return	ע����� ��ת����ҵ�¼ҳ��	
	 */
	@RequestMapping("/register")
	public String register(Customer customer,ModelMap model){
		customerService.saveCustomer(customer);
		Customer cus = customerService.findCusByNameAndPasssword(customer.getUsername(), customer.getPassword());
		model.put("customer", cus);
		return "CustomerClient/login";
	}
	
	/**
	 * ���� �� ���������Ϣ
	 * @param model		���model
	 * @param id		����id
	 * @param nickname	�޸Ĺ����ǳ�
	 * @param telephone	�޸Ĺ����ֻ���
	 * @param file		�޸Ĺ���ͷ��
	 * @param request
	 * @return	��ת�������ҳ
	 * @throws Exception
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(ModelMap model,String id,String nickname,String telephone ,@RequestParam(value="file",required=false) MultipartFile file,  
            HttpServletRequest request)throws Exception{
		//System.out.println(business.getNickName());
        
        //�������·��webapp����·��  
        String pathRoot = request.getSession().getServletContext().getRealPath("");  
        String path="";
        Customer cust = customerService.findById(id);
        if(!file.isEmpty()){  
            //����uuid��Ϊ�ļ�����  
            String uuid = UUID.randomUUID().toString().replaceAll("-","");  
            //����ļ����ͣ������ж��������ͼƬ����ֹ�ϴ���  
            String contentType=file.getContentType();  
            //����ļ���׺����  
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
