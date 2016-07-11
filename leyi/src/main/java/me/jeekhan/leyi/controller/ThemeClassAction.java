package me.jeekhan.leyi.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

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
	 * ȡָ��������Ϣ����������ֱ���¼��������
	 * 	�����û��Ķ������⣻
	 * 	����ָ������Ϊ��ǰ���⣻
	 * 	���µ�ǰ�������������������
	 * 	��ȡ��ǰ���������ֱ���������⣻
	 * @param themeId
	 * @return
	 */
	@RequestMapping(value="/{themeId}",method=RequestMethod.GET)
	public String getTheme(@PathVariable("themeId")int themeId,Map<String,Object> map){
		ThemeClass currTheme = themeClassService.getThemeClass(themeId);
		if(currTheme!=null){
			List<ThemeClass> topThemes = themeClassService.getUserTopThemes(1);
			map.put("topThemes",topThemes);
			
			List<ThemeClass> themeTreeUp = themeClassService.getThemeTreeUp(themeId);
			map.put("themeTreeUp", themeTreeUp);
			
			List<ThemeClass> children = themeClassService.getChildThemes(themeId);
			
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
	 * @param theme
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	//public String addTheme(ThemeClass theme,@ModelAttribute("operator") Operator operator){
	public String addTheme(ThemeClass theme){
//		if(theme.getParentId()==null || operator.hasTheme(theme.getParentId())){
			theme.setParentId(theme.getId());
			theme.setId(null);
			theme.setEnabled("0");
			theme.setUpdateOpr(1);
			theme.setUpdateTime(new Date());
			int id = themeClassService.saveThemeClass(theme);
			return "redirect:/theme/" + id;
//		}else{
//			return "failure";
//		}
	}
	
	/**
	 * ��������
	 * @param theme
	 * @param operator
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
//	public String updateTheme(ThemeClass theme,@ModelAttribute("operator") Operator operator){
//		if((theme.getParentId() == null || operator.hasTheme(theme.getParentId())) 
//				&& operator.hasTheme(theme.getId())){
	public String updateTheme(ThemeClass theme){
			theme.setEnabled("0");
			theme.setUpdateTime(new Date());
			int id = themeClassService.saveThemeClass(theme);
			return "redirect:/theme/" + id;
//		}else{
//			return "failure";
//		}
	}
	
	/**
	 * ɾ������
	 * @param themeId
	 * @param operator
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
//	public String deleteTheme(ThemeClass theme,@ModelAttribute("operator") Operator operator){
//		if(!operator.hasTheme(themeId)){
	public String deleteTheme(ThemeClass theme){
			Integer id = themeClassService.deleteThemeClass(theme.getId());
			if(id == null){
				return "redirect:/theme/";
			}
			return "redirect:/theme/" + id;
//		}else{
//			return "failure";
//		}
	}
	
	/**
	 * ȡ�û�ȫ������������Ϣ��
	 * 	Ĭ�����õ�һ����������Ϊ��ǰ���⣻
	 * 	���õ�ǰ�������������������
	 *  ��ȡ��ǰ���������ֱ���������⣻
	 * @param themeId
	 * @return
	 */
	@RequestMapping(value="/*",method=RequestMethod.GET)
	public String getUserThemes(Map<String,Object> map){
		List<ThemeClass> topThemes = themeClassService.getUserTopThemes(1);
		map.put("topThemes",topThemes);
		ThemeClass currTheme = topThemes.get(0);
		List<ThemeClass> themeTreeUp = themeClassService.getThemeTreeUp(currTheme.getId());
		map.put("themeTreeUp", themeTreeUp);
		List<ThemeClass> children = themeClassService.getChildThemes(currTheme.getId());
		map.put("currTheme", currTheme);
		map.put("children",children);
		return "themeClass";
	}
	
}
