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
 * ������ط��ʿ�����
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
	 * ȡ�û��Լ���ָ��������Ϣ����������ֱ���¼��������
	 * ��Ȩ�ޡ�
	 *  1����¼�û�
	 * ������˵����
	 * 	1�������û��Ķ������⣻
	 * 	2������ָ������Ϊ��ǰ���⣻
	 * 	3�����µ�ǰ�������������������
	 * 	4����ȡ��ǰ���������ֱ���������⣻
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
	 * �������û��ſ�ʹ�øù���
	 * @return
	 */
	@RequestMapping(value="/all",method=RequestMethod.GET)
	@ResponseBody
	public List<ThemeClass> getAllThemes(){
		return null;
	}
	
	/**
	 * �������
	 * ��Ȩ�ޡ�
	 *  1����¼�û�
	 * ������˵����
	 *  1����֤�Ƿ��Ѿ���ͬ�������õ����⣻
	 * @param theme
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addTheme(ThemeClass theme,@ModelAttribute("operator") Operator operator){
		theme.setParentId(theme.getId());	//���ø�����
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
	 * ��������
	 * ��Ȩ�ޡ�
	 *  1����¼�û�
	 * ������˵����
	 *  1����֤Ȩ�ޣ���ӵ���߿ɸ��£�
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
	 * ɾ������
	 * ��Ȩ�ޡ�
	 *  1����¼�û�
	 * ������˵����
	 *  1����֤Ȩ�ޣ���ӵ���߿�ɾ����
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
	 * �û����������ҳ
	 * ��Ȩ�ޡ�
	 *  1����¼�û�
	 * ������˵����
	 * 	1��Ĭ�����õ�һ����������Ϊ��ǰ���⣻
	 * 	2�����õ�ǰ�������������������
	 *  3����ȡ��ǰ���������ֱ���������⣻
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
