package me.jeekhan.leyi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import me.jeekhan.leyi.dto.Operator;
import me.jeekhan.leyi.service.UserService;

@Controller
@SessionAttributes("operator")
public class LoginAcion {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	//@ResponseBody
	public String login(String username,String password,Model model){
		if("jee".equals(username)&& "jee".equals(password)){
			Operator operator = new Operator();
			operator.setLevel(0);
			operator.setUserId(1);
			operator.setUsername(username);
			model.addAttribute("operator", operator);
			return "loginSuccess";
		}else{
			return "hello";
		}
	}
	
}
