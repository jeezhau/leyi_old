package me.jeekhan.leyi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	 * ȡָ��������Ϣ�������µ�������ʾ����ҳ
	 * ��Ȩ�ޡ�
	 * 		������
	 * ������˵����
	 * 		1.ȡ������Ϣ��
	 * 		2.ȡ���ߵĶ���������Ϣ��
	 * 		3.ȡ��ǰ���⡢���ϵ���������ֱ���������⣻
	 * 		4.ȡ��ǰ�����µ����£�
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
			if(operator.getUserId() == currTheme.getUpdateOpr() ){ //�����Լ�
				isSelf = true;
			}else {
				if(operator.getUserId()< 1 && !"0".equals(userInfo.getEnabled()) ){ //���ʷ���ʽ�û�
					return "redirect:/";
				}
				if(!"0".equals(currTheme.getEnabled())){ // ����ʽ����
					return "redirect:/" + username;
				}
			}
			map.put("currTheme", currTheme);
			map.put("userInfo", userInfo);
			
			int id = userInfo.getId();
			List<ThemeClass> topThemes = themeClassService.getUserTopThemes(id,isSelf);
			map.put("topThemes",topThemes);
			
			List<ThemeClass> themeTreeUp = themeClassService.getThemeTreeUp(themeId);
			map.put("themeTreeUp", themeTreeUp);
			
			List<ThemeClass> children = themeClassService.getChildThemes(themeId,isSelf);
			map.put("children",children);
			
			List<ArticleBrief> articleBriefs = articleService.getArticlesByTheme(themeId,isSelf, new PageCond());
			map.put("articleBriefs",articleBriefs);
			return "myIndex";
		}else{
			return "redirect:/";
		}
	}
	/**
	 * ȡ���ߵ���ҳ��Ϣ
	 * ��Ȩ�ޡ�
	 * 		������
	 * ������˵����
	 * 		1.ȡ������Ϣ��
	 * 		2.ȡ���ߵĶ������⣻
	 * 		3.ȡ���ߵ������������������£�
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
			if(operator.getUserId() == id ){ //�����Լ�
				isSelf = true;
			}else{
				if(operator.getUserId()<1 && !"0".equals(userInfo.getEnabled()) ){ //���ʷ���ʽ�û�
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
	 * ��ʾ������ϸ��Ϣ
	 * ��Ȩ�ޡ�
	 * 	1��������ʾ-�����ˣ�
	 * ������˵����
	 * 	1.ȡ������Ϣ��������²������򷵻�Ӧ����ҳ��
	 * 	2.ȡ����������Ϣ��
	 * @param articleId	����ID
	 * @param map
	 * @return
	 */
	@RequestMapping(value="/{username}/article/{articleId}",method=RequestMethod.GET)
	public String showArticle(@PathVariable("username")String username,@PathVariable("articleId")Integer articleId,Operator operator,Map<String,Object> map){
		UserFullInfo userInfo = userService.getUserFullInfo(username);
		if(userInfo == null){	//�޸��û�
			return "redirect:/";
		}
		ArticleBrief brief = articleService.getArticleBref(articleId);
		if(brief == null){	//�޸�����
			return "redirect:/" + username;
		}
		ArticleContent content = articleService.getArticleContent(articleId);
		if(operator.getUserId() == brief.getUpdateOpr()){ 	//�����Լ�
			if("D".equals(brief.getEnabled())){
				return "redirect:/" + username;
			}
		}else{	//������
			if(operator.getUserId()<1 && !"0".equals(userInfo.getEnabled()) ){ //���ʷ���ʽ�û�
				return "redirect:/";
			}
			if(!"0".equals(brief.getEnabled())){	//����ʽ����
				return "redirect:/" + username;
			}
		}
		map.put("brief", brief);
		map.put("userInfo", userInfo);	
		map.put("content", content);
	
		return "articleShow";
	}
}
