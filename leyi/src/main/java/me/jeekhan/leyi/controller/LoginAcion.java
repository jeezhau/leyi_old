package me.jeekhan.leyi.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

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
			return "redirect:/mypage/";
		}else{
			return "index";
		}
	}
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String register(UserFullInfo userInfo,Map<String,Object>map) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		userInfo.setEnabled("1");
		userInfo.setRegistTime(new Date());
		userInfo.setRegistTime(new Date());
		userInfo.setPasswd(SunSHAUtils.encodeSHA512Hex(userInfo.getPasswd()));
		int id = userService.saveUser(userInfo);
		Operator operator = new Operator();
		operator.setLevel(1);
		operator.setUserId(id);
		operator.setUsername(userInfo.getUsername());
		map.put("operator", operator);
		return "redirect:/mypage/";
	}
	
}
