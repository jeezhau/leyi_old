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
	public List<ArticleBrief> getArticlesByUser(int userId,boolean isSelf,PageCond pageCond){
		if(pageCond == null){
			pageCond = new PageCond(0);
		}
		List<ArticleBrief> list = articleBriefMapper.selectArticlesByUser(userId, isSelf,pageCond);
		return list;
	}
	/**
	 * 查询所有文章信息
	 */
	public List<ArticleBrief> getArticles(boolean isSelf,PageCond pageCond){
		if(pageCond == null){
			pageCond = new PageCond(0);
		}
		List<ArticleBrief> list = articleBriefMapper.selectArticles(isSelf,pageCond);
		return list;
	}
	/**
	 * 保存文章信息：有则更新，无则插入
	 * @param articleBrief	文章简介
	 * @return 文章ID
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
	 * 保存文章内容：有则更新，无则插入
	 * @param articleContent	文章内容
	 * @return 文章ID
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
	 * 保存文章全部信息：有则更新，无则插入
	 * @param articleBrief	文章简介
	 * @param articleContent	文章内容  
	 * @return 文章ID 
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
	 * 逻辑删除指定文章
	 */
	@Override
	public int deleteArticle(int articleId) {
		return articleBriefMapper.updateEnabledStatus(articleId,'D');
	}	
	
	/**
	 * 获取指定主题下的所有文章简介信息
	 */
	@Override
	public List<ArticleBrief> getArticlesByTheme(int themeId,boolean isSelf,PageCond pageCond){
		return articleBriefMapper.selectArticlesByTheme(themeId, isSelf,pageCond);
	}
	/**
	 * 取最新最热门的文章20条
	 */
	public List<ArticleBrief> getHotNewArticles(){
		return articleBriefMapper.selectArticles(false, new PageCond());
	}
	/**
	 * 取最新待审核的20条记录
	 */
	public List<ArticleBrief> getArticles4Review(){
		return articleBriefMapper.selectArticles4Review();
	}
	
	/**
	 * 文章审核通过
	 */
	public int acceptArticle(int articleId,String remark) {
		return articleBriefMapper.updateEnabledStatus(articleId,'0');
	}
	/**
	 * 文章审核拒绝
	 */
	public int refuseArticle(int articleId,String remark) {
		return articleBriefMapper.updateEnabledStatus(articleId,'R');
	}
	
	
}
