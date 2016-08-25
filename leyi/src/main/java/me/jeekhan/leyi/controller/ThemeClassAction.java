package me.jeekhan.leyi.controller;

import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import me.jeekhan.leyi.dto.Operator;
import me.jeekhan.leyi.model.ArticleBrief;
import me.jeekhan.leyi.model.ReviewInfo;
import me.jeekhan.leyi.model.ThemeClass;
import me.jeekhan.leyi.service.ThemeClassService;

/**
 * 主题相关访问控制类
 * @author Jee Khan
 *
 */
@RequestMapping("/{username}/theme_mgr")
@Controller
@SessionAttributes("operator")
public class ThemeClassAction {
	@Autowired
	private ThemeClassService themeClassService;
	
	/**
	 * 取用户自己的指定主题信息，包括所有直接下级主题分类
	 * 【权限】
	 *  1、登录用户
	 * 【功能说明】
	 * 	1、更新用户的顶层主题；
	 * 	2、设置指定主题为当前主题；
	 * 	3、更新当前主题的向上主题层次树；
	 * 	4、获取当前主题的所有直接下属主题；
	 * @param themeId
	 * @return
	 */
	@RequestMapping(value="/{themeId}",method=RequestMethod.GET)
	public String getTheme(@PathVariable("themeId")int themeId,@ModelAttribute("operator") Operator operator,Map<String,Object> map){
		String redirectUrl = "redirect:/" + operator.getUsername() + "/theme_mgr/";
		ThemeClass currTheme = themeClassService.getThemeClass(themeId);
		if(currTheme!=null){
			List<ThemeClass> topThemes = themeClassService.getUserTopThemes(operator.getUserId(),true);
			map.put("topThemes",topThemes);
			
			List<ThemeClass> themeTreeUp = themeClassService.getThemeTreeUp(themeId);
			map.put("themeTreeUp", themeTreeUp);
			
			List<ThemeClass> children = themeClassService.getChildThemes(themeId,true);
			
			map.put("currTheme", currTheme);
			map.put("children",children);
			
			return "themeClass";
		}else{
			return redirectUrl + "?error=系统中无该主题信息！";
		}
		
	}
	
	/**
	 * 仅超级用户才可使用该功能
	 * @return
	 */
	@RequestMapping(value="/all",method=RequestMethod.GET)
	@ResponseBody
	public List<ThemeClass> getAllThemes(){
		return null;
	}
	
	/**
	 * 添加主题
	 * 【权限】
	 *  1、登录用户
	 * 【功能说明】
	 *  1、有Id则更新，无ID则新插；
	 *  2、同层下不可存在同名的主题；
	 * @param theme
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addTheme(@Valid ThemeClass theme,BindingResult result,@ModelAttribute("operator") Operator operator){
		String redirectUrl = "redirect:/" + operator.getUsername() + "/theme_mgr/";
		if(result.hasErrors()){
			String errors = "";
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError e :list){
				errors += e.getDefaultMessage();
			}
			return redirectUrl + "?error=" + errors;
		}
		theme.setParentId(theme.getId());	//设置父主题
		theme.setId(null);
		theme.setUpdateOpr(operator.getUserId());
		int id = themeClassService.saveThemeClass(theme);
		if(id>0){
			return redirectUrl + id;
		}else{
			String msg = "";
			switch(id){
				case -1:
					msg = "数据不正确！";
					break;
				case -2:
					msg = "顶层主题个数大于6个！";
					break;
				case -3:
					msg = "存在同层同名的活动主题！";
					break;
			}
			return redirectUrl + "?error=" + msg;
		}
	}
	
	/**
	 * 更新主题
	 * 【权限】
	 *  1、登录用户
	 * 【功能说明】
	 *  1、验证权限：仅拥有者可更新；
	 * @param theme
	 * @param operator
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public String updateTheme(@Valid ThemeClass theme,BindingResult result,@ModelAttribute("operator") Operator operator,Map<String,String> map){
		String redirectUrl = "redirect:/" + operator.getUsername() + "/theme_mgr/";
		if(theme == null || theme.getId()==null){
			return redirectUrl + "?error=没有指定主题！";
		}
		if(result.hasErrors()){
			String errors = "";
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError e :list){
				errors += e.getDefaultMessage();
			}
			return redirectUrl + "?error=" + errors;
		}
		
		ThemeClass old = themeClassService.getThemeClass(theme.getId());
		if(old != null && old.getUpdateOpr() == operator.getUserId()){
			theme.setUpdateOpr(operator.getUserId());
			int id = themeClassService.saveThemeClass(theme);
			if(id>0){
				return redirectUrl + id;
			}else{
				String msg = "";
				switch(id){
					case -1:
						msg = "数据不正确！";
						break;
					case -2:
						msg = "顶层主题个数大于6个！";
						break;
					case -3:
						msg = "存在同层同名的活动主题！";
						break;
				}
				map.put("error", msg);
				return  redirectUrl + "?error=" + msg;
			}
		}else{	
			return redirectUrl + "?error=您无权限执行该操作！";
		}
	}
	
	/**
	 * 删除主题
	 * 【权限】
	 *  1、登录用户
	 * 【功能说明】
	 *  1、验证权限：仅拥有者可删除；
	 * @param themeId
	 * @param operator
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public String deleteTheme(ThemeClass theme,@ModelAttribute("operator") Operator operator){
		String redirectUrl = "redirect:/" + operator.getUsername() + "/theme_mgr/";
		if(theme == null || theme.getId()==null){
			return redirectUrl + "?error=没有指定主题！";
		}
		ThemeClass tmp = themeClassService.getThemeClass(theme.getId());
		if(tmp!=null && tmp.getUpdateOpr() == operator.getUserId()){
			Integer id = themeClassService.deleteThemeClass(theme.getId());
			return redirectUrl;
		}else{
			return redirectUrl + "?error=您无权限执行该操作！";
		}
	}
	
	/**
	 * 用户主题管理首页
	 * 【权限】
	 *  1、登录用户
	 * 【功能说明】
	 * 	1、默认设置第一个顶层主题为当前主题；
	 * 	2、设置当前主题的向上主题层次树；
	 *  3、获取当前主题的所有直接下属主题；
	 * @param themeId
	 * @return
	 */
	@RequestMapping(value="/*",method=RequestMethod.GET)
	public String getUserThemes(@ModelAttribute("operator") Operator operator,Map<String,Object> map){
		List<ThemeClass> topThemes = themeClassService.getUserTopThemes(operator.getUserId(),true);
		map.put("topThemes",topThemes);
		if(topThemes !=null && topThemes.size()>0){
			ThemeClass currTheme = topThemes.get(0);
			map.put("currTheme", currTheme);
			List<ThemeClass> themeTreeUp = themeClassService.getThemeTreeUp(currTheme.getId());
			map.put("themeTreeUp", themeTreeUp);
			List<ThemeClass> children = themeClassService.getChildThemes(currTheme.getId(),true);
			map.put("children",children);
		}
		return "themeClass";
	}
	/**
	 * 主题审核：通过
	 * 【权限】
	 * 	1、仅登录的管理员可执行该操作；
	 * 【功能说明】
	 *  1、判断审核的主题是否存在；
	 *  2、执行审核通过
	 * @param themeId
	 * @param remark
	 * @param operator
	 * @return
	 */
	@RequestMapping(value="/accept",method=RequestMethod.POST)
	public String accept(Integer themeId,String remark,@ModelAttribute("operator")Operator operator){
		String redirectUrl = "redirect:/"+operator.getUsername()+"/review";
		if(operator == null || operator.getLevel() < 9){ //无权限
			return redirectUrl + "?error=您无权限执行该操作！";
		}
		if(themeId == null){ //主题为空
			return redirectUrl;
		}
		if(remark !=null && remark.length()>600){
			return redirectUrl + "?error=" + "审核说明：不可超过600个字符！";
		}
		ThemeClass theme = themeClassService.getThemeClass(themeId);
		if(theme == null){ //无该主题
			return redirectUrl + "?error=系统中无该主题信息！";
		}
		ReviewInfo reviewInfo = new ReviewInfo();
		reviewInfo.setReviewInfo(remark);
		reviewInfo.setReviewOpr(operator.getUserId());
		themeClassService.reviewTheme(themeId, "0",reviewInfo);
		return redirectUrl;
	}
	/**
	 * 主题审核：拒绝
	 * 【权限】
	 * 	1、仅登录的管理员可执行该操作；
	 * 【功能说明】
	 *  1、判断审核的主题是否存在；
	 *  2、执行审核拒绝
	 * @param themeId
	 * @param remark
	 * @param operator
	 * @return
	 */
	@RequestMapping(value="/refuse",method=RequestMethod.POST)
	public String refuse(Integer themeId,String remark,@ModelAttribute("operator")Operator operator){
		String redirectUrl = "redirect:/"+operator.getUsername()+"/review";
		if(operator == null || operator.getLevel() < 9){ //无权限
			return redirectUrl + "?error=您无权限执行该操作！";
		}
		
		if(themeId == null || remark == null || remark.trim().length()<1){ //主题或审核说明为空
			return redirectUrl + "?error=" + ((themeId == null)? "主题ID：不可为空！" : "审核说明：不可为空！");
		}
		if(remark.length()>600){
			return redirectUrl + "?error=" + "审核说明不可超过600个字符！";
		}
		ThemeClass theme = themeClassService.getThemeClass(themeId);
		if(theme == null){ //无该主题
			return redirectUrl + "?error=系统中无该主题信息！";
		}
		ReviewInfo reviewInfo = new ReviewInfo();
		reviewInfo.setReviewInfo(remark);
		reviewInfo.setReviewOpr(operator.getUserId());
		themeClassService.reviewTheme(themeId, "R",reviewInfo);
		
		return redirectUrl;
	}
}
