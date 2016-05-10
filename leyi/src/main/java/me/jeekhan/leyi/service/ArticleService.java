package me.jeekhan.leyi.service;

import java.util.List;

import me.jeekhan.leyi.common.PageCond;
import me.jeekhan.leyi.model.ArticleBrief;
import me.jeekhan.leyi.model.ArticleContent;

/**
 * ������ط���
 * @author Jee Khan
 *
 */
public interface ArticleService {
	/**
	 * ��ȡ���¼����Ϣ
	 * @param id	����ID
	 * @return
	 */
	public ArticleBrief getArticleBref(int id);
	/**
	 * ��ȡ��������
	 * @param id	����ID
	 * @return
	 */
	public ArticleContent getArticleContent(int id);
	
	/**
	 * ��ҳ��ѯ�û�������������Ϣ��������ʱ�䣬����������������;��ҳ��ʾ
	 * @param userId	�û�ID
	 * @param pageCond	��ҳ����
	 * @return
	 */
	public List<ArticleBrief> getArticleByUser(int userId,PageCond pageCond);
	
	
	
	

}
