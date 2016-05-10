package me.jeekhan.leyi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import me.jeekhan.leyi.model.UserFullInfo;
import me.jeekhan.leyi.service.UserService;

@Controller
@RequestMapping("/hi")
public class HelloWorld {
	public HelloWorld(){
		System.out.println("hhhhhhhhhhhhhh");
	}
	@Autowired
	private UserService userService;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/jee")
	public String hello(Map map){
		UserFullInfo user = userService.getUserFullInfo(1);
		map.put("user", user);
		userService.logOffUser(user.getId());
		return "hello";
	}
}
