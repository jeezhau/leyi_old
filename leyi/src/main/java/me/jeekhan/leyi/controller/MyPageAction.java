package me.jeekhan.leyi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
@SessionAttributes({"operator","topThemes"})
public class MyPageAction {
	@Autowired
	ThemeClassService themeClassService;
	@Autowired
	ArticleService  articleService;
	@Autowired
	UserService userService;

	/**
	 * 取指定主题信息及主题下的文章显示于主页
	 * 【权限】
	 * 		所有人
	 * 【功能说明】
	 * 		1.取作者信息；
	 * 		2.取作者的顶层主题信息；
	 * 		3.取当前主题、向上的主题树、直接下属主题；
	 * 		4.取当前主题下的文章；
	 * @param themeId
	 * @param operator
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/{username}/theme/{themeId}")
	public String MyIndexPage(@PathVariable("themeId")Integer themeId,@PathVariable("username")String username,
			@ModelAttribute("operator")Operator operator,Map<String,Object> map){
		UserFullInfo userInfo = userService.getUserFullInfo(username);
		ThemeClass currTheme = themeClassService.getThemeClass(themeId);
		if(userInfo != null && currTheme != null && userInfo.getId() == currTheme.getUpdateOpr()){
			map.put("currTheme", currTheme);
			map.put("userInfo", userInfo);
			
			int id = userInfo.getId();
			List<ThemeClass> topThemes = themeClassService.getUserTopThemes(id);
			map.put("topThemes",topThemes);
			
			List<ThemeClass> themeTreeUp = themeClassService.getThemeTreeUp(themeId);
			map.put("themeTreeUp", themeTreeUp);
			
			List<ThemeClass> children = themeClassService.getChildThemes(themeId);
			map.put("children",children);
			boolean reviewing = false;
			if(operator.getUserId() == currTheme.getUpdateOpr() ){
				reviewing = true;
			}
			List<ArticleBrief> articleBriefs = articleService.getArticlesByTheme(themeId,reviewing, new PageCond());
			map.put("articleBriefs",articleBriefs);
			return "myIndex";
		}else{
			return "redirect:/";
		}
	}
	/**
	 * 取作者的主页信息
	 * 【权限】
	 * 		所有人
	 * 【功能说明】
	 * 		1.取作者信息；
	 * 		2.取作者的顶层主题；
	 * 		3.取作者的所有最新最热门文章；
	 * @param username
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/{username}")
	public String MyIndexPage(@PathVariable("username")String username ,@ModelAttribute("operator")Operator operator,Map<String,Object> map){
		UserFullInfo userInfo = userService.getUserFullInfo(username);
		if(userInfo != null){
			int id = userInfo.getId();
			map.put("userInfo", userInfo);
			
			List<ThemeClass> topThemes = themeClassService.getUserTopThemes(id);
			map.put("topThemes",topThemes);
			boolean reviewing = false;
			if(operator.getUserId() == id ){
				reviewing = true;
			}
			List<ArticleBrief> articleBriefs = articleService.getArticlesByUser(id, reviewing,new PageCond());
			map.put("articleBriefs",articleBriefs);
			return "myIndex";
		}else{
			return "redirect:/";
		}
	}
}
