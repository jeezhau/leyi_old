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
	 * 保存文章简介信息
	 * @param articleBrief	文章简介
	 * @return
	 */
	public int saveArticleBrief(ArticleBrief articleBrief);
	/**
	 * 保存文章内容
	 * @param articleContent	文章内容
	 * @return
	 */
	public int saveArtileContent(ArticleContent articleContent);
	/**
	 * 保存文章全部信息
	 * @param articleBrief		文章简介
	 * @param articleContent	文章内容
	 * @return
	 */
	public int saveArticleInfo(ArticleBrief articleBrief,ArticleContent articleContent);
	/**
	 * 逻辑删除指定的文章
	 * @param articleId
	 * @return
	 */
	public int deleteArticle(int articleId);
	
	/**
	 * 获取指定文章的简介信息
	 * @param id	文章ID
	 * @return
	 */
	public ArticleBrief getArticleBref(int id);
	/**
	 * 获取指定文章的内容
	 * @param id	文章ID
	 * @return
	 */
	public ArticleContent getArticleContent(int id);
	
	/**
	 * 分页查询指定用户的所有文章信息，按更新时间，评论数量降序排列;
	 * 分页显示
	 * @param userId	用户ID
	 * @param pageCond	分页条件
	 * @return
	 */
	public List<ArticleBrief> getArticlesByUser(int userId,PageCond pageCond);
	
	/**
	 * 分页查询显示所有用户的所有文章信息，按更新时间，评论数量降序排列
	 * @param pageCond	分页条件
	 * @return
	 */
	public List<ArticleBrief> getArticles(PageCond pageCond);
	
	/**
	 * 分页查询显示指定主题下的所有文章信息，按更新时间，评论数量降序排列
	 * @param themeId
	 * @return
	 */
	public List<ArticleBrief> getArticlesByTheme(int themeId,PageCond pageCond);
	

}
