package me.jeekhan.leyi.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import me.jeekhan.leyi.common.PageCond;
import me.jeekhan.leyi.dto.Operator;
import me.jeekhan.leyi.model.ArticleBrief;
import me.jeekhan.leyi.model.ArticleContent;
import me.jeekhan.leyi.model.ThemeClass;
import me.jeekhan.leyi.model.UserFullInfo;
import me.jeekhan.leyi.service.ArticleService;
import me.jeekhan.leyi.service.ThemeClassService;
import me.jeekhan.leyi.service.UserService;
/**
 * –≈œ¢…Û∫À
 * @author Jee Khan
 *
 */
@Controller
@SessionAttributes({"operator"})
public class ReviewAction {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ThemeClassService themeClassService;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/review",method=RequestMethod.GET)
	public String review(@ModelAttribute("operator")Operator operator,Map<String,Object> map){
		if(operator.getUserId()>0){
			List<ArticleBrief> articles = articleService.getArticles4Review();
			map.put("articles", articles);
			return "articleShow";
		}else{
			return "redirect:/";
		}
	}

}
