package me.jeekhan.leyi.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import jdk.nashorn.internal.ir.RuntimeNode.Request;
import me.jeekhan.leyi.common.SunSHAUtils;
import me.jeekhan.leyi.dto.Operator;
import me.jeekhan.leyi.model.UserFullInfo;
import me.jeekhan.leyi.service.UserService;

@Controller
@SessionAttributes({"operator"})
public class LoginAcion {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/login")
	public String login(String username,String password,Map<String,Object>map){
		if(userService.authentification(username, password)){
			UserFullInfo userInfo = userService.getUserFullInfo(username);
			Operator operator = new Operator();
			operator.setLevel(0);
			operator.setUserId(userInfo.getId());
			operator.setUsername(username);
			map.put("operator", operator);
			return "redirect:/mypage/" + username;
		}else{
			return "index";
		}
	}
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String register(UserFullInfo userInfo,@RequestParam(value="picFile",required=false)MultipartFile file,Map<String,Object>map,HttpServletRequest request) throws NoSuchAlgorithmException, IOException{
		userInfo.setEnabled("1");
		userInfo.setRegistTime(new Date());
		userInfo.setRegistTime(new Date());
		userInfo.setPasswd(SunSHAUtils.encodeSHA512Hex(userInfo.getPasswd()));
		if(file != null){
			userInfo.setPicture(file.getOriginalFilename());
		}
		int id = userService.saveUser(userInfo);
		if(id<=0){
			if(id == -1){
				map.put("username_msg", "该用户名已被使用");
			}
			if(id == -2){
				map.put("email_msg", "该邮箱已被使用");
			}
			return "redirect:/register.jsp";
		}
		if(file != null){
			String path = request.getSession().getServletContext().getRealPath("upload/" + userInfo.getUsername() + "/");  
			File dir = new File(path);
			if(!dir.exists()){
				dir.mkdirs();
			}
			userInfo.setPicture(file.getOriginalFilename());
			FileOutputStream out = new FileOutputStream(path + file.getOriginalFilename());
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
		return "redirect:/mypage/" + userInfo.getUsername();
	}
	
}
