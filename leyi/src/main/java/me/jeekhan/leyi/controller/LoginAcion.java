package me.jeekhan.leyi.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import me.jeekhan.leyi.common.PageCond;
import me.jeekhan.leyi.dto.Operator;
import me.jeekhan.leyi.model.ArticleBrief;
import me.jeekhan.leyi.model.ThemeClass;
import me.jeekhan.leyi.model.UserFullInfo;
import me.jeekhan.leyi.service.ArticleService;
import me.jeekhan.leyi.service.ThemeClassService;
import me.jeekhan.leyi.service.UserService;

@Controller
@SessionAttributes({"operator"})
public class LoginAcion {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(String username,String password,HttpSession session){
		if(userService.authentification(username, password)){
			UserFullInfo userInfo = userService.getUserFullInfo(username);
			Operator operator = new Operator();
			operator.setLevel(0);
			operator.setUserId(userInfo.getId());
			operator.setUsername(username);
			session.setAttribute("operator", operator);
			return "redirect:/mypage/";
		}else{
			return "index";
		}
	}
	
}
