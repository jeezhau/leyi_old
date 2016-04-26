package me.jeekhan.leyi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import me.jeekhan.leyi.dao.UserMapper;
import me.jeekhan.leyi.model.User;
import me.jeekhan.leyi.service.impl.UserService;

@Controller
public class HelloWorld {
	@Autowired
	private UserService userService;
	@Autowired
	private UserMapper userMapper;
	@RequestMapping("/springmvc")
	public String helloworld(Map model){
		User user = userService.getUser(1);
		model.put("user", user);
		System.out.println("helloworld...");
		return "success";
	}
	

}
