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
	 * ��ʾ����
	 * @param articleId	����ID
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
	 * �����±༭
	 * ��Ȩ�ޡ�
	 * 		1.��¼��
	 * 		2.���������û��ɱ༭,���򷵻���ҳ��
	 * ������˵����
	 * 		�����ָ��������Id�����ȡ���¼������ݣ�����ֱ�ӷ���
	 * �����������
	 * @param articleId	����ID
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
	 * ������������
	 * ��Ȩ�ޡ�
	 * 		1.��¼
	 * ����������� 
	 * @param brief		���¼����Ϣ
	 * @param content	��������
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
	 * �������±༭
	 * @param brief		���¼����Ϣ
	 * @param content	��������
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
	 * ɾ��ָ������
	 * @param id	����ID
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
	 * ȡ�û���Ĭ�������µ���������
	 * 	�����û��ĵ�һ����������Ϊ��ǰ���⣻
	 * 	�����������ϲ������
	 * 	ȡ��ǰ���������ֱ����������
	 *  ȡ��ǰ�����µ��������¼��
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
	 * ȡ�û���Ĭ�������µ���������
	 * 	�����û��ĵ�һ���������Ϊ��ǰ���⣻
	 * 	�����������ϲ������
	 * 	ȡ��ǰ���������ֱ����������
	 *  ȡ��ǰ�����µ��������¼��
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
