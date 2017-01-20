package me.jeekhan.leyi.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
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
import me.jeekhan.leyi.model.ReviewInfo;
import me.jeekhan.leyi.model.ThemeClass;
import me.jeekhan.leyi.model.UserFullInfo;
import me.jeekhan.leyi.service.ArticleService;
import me.jeekhan.leyi.service.ThemeClassService;
import me.jeekhan.leyi.service.UserService;
/**
 * 文章管理
 * @author Jee Khan
 *
 */
@Controller
@RequestMapping("/{username}/article_mgr")
@SessionAttributes({"currTheme","operator","topThemes"})
public class ArticleMgrAction {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ThemeClassService themeClassService;
	@Autowired
	private UserService userService;
	
	
	/**
	 * 显示文章详细信息
	 * 【权限】
	 * 	1、详情显示-所有人；
	 *  2、审核-管理员；如果为非管理员则变更模式为详情显示；
	 * 【功能说明】
	 * 完成文章信息内容显示及审核显示：detail-详情显示；review-审核显示
	 * 	1.取文章信息，如果文章不存在则返回应用主页；
	 * 	2.取文章作者信息；
	 * 	3.保存显示模式（详情或审核）；
	 * @param articleId	文章ID
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/{mode}/{articleId}",method=RequestMethod.GET)
	public String showArticle(@PathVariable("mode")String mode,@PathVariable("articleId")Integer articleId,Map<String,Object> map){
		ArticleBrief brief = articleService.getArticleBref(articleId);
		if(("detail".equals(mode) || "review".equals(mode)) && brief != null){
			map.put("mode", mode);
			map.put("brief", brief);
			if("review".equals(mode)){
				Operator operator = (Operator) map.get("operator");
				if(operator == null || operator.getLevel() < 9){
					mode = "detail";
				}
			}
			ArticleContent content = articleService.getArticleContent(articleId);
			if(content != null){
				map.put("content", content);
			}
			UserFullInfo userInfo = (UserFullInfo) map.get("userInfo");
			if(userInfo == null || brief.getUpdateOpr() != userInfo.getId()){
				userInfo = userService.getUserFullInfo(brief.getUpdateOpr());
				map.put("userInfo", userInfo);
			}
			return "articleShow";
		}else{
			return "redirect:/";
		}
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
	public String addArticle(@Valid ArticleBrief brief,BindingResult result,String content,
			@ModelAttribute("operator")Operator operator,Map<String,Object> map){
		if(result.hasErrors()){	//验证出错
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError e :list){
				String field = e.getCodes()[0].substring(e.getCodes()[0].lastIndexOf('.')+1);
				map.put("valid_"+field, e.getDefaultMessage());
			}
			return "articleEditing";
		}
		if(content.length()>10240){
			map.put("valid_content", "内容：最大长度为10K个字符！");
			return "articleEditing";
		}
		ThemeClass theme = (ThemeClass) map.get("currTheme");
		int themeId = theme.getId();
		brief.setThemeId(themeId);
		brief.setUpdateOpr(operator.getUserId());  
		int id = articleService.saveArticleBrief(brief);
		if(id >0){
			ArticleContent articleContent = new ArticleContent();
			articleContent.setContent(content);
			articleContent.setArticleId(id);
			articleService.saveArticleContent(articleContent);
		}
		return "redirect:/"+operator.getUsername()+"/article_mgr/theme/" + themeId + "/1";
	}
	/**
	 * 保存文章编辑
	 * @param brief		文章简介信息
	 * @param content	文章内容
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public String editArticle(@Valid ArticleBrief brief,BindingResult result,String content,
			@ModelAttribute("operator")Operator operator,Map<String,String> map){
		if(result.hasErrors()){	//验证出错
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError e :list){
				String field = e.getCodes()[0].substring(e.getCodes()[0].lastIndexOf('.')+1);
				map.put("valid." + field, e.getDefaultMessage());
			}
			return "articleEditing";
		}
		if(content.length()>10240){
			map.put("content", "内容：最大长度为10K个字符！");
			return "articleEditing";
		}
		String redirectUrl = "redirect:/"+operator.getUsername()+"/article_mgr/theme/";
		int id = brief.getId();
		ArticleBrief old = articleService.getArticleBref(id);
		if(old == null || old.getUpdateOpr()!= operator.getUserId()){ //无该文章或非属主
			map.put("error", "您无权限执行该操作！");
			return "articleEditing";
		}else{
			old.setName(brief.getName());
			old.setKeywords(brief.getKeywords());
			old.setSource(brief.getSource());
			old.setType(brief.getType());
			old.setBrief(brief.getBrief());
			ArticleContent articleContent = new ArticleContent();
			articleContent.setContent(content);
			articleContent.setArticleId(id);
			articleService.saveArticleInfo(old, articleContent);
			return redirectUrl + old.getThemeId() + "/1";
		}
	}
	/**
	 * 删除指定文章
	 * @param id	文章ID
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String deleteArticle(int articleId,@ModelAttribute("operator")Operator operator){
		String redirectUrl = "redirect:/"+operator.getUsername()+"/article_mgr/theme/";
		ArticleBrief old = articleService.getArticleBref(articleId);
		if(old == null || old.getUpdateOpr()!= operator.getUserId()){ 
			return redirectUrl + "?error=您无权限执行该操作！";
		}else{
			articleService.deleteArticle(articleId);
		}
		return redirectUrl + old.getThemeId() + "/1";
	}
	/**
	 * 文章审核：通过
	 * 【权限】
	 * 	1、仅登录的管理员可执行该操作；
	 * 【功能说明】
	 *  1、判断审核的文章是否存在；
	 *  2、执行审核通过
	 * @param articleId
	 * @param remark
	 * @param operator
	 * @return
	 */
	@RequestMapping(value="/accept",method=RequestMethod.POST)
	public String accept(Integer articleId,String remark,@ModelAttribute("operator")Operator operator){
		String redirectUrl = "redirect:/"+operator.getUsername()+"/article_mgr/review/" + articleId;
		if(operator == null || operator.getLevel() < 9){ //无权限
			return redirectUrl + "?error=您无权限执行该操作！";
		}
		if(articleId == null){ //文章为空
			return redirectUrl;
		}
		if(remark !=null && remark.length()>600){
			return redirectUrl + "?error=" + "审核说明：不可超过600个字符！";
		}
		ArticleBrief brief = articleService.getArticleBref(articleId);
		if(brief == null){ //无该文章
			return redirectUrl + "?error=系统中无该文章信息！";
		}
		ReviewInfo reviewInfo = new ReviewInfo();
		reviewInfo.setReviewInfo(remark);
		reviewInfo.setReviewOpr(operator.getUserId());
		articleService.reviewArticle(articleId,"0",reviewInfo);
		return "redirect:/"+operator.getUsername()+ "/review/";
	}
	/**
	 * 文章审核：拒绝
	 * 【权限】
	 * 	1、仅登录的管理员可执行该操作；
	 * 【功能说明】
	 *  1、判断审核的文章是否存在；
	 *  2、执行审核拒绝
	 * @param articleId
	 * @param remark
	 * @param operator
	 * @return
	 */
	@RequestMapping(value="/refuse",method=RequestMethod.POST)
	public String refuse(Integer articleId,String remark,@ModelAttribute("operator")Operator operator){
		String redirectUrl = "redirect:/"+operator.getUsername()+"/article_mgr/review/" + articleId ;
		if(operator == null || operator.getLevel() < 9){ //无权限
			return redirectUrl + "?error=您无权限执行该操作！";
		}
		
		if(articleId == null || remark == null || remark.trim().length()<1){ //主题或审核说明为空
			return redirectUrl + "?error=" + ((articleId == null)? "文章ID：不可为空！" : "审核说明：不可为空！");
		}
		if(remark.length()>600){
			return redirectUrl + "?error=" + "审核说明：不可超过600个字符！";
		}
		
		ArticleBrief brief = articleService.getArticleBref(articleId);
		if(brief == null){ //无该文章
			return redirectUrl + "?error=系统中无该文章信息！";
		}
		ReviewInfo reviewInfo = new ReviewInfo();
		reviewInfo.setReviewInfo(remark);
		reviewInfo.setReviewOpr(operator.getUserId());
		articleService.reviewArticle(articleId,"R",reviewInfo);
		return "redirect:/"+operator.getUsername()+ "/review/";
	}
	
	/**
	 * 取用户的指定主题下的所有文章
	 * 	设置用户的第一个顶层主题为当前主题；
	 * 	更新主题向上层次树；
	 * 	取当前主题的所有直接下属主题
	 *  取当前主题下的所有文章简介
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/theme/{themeId}/{begin}",method=RequestMethod.GET)
	public String article(@PathVariable("themeId") int themeId,@PathVariable("begin") int begin,@ModelAttribute("operator")Operator operator,Map<String,Object> map){
		ThemeClass currTheme = themeClassService.getThemeClass(themeId);
		map.put("currTheme", currTheme);
		List<ThemeClass> themeTreeUp = themeClassService.getThemeTreeUp(currTheme.getLogicId());
		map.put("themeTreeUp", themeTreeUp);
		List<ThemeClass> children = themeClassService.getChildThemes(currTheme.getLogicId(),true);
		map.put("children",children);
		boolean isSelf = false;
		if(operator.getUserId() == currTheme.getUpdateOpr() ){
			isSelf = true;
		}
		if(begin<1){//从1开始
			begin = 1;
			return "redirect:"+begin;
		}
		PageCond pageCond = new PageCond(begin);
		List<ArticleBrief> currArticles = articleService.getArticlesByTheme(currTheme.getLogicId(),isSelf,pageCond);
		map.put("currArticles", currArticles);
		map.put("pageCond", pageCond);
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
	public String article(@ModelAttribute("operator")Operator operator,Map<String,Object> map){
		@SuppressWarnings("unchecked")
		List<ThemeClass> topThemes = (List<ThemeClass>) map.get("topThemes");
		if(topThemes !=null && topThemes.size()>0){
			ThemeClass currTheme = topThemes.get(0);
			return "redirect:/" + operator.getUsername() + "/article_mgr/theme/" + currTheme.getId() + "/1";
		}
		return "articleMgr";
	}

}
