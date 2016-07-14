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
import me.jeekhan.leyi.service.ArticleService;
import me.jeekhan.leyi.service.ThemeClassService;

@Controller
@RequestMapping("/article")
@SessionAttributes({"currTheme","operator","topThemes"})
public class ArticleMgrAction {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ThemeClassService themeClassService;
	
	/**
	 * 显示文章
	 * @param articleId	文章ID
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/{articleId}",method=RequestMethod.GET)
	public String showArticle(@PathVariable("articleId")Integer articleId,Map<String,Object> map){
		ArticleBrief brief = articleService.getArticleBref(articleId);
		if(brief!=null){
			map.put("brief", brief);
		}
		ArticleContent content = articleService.getArticleContent(articleId);
		if(content != null){
			map.put("content", content);
		}
		return "articleShow";
	}

	/**
	 * 打开文章编辑
	 * 【权限】
	 * 		1.登录；
	 * 		2.文章所属用户可编辑,否则返回首页；
	 * 【功能说明】
	 * 		如果有指定的文章Id，则获取文章简介和内容；否则直接返回
	 * 【输入输出】
	 * @param articleId	文章ID
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/editing",method=RequestMethod.GET)
	public String editingArticle(@RequestParam(value="articleId",required=false)Integer articleId,
			@ModelAttribute("operator")Operator operator,Map<String,Object> map){
		if(articleId != null){
			ArticleBrief brief = articleService.getArticleBref(articleId);
			if(brief.getUpdateOpr() != operator.getUserId()){
				return "index";
			}
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
	 * 【权限】
	 * 		1.登录
	 * 【输入输出】 
	 * @param brief		文章简介信息
	 * @param content	文章内容
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addArticle(ArticleBrief brief,String content,
			@ModelAttribute("operator")Operator operator,Map<String,Object> map){
		ThemeClass theme = (ThemeClass) map.get("currTheme");
		int themeId = theme.getId();
		brief.setEnabled("0");
		brief.setThemeId(themeId);
		brief.setUpdateOpr(operator.getUserId());  
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
	 * 保存文章编辑
	 * @param brief		文章简介信息
	 * @param content	文章内容
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public String editArticle(ArticleBrief brief,String content,@ModelAttribute("operator")Operator operator){
		int id = brief.getId();
		ArticleBrief old = articleService.getArticleBref(id);
		if(old == null || old.getUpdateOpr()!= operator.getUserId()){ 
			;
		}else{
			old.setName(brief.getName());
			old.setKeywords(brief.getKeywords());
			old.setSource(brief.getSource());
			old.setType(brief.getType());
			old.setBrief(brief.getBrief());
			old.setEnabled("0");
			old.setUpdateTime(new Date());
			ArticleContent articleContent = new ArticleContent();
			articleContent.setContent(content);
			articleContent.setArticleId(id);
			articleService.saveArticleInfo(old, articleContent);
		}
		return "redirect:/article/theme/" + old.getThemeId();
	}
	/**
	 * 删除指定文章
	 * @param id	文章ID
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String deleteArticle(int articleId,@ModelAttribute("operator")Operator operator){
		ArticleBrief old = articleService.getArticleBref(articleId);
		if(old == null || old.getUpdateOpr()!= operator.getUserId()){ 
			;
		}else{
			articleService.deleteArticle(articleId);
		}
		return "redirect:/article/theme/" + old.getThemeId();
	}
	
	/**
	 * 取用户的默认主题下的所有文章
	 * 	设置用户的第一个顶层主题为当前主题；
	 * 	更新主题向上层次树；
	 * 	取当前主题的所有直接下属主题
	 *  取当前主题下的所有文章简介
	 * @param map
	 * @return
	 */
	
	@RequestMapping(value="/theme/{themeId}",method=RequestMethod.GET)
	public String article(@PathVariable("themeId") int themeId,@ModelAttribute("operator")Operator operator,Map<String,Object> map){
		ThemeClass currTheme = themeClassService.getThemeClass(themeId);
		map.put("currTheme", currTheme);
		List<ThemeClass> themeTreeUp = themeClassService.getThemeTreeUp(currTheme.getId());
		map.put("themeTreeUp", themeTreeUp);
		List<ThemeClass> children = themeClassService.getChildThemes(currTheme.getId());
		map.put("children",children);	
		List<ArticleBrief> currArticles = articleService.getArticlesByTheme(currTheme.getId(), new PageCond());
		map.put("currArticles", currArticles);
		
		return "articleMgr";
	}
	/**
	 * 取用户的默认主题下的所有文章
	 * 	设置用户的第一顶层个主题为当前主题；
	 * 	更新主题向上层次树；
	 * 	取当前主题的所有直接下属主题
	 *  取当前主题下的所有文章简介
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/*",method=RequestMethod.GET)
	public String article(Map<String,Object> map){
		@SuppressWarnings("unchecked")
		List<ThemeClass> topThemes = (List<ThemeClass>) map.get("topThemes");
		ThemeClass currTheme = topThemes.get(0);
		map.put("currTheme", currTheme);
		List<ThemeClass> themeTreeUp = themeClassService.getThemeTreeUp(currTheme.getId());
		map.put("themeTreeUp", themeTreeUp);
		List<ThemeClass> children = themeClassService.getChildThemes(currTheme.getId());
		map.put("children",children);

		List<ArticleBrief> currArticles = articleService.getArticlesByTheme(currTheme.getId(), new PageCond());
		map.put("currArticles", currArticles);
		
		return "articleMgr";
	}

}
