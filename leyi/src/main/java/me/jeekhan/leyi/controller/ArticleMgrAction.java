package me.jeekhan.leyi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import me.jeekhan.leyi.common.PageCond;
import me.jeekhan.leyi.model.ArticleBrief;
import me.jeekhan.leyi.model.ArticleContent;
import me.jeekhan.leyi.model.ThemeClass;
import me.jeekhan.leyi.service.ArticleService;
import me.jeekhan.leyi.service.ThemeClassService;

@Controller
@RequestMapping("/article")
@SessionAttributes("currTheme")
public class ArticleMgrAction {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ThemeClassService themeClassService;

	/**
	 * 打开文章编辑
	 * 	如果有指定的文章Id，则获取文章简介和内容；否则直接返回
	 * @param articleId	文章ID
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/editing",method=RequestMethod.GET)
	public String editingArticle(@RequestParam(value="articleId",required=false)Integer articleId,Map<String,Object> map){
		if(articleId != null){
			ArticleBrief brief = articleService.getArticleBref(articleId);
			if(brief!=null){
				map.put("brief", brief);
			}
			ArticleContent content = articleService.getArticleContent(articleId);
			if(content != null){
				map.put("content", content);
			}
		}
		
		return "articleEditing";
	}
	/**
	 * 保存新增文章
	 * @param brief		文章简介信息
	 * @param content	文章内容
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addArticle(ArticleBrief brief,String content,Map<String,Object> map){
		ThemeClass theme = (ThemeClass) map.get("currTheme");
		int themeId;
		if(theme!=null){
			themeId = theme.getId();
		}else{
			themeId = 1;
		}
		brief.setEnabled("0");
		brief.setThemeId(themeId);
		brief.setUpdateOpr(1);
		int id = articleService.saveArticleBrief(brief);
		if(id >0){
			ArticleContent articleContent = new ArticleContent();
			articleContent.setContent(content);
			articleContent.setArticleId(id);
			articleService.saveArticleContent(articleContent);
		}
		
		return "redirect:/article/theme/" + themeId;
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
