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
	 * 用户登录
	 * 【权限】
	 * 		所用人
	 * 【功能说明】
	 * 		1、用户使用用户名或邮箱加密码进行登录
	 * 		2、登录成功重定向至个人主页，否则返回首页
	 * 【输入输出】
	 * @param username
	 * @param password
	 * @param map
	 * @return	目标页面
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
	 * 用户注销登录
	 * 【权限】
	 * 		已登录用户
	 * 【功能说明】
	 * 		1、清除登录信息；
	 * 		2、重定向至个人主页；
	 * 【输入输出】
	 * @param map
	 * @return	目标页面
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
	 * 用户注册
	 * 【权限】
	 * 		所用人
	 * 【功能说明】
	 * 		1、验证用户名或邮箱唯一；
	 * 		2、注册成功重定向至个人主页，否则返回注册页面并显示相关错误信息；
	 * 		3、保存用户图片至指定路径；
	 * 【输入输出】
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
				map.put("username", "该用户名已被使用");
			}
			if(id == -2){
				map.put("email", "该邮箱已被使用");
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
	 * 访问首页
	 * 【权限】
	 * 		所用人
	 * 【功能说明】
	 * 		1、如果用户未登录，则随机获取一个可用于展现于首页的用户，显示该用户的信息；
	 * 		2、获取最热门的文章显示；
	 * 【输入输出】
	 * @param map
	 * @return	目标页面
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
	 * 用户注册初始化
	 * 【权限】
	 * 	1、所用人
	 * 【功能说明】
	 * 	1、用户注册初始化；
	 * 【输入输出】
	 * @param map
	 * @return	目标页面
	 */
	@RequestMapping(value="/register")
	public String register(Map<String,Object>map){

		return "register";
	}

}
