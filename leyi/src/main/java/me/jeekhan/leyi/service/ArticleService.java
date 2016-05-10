package me.jeekhan.leyi.service;

import java.util.List;

import me.jeekhan.leyi.common.PageCond;
import me.jeekhan.leyi.model.ArticleBrief;
import me.jeekhan.leyi.model.ArticleContent;

/**
 * 文章相关服务
 * @author Jee Khan
 *
 */
public interface ArticleService {
	/**
	 * 获取文章简介信息
	 * @param id	文章ID
	 * @return
	 */
	public ArticleBrief getArticleBref(int id);
	/**
	 * 获取文章内容
	 * @param id	文章ID
	 * @return
	 */
	public ArticleContent getArticleContent(int id);
	
	/**
	 * 分页查询用户的所有文章信息，按更新时间，评论数量降序排列;分页显示
	 * @param userId	用户ID
	 * @param pageCond	分页条件
	 * @return
	 */
	public List<ArticleBrief> getArticleByUser(int userId,PageCond pageCond);
	
	
	
	

}
