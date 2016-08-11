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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import me.jeekhan.leyi.dto.Operator;
import me.jeekhan.leyi.model.ThemeClass;
import me.jeekhan.leyi.service.ThemeClassService;

/**
 * 主题相关访问控制类
 * @author Jee Khan
 *
 */
@RequestMapping("/theme")
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
		ThemeClass currTheme = themeClassService.getThemeClass(themeId);
		if(currTheme!=null){
			List<ThemeClass> topThemes = themeClassService.getUserTopThemes(operator.getUserId(),true);
			map.put("topThemes",topThemes);
			
			List<ThemeClass> themeTreeUp = themeClassService.getThemeTreeUp(themeId);
			map.put("themeTreeUp", themeTreeUp);
			
			List<ThemeClass> children = themeClassService.getChildThemes(themeId,true);
			
			map.put("currTheme", currTheme);
			map.put("children",children);
		}else{
			return "redirect:/theme/";
		}
		return "themeClass";
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
	 *  1、验证是否已经在同名且启用的主题；
	 * @param theme
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addTheme(ThemeClass theme,@ModelAttribute("operator") Operator operator){
		theme.setParentId(theme.getId());	//设置父主题
		if(theme.getParentId() == null || theme.getParentId()!= null && themeClassService.getThemeClass(theme.getName())==null){
			theme.setId(null);
			theme.setUpdateOpr(operator.getUserId());
			int id = themeClassService.saveThemeClass(theme);
			return "redirect:/theme/" + id;
		}else{
			return "redirect:/theme/";
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
	public String updateTheme(ThemeClass theme,@ModelAttribute("operator") Operator operator){
		if(theme.getUpdateOpr() == operator.getUserId() && operator.hasTheme(theme.getId())){
			int id = themeClassService.saveThemeClass(theme);
			return "redirect:/theme/" + id;
		}else{
			return "redirect:/theme/";
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
		if(theme.getUpdateOpr() == operator.getUserId() && operator.hasTheme(theme.getId())){
			Integer id = themeClassService.deleteThemeClass(theme.getId());
			if(id == null){
				return "redirect:/theme/";
			}
			return "redirect:/theme/" + id;
		}else{
			return "redirect:/theme/";
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
	
}
