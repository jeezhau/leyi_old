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
 * ���¹���
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
	 * ��ʾ������ϸ��Ϣ
	 * ��Ȩ�ޡ�
	 * 	1��������ʾ-�����ˣ�
	 *  2�����-����Ա�����Ϊ�ǹ���Ա����ģʽΪ������ʾ��
	 * ������˵����
	 * ���������Ϣ������ʾ�������ʾ��detail-������ʾ��review-�����ʾ
	 * 	1.ȡ������Ϣ��������²������򷵻�Ӧ����ҳ��
	 * 	2.ȡ����������Ϣ��
	 * 	3.������ʾģʽ���������ˣ���
	 * @param articleId	����ID
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
	public String addArticle(@Valid ArticleBrief brief,BindingResult result,String content,
			@ModelAttribute("operator")Operator operator,Map<String,Object> map){
		if(result.hasErrors()){	//��֤����
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError e :list){
				String field = e.getCodes()[0].substring(e.getCodes()[0].lastIndexOf('.')+1);
				map.put(field, e.getDefaultMessage());
			}
			return "articleEditing";
		}
		if(content.length()>10240){
			map.put("content", "���ݣ���󳤶�Ϊ10K���ַ���");
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
		return "redirect:/"+operator.getUsername()+"/article_mgr/theme/" + themeId;
	}
	/**
	 * �������±༭
	 * @param brief		���¼����Ϣ
	 * @param content	��������
	 * @return
	 */
	@RequestMapping(value="/edit",method=RequestMethod.POST)
	public String editArticle(@Valid ArticleBrief brief,BindingResult result,String content,
			@ModelAttribute("operator")Operator operator,Map<String,String> map){
		if(result.hasErrors()){	//��֤����
			List<ObjectError> list = result.getAllErrors();
			for(ObjectError e :list){
				String filed = e.getCodes()[0].substring(e.getCodes()[0].lastIndexOf('.')+1);
				map.put(filed, e.getDefaultMessage());
			}
			return "articleEditing";
		}
		if(content.length()>10240){
			map.put("content", "���ݣ���󳤶�Ϊ10K���ַ���");
			return "articleEditing";
		}
		String redirectUrl = "redirect:/"+operator.getUsername()+"/article_mgr/theme/";
		int id = brief.getId();
		ArticleBrief old = articleService.getArticleBref(id);
		if(old == null || old.getUpdateOpr()!= operator.getUserId()){ //�޸����»������
			map.put("error", "����Ȩ��ִ�иò�����");
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
			return redirectUrl + old.getThemeId();
		}
	}
	/**
	 * ɾ��ָ������
	 * @param id	����ID
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String deleteArticle(int articleId,@ModelAttribute("operator")Operator operator){
		String redirectUrl = "redirect:/"+operator.getUsername()+"/article_mgr/theme/";
		ArticleBrief old = articleService.getArticleBref(articleId);
		if(old == null || old.getUpdateOpr()!= operator.getUserId()){ 
			return redirectUrl + "?error=����Ȩ��ִ�иò�����";
		}else{
			articleService.deleteArticle(articleId);
		}
		return redirectUrl + old.getThemeId();
	}
	
	/**
	 * ȡ�û���ָ�������µ���������
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
		List<ThemeClass> children = themeClassService.getChildThemes(currTheme.getId(),true);
		map.put("children",children);
		boolean isSelf = false;
		if(operator.getUserId() == currTheme.getUpdateOpr() ){
			isSelf = true;
		}
		List<ArticleBrief> currArticles = articleService.getArticlesByTheme(currTheme.getId(),isSelf,new PageCond());
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
	public String article(@ModelAttribute("operator")Operator operator,Map<String,Object> map){
		@SuppressWarnings("unchecked")
		List<ThemeClass> topThemes = (List<ThemeClass>) map.get("topThemes");
		if(topThemes !=null && topThemes.size()>0){
			ThemeClass currTheme = topThemes.get(0);
			map.put("currTheme", currTheme);
			List<ThemeClass> themeTreeUp = themeClassService.getThemeTreeUp(currTheme.getId());
			map.put("themeTreeUp", themeTreeUp);
			List<ThemeClass> children = themeClassService.getChildThemes(currTheme.getId(),true);
			map.put("children",children);
			boolean isSelf = false;
			if(operator.getUserId() == currTheme.getUpdateOpr() ){
				isSelf = true;
			}
			List<ArticleBrief> currArticles = articleService.getArticlesByTheme(currTheme.getId(),isSelf, new PageCond());
			map.put("currArticles", currArticles);
		}
		return "articleMgr";
	}
	/**
	 * ������ˣ�ͨ��
	 * ��Ȩ�ޡ�
	 * 	1������¼�Ĺ���Ա��ִ�иò�����
	 * ������˵����
	 *  1���ж���˵������Ƿ���ڣ�
	 *  2��ִ�����ͨ��
	 * @param articleId
	 * @param remark
	 * @param operator
	 * @return
	 */
	@RequestMapping(value="/accept",method=RequestMethod.POST)
	public String accept(Integer articleId,String remark,@ModelAttribute("operator")Operator operator){
		String redirectUrl = "redirect:/"+operator.getUsername()+"/article_mgr/review/" + articleId;
		if(operator == null || operator.getLevel() < 9){ //��Ȩ��
			return redirectUrl + "?error=����Ȩ��ִ�иò�����";
		}
		if(articleId == null){ //����Ϊ��
			return redirectUrl;
		}
		if(remark !=null && remark.length()>600){
			return redirectUrl + "?error=" + "���˵�������ɳ���600���ַ���";
		}
		ArticleBrief brief = articleService.getArticleBref(articleId);
		if(brief == null){ //�޸�����
			return redirectUrl + "?error=ϵͳ���޸�������Ϣ��";
		}
		ReviewInfo reviewInfo = new ReviewInfo();
		reviewInfo.setReviewInfo(remark);
		reviewInfo.setReviewOpr(operator.getUserId());
		articleService.reviewArticle(articleId,"0",reviewInfo);
		return "redirect:/"+operator.getUsername()+ "/review/";
	}
	/**
	 * ������ˣ��ܾ�
	 * ��Ȩ�ޡ�
	 * 	1������¼�Ĺ���Ա��ִ�иò�����
	 * ������˵����
	 *  1���ж���˵������Ƿ���ڣ�
	 *  2��ִ����˾ܾ�
	 * @param articleId
	 * @param remark
	 * @param operator
	 * @return
	 */
	@RequestMapping(value="/refuse",method=RequestMethod.POST)
	public String refuse(Integer articleId,String remark,@ModelAttribute("operator")Operator operator){
		String redirectUrl = "redirect:/"+operator.getUsername()+"/article_mgr/review/" + articleId ;
		if(operator == null || operator.getLevel() < 9){ //��Ȩ��
			return redirectUrl + "?error=����Ȩ��ִ�иò�����";
		}
		
		if(articleId == null || remark == null || remark.trim().length()<1){ //��������˵��Ϊ��
			return redirectUrl + "?error=" + ((articleId == null)? "����ID������Ϊ�գ�" : "���˵��������Ϊ�գ�");
		}
		if(remark.length()>600){
			return redirectUrl + "?error=" + "���˵�������ɳ���600���ַ���";
		}
		
		ArticleBrief brief = articleService.getArticleBref(articleId);
		if(brief == null){ //�޸�����
			return redirectUrl + "?error=ϵͳ���޸�������Ϣ��";
		}
		ReviewInfo reviewInfo = new ReviewInfo();
		reviewInfo.setReviewInfo(remark);
		reviewInfo.setReviewOpr(operator.getUserId());
		articleService.reviewArticle(articleId,"R",reviewInfo);
		return "redirect:/"+operator.getUsername()+ "/review/";
	}
}
