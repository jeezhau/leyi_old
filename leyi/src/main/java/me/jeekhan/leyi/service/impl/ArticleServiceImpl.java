package me.jeekhan.leyi.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.jeekhan.leyi.common.PageCond;
import me.jeekhan.leyi.dao.ArticleBriefMapper;
import me.jeekhan.leyi.dao.ArticleContentMapper;
import me.jeekhan.leyi.model.ArticleBrief;
import me.jeekhan.leyi.model.ArticleContent;
import me.jeekhan.leyi.service.ArticleService;

/**
 * ������ط���
 * ����Ȩ�޲���ʹ��������ʽ����
 * @author Jee Khan
 *
 */
@Service
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	private ArticleBriefMapper articleBriefMapper;
	@Autowired
	private ArticleContentMapper  articleContentMapper;
	/**
	 * ��ȡָ��������Ϣ
	 */
	@Override
	public ArticleBrief getArticleBref(int articleId) {
		return articleBriefMapper.selectByPrimaryKey(articleId);
	}
	/**
	 * ��ȡָ����������
	 */
	@Override
	public ArticleContent getArticleContent(int articleId) {
		return articleContentMapper.selectByPrimaryKey(articleId);
	}
	/**
	 * ��ѯָ���û��µ���������
	 */
	@Override
	public List<ArticleBrief> getArticlesByUser(int userId,boolean isSelf,PageCond pageCond){
		if(pageCond == null){
			pageCond = new PageCond(0);
		}
		List<ArticleBrief> list = articleBriefMapper.selectArticlesByUser(userId, isSelf,pageCond);
		return list;
	}
	/**
	 * ��ѯ����������Ϣ
	 */
	public List<ArticleBrief> getArticles(boolean isSelf,PageCond pageCond){
		if(pageCond == null){
			pageCond = new PageCond(0);
		}
		List<ArticleBrief> list = articleBriefMapper.selectArticles(isSelf,pageCond);
		return list;
	}
	/**
	 * ����������Ϣ��������£��������
	 * @param articleBrief	���¼��
	 * @return ����ID
	 */
	@Override
	public int saveArticleBrief(ArticleBrief articleBrief) {
		if(articleBrief == null){
			return -1;
		}
		articleBrief.setEnabled("1");
		articleBrief.setUpdateTime(new Date());
		if(articleBrief.getId() == null){
			articleBriefMapper.insert(articleBrief);
			return articleBriefMapper.selectLatestRecrod(articleBrief).getId();
		}else{
			articleBriefMapper.updateByPrimaryKey(articleBrief);
			return articleBrief.getId();
		}
	}
	/**
	 * �����������ݣ�������£��������
	 * @param articleContent	��������
	 * @return ����ID
	 */
	@Override
	public int saveArticleContent(ArticleContent articleContent) {
		if(articleContent == null){
			return -1;
		}
		if(articleContent.getArticleId() == null){
			return -2;
		}else{
			if(articleContentMapper.selectByPrimaryKey(articleContent.getArticleId())!=null){
				articleContentMapper.update(articleContent);
			}else{
				articleContentMapper.insert(articleContent);
			}
			return articleContent.getArticleId();
		}
	}
	/**
	 * ��������ȫ����Ϣ��������£��������
	 * @param articleBrief	���¼��
	 * @param articleContent	��������  
	 * @return ����ID 
	 */
	@Override
	public int saveArticleInfo(ArticleBrief articleBrief, ArticleContent articleContent) {
		if(articleBrief == null || articleContent == null){
			return -1;
		}
		if(articleBrief.getId() != articleContent.getArticleId()){
			return -2;
		}
		saveArticleContent(articleContent);
		saveArticleBrief(articleBrief);
		return articleBrief.getId();
	}
	/**
	 * �߼�ɾ��ָ������
	 */
	@Override
	public int deleteArticle(int articleId) {
		return articleBriefMapper.updateEnabledStatus(articleId,'D');
	}	
	
	/**
	 * ��ȡָ�������µ��������¼����Ϣ
	 */
	@Override
	public List<ArticleBrief> getArticlesByTheme(int themeId,boolean isSelf,PageCond pageCond){
		return articleBriefMapper.selectArticlesByTheme(themeId, isSelf,pageCond);
	}
	/**
	 * ȡ���������ŵ�����20��
	 */
	public List<ArticleBrief> getHotNewArticles(){
		return articleBriefMapper.selectArticles(false, new PageCond());
	}
	/**
	 * ȡ���´���˵�20����¼
	 */
	public List<ArticleBrief> getArticles4Review(){
		return articleBriefMapper.selectArticles4Review();
	}
	
	/**
	 * �������ͨ��
	 */
	public int acceptArticle(int articleId,String remark) {
		return articleBriefMapper.updateEnabledStatus(articleId,'0');
	}
	/**
	 * ������˾ܾ�
	 */
	public int refuseArticle(int articleId,String remark) {
		return articleBriefMapper.updateEnabledStatus(articleId,'R');
	}
	
	
}
