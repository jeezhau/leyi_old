package me.jeekhan.leyi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import me.jeekhan.leyi.common.PageCond;
import me.jeekhan.leyi.model.ArticleBrief;
import me.jeekhan.leyi.model.ThemeClass;
import me.jeekhan.leyi.service.ArticleService;
import me.jeekhan.leyi.service.ThemeClassService;

@Controller
@RequestMapping("/article")
public class ArticleMgrAction {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ThemeClassService themeClassService;

	/**
	 * 取用户的默认主题下的所有文章
	 * 	设置用户的第一顶层个主题为当前主题；
	 * 	更新主题向上层次树；
	 * 	--设置用户的所有主题；登录和主题变更时完成该功能
	 *  取当前主题下的所有文章简介
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/theme/{themeId}",method=RequestMethod.GET)
	public String article(@PathVariable("themeId") int themeId,Map<String,Object> map){
		List<ThemeClass> topThemes = themeClassService.getUserTopThemes(1);
		map.put("topThemes",topThemes);
		ThemeClass currTheme = themeClassService.getThemeClass(themeId);
		map.put("currTheme", currTheme);
		List<ThemeClass> themeTreeUp = themeClassService.getThemeTreeUp(currTheme.getId());
		map.put("themeTreeUp", themeTreeUp);
		List<ThemeClass> children = themeClassService.getChildThemes(currTheme.getId());
		map.put("children",children);
		List<ThemeClass> allUserThemes = themeClassService.getUserThemes(1);
		map.put("allUserThemes",allUserThemes);
		
		List<ArticleBrief> currArticles = articleService.getArticlesByTheme(currTheme.getId(), new PageCond());
		map.put("currArticles", currArticles);
		
		return "articleMgr";
	}
	/**
	 * 取用户的默认主题下的所有文章
	 * 	设置用户的第一顶层个主题为当前主题；
	 * 	更新主题向上层次树；
	 * 	--设置用户的所有主题；登录和主题变更时完成该功能
	 *  取当前主题下的所有文章简介
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/*",method=RequestMethod.GET)
	public String article(Map<String,Object> map){
		List<ThemeClass> topThemes = themeClassService.getUserTopThemes(1);
		map.put("topThemes",topThemes);
		ThemeClass currTheme = topThemes.get(0);
		map.put("currTheme", currTheme);
		List<ThemeClass> themeTreeUp = themeClassService.getThemeTreeUp(currTheme.getId());
		map.put("themeTreeUp", themeTreeUp);
		List<ThemeClass> children = themeClassService.getChildThemes(currTheme.getId());
		map.put("children",children);
		List<ThemeClass> allUserThemes = themeClassService.getUserThemes(1);
		map.put("allUserThemes",allUserThemes);
		
		List<ArticleBrief> currArticles = articleService.getArticlesByTheme(currTheme.getId(), new PageCond());
		map.put("currArticles", currArticles);
		
		return "articleMgr";
	}

}
