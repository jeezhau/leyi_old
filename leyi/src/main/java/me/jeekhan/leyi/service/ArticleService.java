package me.jeekhan.leyi.service;

import java.util.List;

import me.jeekhan.leyi.common.PageCond;
import me.jeekhan.leyi.model.ArticleBrief;
import me.jeekhan.leyi.model.ArticleContent;
import me.jeekhan.leyi.model.ReviewInfo;

/**
 * ������ط���
 * @author Jee Khan
 *
 */
public interface ArticleService {
	
	/**
	 * �������¼����Ϣ
	 * @param articleBrief	���¼��
	 * @return
	 */
	public int saveArticleBrief(ArticleBrief articleBrief);
	/**
	 * ������������
	 * @param articleContent	��������
	 * @return
	 */
	public int saveArticleContent(ArticleContent articleContent);
	/**
	 * ��������ȫ����Ϣ
	 * @param articleBrief		���¼��
	 * @param articleContent	��������
	 * @return
	 */
	public int saveArticleInfo(ArticleBrief articleBrief,ArticleContent articleContent);
	/**
	 * �߼�ɾ��ָ��������
	 * @param articleId
	 * @return
	 */
	public int deleteArticle(int articleId);
	
	/**
	 * ��ȡָ�����µļ����Ϣ
	 * @param id	����ID
	 * @return
	 */
	public ArticleBrief getArticleBref(int id);
	/**
	 * ��ȡָ�����µ�����
	 * @param id	����ID
	 * @return
	 */
	public ArticleContent getArticleContent(int id);
	
	/**
	 * ��ҳ��ѯָ���û�������������Ϣ��������ʱ�䣬����������������;
	 * ��ҳ��ʾ
	 * @param userId	�û�ID
	 * @param isSelf �Ƿ��������еļ�¼
	 * @param pageCond	��ҳ����
	 * @return
	 */
	public List<ArticleBrief> getArticlesByUser(int userId,boolean isSelf,PageCond pageCond);
	
	/**
	 * ��ҳ��ѯ��ʾ�����û�������������Ϣ��������ʱ�䣬����������������
	 * @param isSelf �Ƿ��������еļ�¼
	 * @param pageCond	��ҳ����
	 * @return
	 */
	public List<ArticleBrief> getArticles(boolean isSelf,PageCond pageCond);
	
	/**
	 * ��ҳ��ѯ��ʾָ�������µ�����������Ϣ��������ʱ�䣬����������������
	 * @param themeId
	 * @param isSelf �Ƿ��������еļ�¼
	 * @return
	 */
	public List<ArticleBrief> getArticlesByTheme(int themeId,boolean isSelf,PageCond pageCond);
	
	/**
	 * ȡ���������ŵ�����20��
	 * @return
	 */
    public List<ArticleBrief> getHotNewArticles();
    
	/**
	 * ȡ���´���˵�����20��
	 * @return
	 */
    public List<ArticleBrief> getArticles4Review();

	/**
	 * �������
	 * @param articleId	  ����ID
	 * @param result	  ��˽����0-ͨ����R-�ܾ�
	 * @param reviewInfo �����Ϣ
	 */
	public int reviewArticle(int articleId,String result,ReviewInfo reviewInfo);
	
	/**
	 * ȡ�������������
	 * @return
	 */
	public int get4ReviewArticlesCnt();
	
}
