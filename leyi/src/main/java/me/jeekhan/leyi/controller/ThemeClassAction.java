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
 * ������ط��ʿ�����
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
			return redirectUrl + "?error=ϵͳ���޸�������Ϣ��";
		}
		
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
	 *  1����Id����£���ID���²壻
	 *  2��ͬ���²��ɴ���ͬ�������⣻
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
		theme.setParentId(theme.getId());	//���ø�����
		theme.setId(null);
		theme.setUpdateOpr(operator.getUserId());
		int id = themeClassService.saveThemeClass(theme);
		if(id>0){
			return redirectUrl + id;
		}else{
			String msg = "";
			switch(id){
				case -1:
					msg = "���ݲ���ȷ��";
					break;
				case -2:
					msg = "���������������6����";
					break;
				case -3:
					msg = "����ͬ��ͬ���Ļ���⣡";
					break;
			}
			return redirectUrl + "?error=" + msg;
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
	public String updateTheme(@Valid ThemeClass theme,BindingResult result,@ModelAttribute("operator") Operator operator,Map<String,String> map){
		String redirectUrl = "redirect:/" + operator.getUsername() + "/theme_mgr/";
		if(theme == null || theme.getId()==null){
			return redirectUrl + "?error=û��ָ�����⣡";
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
						msg = "���ݲ���ȷ��";
						break;
					case -2:
						msg = "���������������6����";
						break;
					case -3:
						msg = "����ͬ��ͬ���Ļ���⣡";
						break;
				}
				map.put("error", msg);
				return  redirectUrl + "?error=" + msg;
			}
		}else{	
			return redirectUrl + "?error=����Ȩ��ִ�иò�����";
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
		String redirectUrl = "redirect:/" + operator.getUsername() + "/theme_mgr/";
		if(theme == null || theme.getId()==null){
			return redirectUrl + "?error=û��ָ�����⣡";
		}
		ThemeClass tmp = themeClassService.getThemeClass(theme.getId());
		if(tmp!=null && tmp.getUpdateOpr() == operator.getUserId()){
			Integer id = themeClassService.deleteThemeClass(theme.getId());
			return redirectUrl;
		}else{
			return redirectUrl + "?error=����Ȩ��ִ�иò�����";
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
	/**
	 * ������ˣ�ͨ��
	 * ��Ȩ�ޡ�
	 * 	1������¼�Ĺ���Ա��ִ�иò�����
	 * ������˵����
	 *  1���ж���˵������Ƿ���ڣ�
	 *  2��ִ�����ͨ��
	 * @param themeId
	 * @param remark
	 * @param operator
	 * @return
	 */
	@RequestMapping(value="/accept",method=RequestMethod.POST)
	public String accept(Integer themeId,String remark,@ModelAttribute("operator")Operator operator){
		String redirectUrl = "redirect:/"+operator.getUsername()+"/review";
		if(operator == null || operator.getLevel() < 9){ //��Ȩ��
			return redirectUrl + "?error=����Ȩ��ִ�иò�����";
		}
		if(themeId == null){ //����Ϊ��
			return redirectUrl;
		}
		if(remark !=null && remark.length()>600){
			return redirectUrl + "?error=" + "���˵�������ɳ���600���ַ���";
		}
		ThemeClass theme = themeClassService.getThemeClass(themeId);
		if(theme == null){ //�޸�����
			return redirectUrl + "?error=ϵͳ���޸�������Ϣ��";
		}
		ReviewInfo reviewInfo = new ReviewInfo();
		reviewInfo.setReviewInfo(remark);
		reviewInfo.setReviewOpr(operator.getUserId());
		themeClassService.reviewTheme(themeId, "0",reviewInfo);
		return redirectUrl;
	}
	/**
	 * ������ˣ��ܾ�
	 * ��Ȩ�ޡ�
	 * 	1������¼�Ĺ���Ա��ִ�иò�����
	 * ������˵����
	 *  1���ж���˵������Ƿ���ڣ�
	 *  2��ִ����˾ܾ�
	 * @param themeId
	 * @param remark
	 * @param operator
	 * @return
	 */
	@RequestMapping(value="/refuse",method=RequestMethod.POST)
	public String refuse(Integer themeId,String remark,@ModelAttribute("operator")Operator operator){
		String redirectUrl = "redirect:/"+operator.getUsername()+"/review";
		if(operator == null || operator.getLevel() < 9){ //��Ȩ��
			return redirectUrl + "?error=����Ȩ��ִ�иò�����";
		}
		
		if(themeId == null || remark == null || remark.trim().length()<1){ //��������˵��Ϊ��
			return redirectUrl + "?error=" + ((themeId == null)? "����ID������Ϊ�գ�" : "���˵��������Ϊ�գ�");
		}
		if(remark.length()>600){
			return redirectUrl + "?error=" + "���˵�����ɳ���600���ַ���";
		}
		ThemeClass theme = themeClassService.getThemeClass(themeId);
		if(theme == null){ //�޸�����
			return redirectUrl + "?error=ϵͳ���޸�������Ϣ��";
		}
		ReviewInfo reviewInfo = new ReviewInfo();
		reviewInfo.setReviewInfo(remark);
		reviewInfo.setReviewOpr(operator.getUserId());
		themeClassService.reviewTheme(themeId, "R",reviewInfo);
		
		return redirectUrl;
	}
}
