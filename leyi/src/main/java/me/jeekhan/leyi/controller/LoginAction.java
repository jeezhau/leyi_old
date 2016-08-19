package me.jeekhan.leyi.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import jdk.nashorn.internal.ir.RuntimeNode.Request;
import me.jeekhan.leyi.common.FileFilter;
import me.jeekhan.leyi.common.SunSHAUtils;
import me.jeekhan.leyi.common.SysPropUtil;
import me.jeekhan.leyi.dto.Operator;
import me.jeekhan.leyi.model.ArticleBrief;
import me.jeekhan.leyi.model.UserFullInfo;
import me.jeekhan.leyi.service.ArticleService;
import me.jeekhan.leyi.service.UserService;

@Controller
@SessionAttributes({"operator","userInfo"})
public class LoginAction {
	@Autowired
	private UserService userService;
	@Autowired
	private ArticleService articleService;
	
	/**
	 * �û���¼
	 * ��Ȩ�ޡ�
	 * 		������
	 * ������˵����
	 * 		1���û�ʹ���û����������������е�¼
	 * 		2����¼�ɹ��ض�����������ҳ�����򷵻���ҳ
	 * �����������
	 * @param username
	 * @param password
	 * @param map
	 * @return	Ŀ��ҳ��
	 */
	@RequestMapping(value="/login")
	public String login(String username,String password,Map<String,Object>map,HttpServletRequest request){
		if(userService.authentification(username, password)){
			UserFullInfo userInfo = userService.getUserFullInfo(username);
			Operator operator = new Operator();
			operator.setLevel(9);
			operator.setUserId(userInfo.getId());
			operator.setUsername(username);
			map.put("operator", operator);
			map.put("userInfo", userInfo);
			return "redirect:/" + username;
		}else{
			return "forward:/login.jsp";
		}
	}
	/**
	 * �û�ע����¼
	 * ��Ȩ�ޡ�
	 * 		�ѵ�¼�û�
	 * ������˵����
	 * 		1�������¼��Ϣ��
	 * 		2���ض�����������ҳ��
	 * �����������
	 * @param map
	 * @return	Ŀ��ҳ��
	 */
	@RequestMapping(value="/logout")
	public String logout(Map<String,Object>map,SessionStatus session){
		Operator operator = (Operator) map.get("operator");
		if(operator != null && operator.getUserId()>0){
			session.setComplete();
			return "redirect:/" + operator.getUsername();
		}else{
			return "redirect:/";
		}
	}
	/**
	 * �û�ע��
	 * ��Ȩ�ޡ�
	 * 		������
	 * ������˵����
	 * 		1����֤�û���������Ψһ��
	 * 		2��ע��ɹ��ض�����������ҳ�����򷵻�ע��ҳ�沢��ʾ��ش�����Ϣ��
	 * 		3�������û�ͼƬ��ָ��·����
	 * �����������
	 * @param userInfo
	 * @param file
	 * @param map
	 * @param request
	 * @return	
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	@RequestMapping(value="/addUser",method=RequestMethod.POST)
	public String register(@Valid UserFullInfo userInfo,BindingResult result,@RequestParam(value="picFile",required=false)MultipartFile file,
			Map<String,Object>map,HttpServletRequest request) throws NoSuchAlgorithmException, IOException{
		if(result.hasErrors()){
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError e :list){
				String filed = e.getCodes()[0].substring(e.getCodes()[0].lastIndexOf('.')+1);
				map.put(filed, e.getDefaultMessage());
			}
			
			return "register";
		}
		String fileName = "";
		if(!file.isEmpty()){
			fileName = java.util.UUID.randomUUID().toString() + "." + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.')+1);
			userInfo.setPicture(fileName);
		}
		int id = userService.saveUser(userInfo);
		if(id<=0){
			if(id == -1){
				map.put("username", "���û����ѱ�ʹ��");
			}
			if(id == -2){
				map.put("email", "�������ѱ�ʹ��");
			}
			return "register";
		}
		if(!file.isEmpty()){
			String path = SysPropUtil.getParam("DIR_USER_UPLOAD") + userInfo.getUsername() + "/";  
			File dir = new File(path);
			if(!dir.exists()){
				dir.mkdirs();
			}
			userInfo.setPicture(fileName);
			FileOutputStream out = new FileOutputStream(path + fileName);
			InputStream in = file.getInputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			while((n=in.read(buf))>0){
				out.write(buf, 0, n);
			}
			out.close();
		}
		Operator operator = new Operator();
		operator.setLevel(1);
		operator.setUserId(id);
		operator.setUsername(userInfo.getUsername());
		map.put("operator", operator);
		return "redirect:/" + userInfo.getUsername();
	}
	
	/**
	 * ������ҳ
	 * ��Ȩ�ޡ�
	 * 		������
	 * ������˵����
	 * 		1������û�δ��¼���������ȡһ��������չ������ҳ���û�����ʾ���û�����Ϣ��
	 * 		2����ȡ�����ŵ�������ʾ��
	 * �����������
	 * @param map
	 * @return	Ŀ��ҳ��
	 */
	@RequestMapping(value="/")
	public String accessIndex(Map<String,Object>map){
		Operator operator = (Operator) map.get("operator");
		if(operator == null || operator.getUserId()<1){
			UserFullInfo userInfo = userService.getIndexShowUser();
			map.put("userInfo", userInfo);
		}
		List<ArticleBrief> hotnew = articleService.getHotNewArticles();
		map.put("hotnew", hotnew);
		return "index";
	}
	/**
	 * �û�ע���ʼ��
	 * ��Ȩ�ޡ�
	 * 	1��������
	 * ������˵����
	 * 	1���û�ע���ʼ����
	 * �����������
	 * @param map
	 * @return	Ŀ��ҳ��
	 */
	@RequestMapping(value="/register")
	public String register(Map<String,Object>map){

		return "register";
	}

}
