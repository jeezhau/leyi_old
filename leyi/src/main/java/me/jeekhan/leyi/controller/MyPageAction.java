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
@RequestMapping("/mypage")
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
	 * @param themeId
	 * @param operator
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/theme/{themeId}")
	public String MyIndexPage(@PathVariable("themeId")Integer themeId,
			@ModelAttribute("operator")Operator operator,Map<String,Object> map){
		List<ThemeClass> topThemes = themeClassService.getUserTopThemes(operator.getUserId());
		map.put("topThemes",topThemes);
		
		List<ThemeClass> themeTreeUp = themeClassService.getThemeTreeUp(themeId);
		map.put("themeTreeUp", themeTreeUp);
		
		List<ThemeClass> children = themeClassService.getChildThemes(themeId);
		map.put("children",children);
		
		ThemeClass currTheme = themeClassService.getThemeClass(themeId);
		map.put("currTheme", currTheme);
		
		List<ArticleBrief> articleBriefs = articleService.getArticlesByTheme(themeId, new PageCond());
		map.put("articleBriefs",articleBriefs);
		return "myIndex";
	}
	/**
	 * 取用户的主页信息
	 * 	
	 * @param username
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/{username}")
	public String MyIndexPage(@PathVariable("username")String username ,Map<String,Object> map){
		UserFullInfo userInfo = userService.getUserFullInfo(username);
		int id = userInfo.getId();
		map.put("userInfo", userInfo);
		
		List<ThemeClass> topThemes = themeClassService.getUserTopThemes(id);
		map.put("topThemes",topThemes);

		List<ArticleBrief> articleBriefs = articleService.getArticlesByUser(id, new PageCond());
		map.put("articleBriefs",articleBriefs);
		return "myIndex";
	}
}
