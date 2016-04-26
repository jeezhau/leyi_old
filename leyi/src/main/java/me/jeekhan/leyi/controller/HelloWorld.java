package me.jeekhan.leyi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import me.jeekhan.leyi.model.User;
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
		User user = userService.getUser(1);
		map.put("user", user);
		return "hello";
	}
}
