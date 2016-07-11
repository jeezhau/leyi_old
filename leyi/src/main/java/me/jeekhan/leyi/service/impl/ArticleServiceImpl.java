package me.jeekhan.leyi.service.impl;

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
 * 文章相关服务
 * 功能权限部分使用其他方式控制
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
	 * 获取指定文章信息
	 */
	@Override
	public ArticleBrief getArticleBref(int articleId) {
		return articleBriefMapper.selectByPrimaryKey(articleId);
	}
	/**
	 * 获取指定文章内容
	 */
	@Override
	public ArticleContent getArticleContent(int articleId) {
		return articleContentMapper.selectByPrimaryKey(articleId);
	}
	/**
	 * 查询指定用户下的所有文章
	 */
	@Override
	public List<ArticleBrief> getArticlesByUser(int userId,PageCond pageCond){
		if(pageCond == null){
			pageCond = new PageCond(0);
		}
		List<ArticleBrief> list = articleBriefMapper.selectArticlesByUser(userId, pageCond);
		return list;
	}
	/**
	 * 查询所有文章信息
	 */
	public List<ArticleBrief> getArticles(PageCond pageCond){
		if(pageCond == null){
			pageCond = new PageCond(0);
		}
		List<ArticleBrief> list = articleBriefMapper.selectArticles(pageCond);
		return list;
	}
	/**
	 * 保存文章信息：有则更新，无则插入
	 */
	@Override
	public int saveArticleBrief(ArticleBrief articleBrief) {
		if(articleBrief == null){
			return -1;
		}
		if(articleBrief.getId() == null){
			return articleBriefMapper.insert(articleBrief);
		}else{
			return articleBriefMapper.updateByPrimaryKey(articleBrief);
		}
	}
	/**
	 * 保存文章内容：有则更新，无则插入
	 */
	@Override
	public int saveArtileContent(ArticleContent articleContent) {
		if(articleContent == null){
			return -1;
		}
		if(articleContent.getArticleId() == null){
			return articleContentMapper.insert(articleContent);
		}else{
			return articleContentMapper.update(articleContent);
		}
	}
	/**
	 * 保存文章全部信息：有则更新，无则插入
	 */
	@Override
	public int saveArticleInfo(ArticleBrief articleBrief, ArticleContent articleContent) {
		if(articleBrief == null || articleContent == null){
			return -1;
		}
		if(articleBrief.getId() != articleContent.getArticleId()){
			return -2;
		}
		saveArtileContent(articleContent);
		return saveArticleBrief(articleBrief);
	}
	/**
	 * 删除指定文章
	 */
	@Override
	public int deleteArticle(int articleId) {
		articleBriefMapper.deleteByPrimaryKey(articleId);
		return articleContentMapper.delete(articleId);
	}	
	
	/**
	 * 获取指定主题下的所有文章简介信息
	 */
	@Override
	public List<ArticleBrief> getArticlesByTheme(int themeId,PageCond pageCond){
		return articleBriefMapper.selectArticlesByTheme(themeId, pageCond);
	}
	
	
	
}
