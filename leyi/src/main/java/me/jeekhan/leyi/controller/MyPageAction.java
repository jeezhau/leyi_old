package me.jeekhan.leyi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	 * 		2.取作者的主题信息；
	 * 		3.取当前主题、向上的主题树、直接下属主题；
	 * 		4.取当前主题下的文章；
	 * @param themeId
	 * @param operator
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/{username}/theme/{themeId}")
	public String MyIndexPage(@PathVariable("themeId")Integer themeId,@PathVariable("username")String username,
			Operator operator,Map<String,Object> map){
		UserFullInfo userInfo = userService.getUserFullInfo(username);
		ThemeClass currTheme = themeClassService.getThemeClass(themeId);
		
		if(userInfo != null && currTheme != null && userInfo.getId() == currTheme.getUpdateOpr()){
			boolean isSelf = false;
			if(operator.getUserId() == currTheme.getUpdateOpr() ){ //作者自己
				isSelf = true;
			}else {
				if(operator.getUserId()< 1 && !"0".equals(userInfo.getEnabled()) ){ //访问非正式用户
					return "redirect:/";
				}
				if(!"0".equals(currTheme.getEnabled())){ // 非正式主题
					return "redirect:/" + username;
				}
			}
			map.put("currTheme", currTheme);
			map.put("userInfo", userInfo);
			
			int id = userInfo.getId();
			List<ThemeClass> topThemes = themeClassService.getUserTopThemes(id,isSelf);
			map.put("topThemes",topThemes);
			
			List<ThemeClass> themeTreeUp = themeClassService.getThemeTreeUp(currTheme.getLogicId());
			map.put("themeTreeUp", themeTreeUp);
			
			List<ThemeClass> children = themeClassService.getChildThemes(currTheme.getLogicId(),isSelf);
			map.put("children",children);
			
			List<ArticleBrief> articleBriefs = articleService.getArticlesByTheme(currTheme.getLogicId(),isSelf, new PageCond());
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
	public String MyIndexPage(@PathVariable("username")String username ,Operator operator,Map<String,Object> map){
		UserFullInfo userInfo = userService.getUserFullInfo(username);
		if(userInfo != null){
			int id = userInfo.getId();
			boolean isSelf = false;
			if(operator.getUserId() == id ){ //作者自己
				isSelf = true;
			}else{
				if(operator.getUserId()<1 && !"0".equals(userInfo.getEnabled()) ){ //访问非正式用户
					return "redirect:/";
				}
			}
			map.put("userInfo", userInfo);
			List<ThemeClass> topThemes = themeClassService.getUserTopThemes(id,isSelf);
			map.put("topThemes",topThemes);

			List<ArticleBrief> articleBriefs = articleService.getArticlesByUser(id, isSelf,new PageCond());
			map.put("articleBriefs",articleBriefs);
			return "myIndex";
		}else{
			return "redirect:/";
		}
	}
	/**
	 * 显示文章详细信息
	 * 【权限】
	 * 	1、详情显示-所有人；
	 * 【功能说明】
	 * 	1.取文章信息，如果文章不存在则返回应用主页；
	 * 	2.取文章作者信息；
	 * @param articleId	文章ID
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/{username}/article/{articleId}",method=RequestMethod.GET)
	public String showArticle(@PathVariable("username")String username,@PathVariable("articleId")Integer articleId,Operator operator,Map<String,Object> map){
		UserFullInfo userInfo = userService.getUserFullInfo(username);
		if(userInfo == null){	//无该用户
			return "redirect:/";
		}
		ArticleBrief brief = articleService.getArticleBref(articleId);
		if(brief == null){	//无该文章
			return "redirect:/" + username;
		}
		ArticleContent content = articleService.getArticleContent(articleId);
		if(operator.getUserId() == brief.getUpdateOpr()){ 	//作者自己
			if("D".equals(brief.getEnabled())){
				return "redirect:/" + username;
			}
		}else{	//其他人
			if(operator.getUserId()<1 && !"0".equals(userInfo.getEnabled()) ){ //访问非正式用户
				return "redirect:/";
			}
			if(!"0".equals(brief.getEnabled())){	//非正式文章
				return "redirect:/" + username;
			}
		}
		map.put("brief", brief);
		map.put("userInfo", userInfo);	
		map.put("content", content);
	
		return "articleShow";
	}
	/**
	 * 显示用户详细信息
	 * 【权限】
	 * 	1、详情显示-所有人；
	 * 【功能说明】
	 * 	1.取用户信息，如果用户不存在则返回应用主页；
	 * @param userId	文章ID
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/{username}/detail",method=RequestMethod.GET)
	public String showUser(@PathVariable("username")String username,Operator operator,Map<String,Object> map){
		UserFullInfo userInfo = userService.getUserFullInfo(username);
		if(userInfo == null){	//无该用户
			return "redirect:/";
		}
		if(operator.getUserId() == userInfo.getId()){ 	//用户自己
			if("D".equals(userInfo.getEnabled())){
				return "redirect:/";
			}
		}else{	//其他人
			if(operator.getUserId()<1 && !"0".equals(userInfo.getEnabled()) ){ //访问非正式用户
				return "redirect:/";
			}
			if(!"0".equals(userInfo.getEnabled())){	//非正式用户
				return "redirect:/";
			}
		}
		map.put("userInfo", userInfo);	
	
		return "userShow";
	}
}
