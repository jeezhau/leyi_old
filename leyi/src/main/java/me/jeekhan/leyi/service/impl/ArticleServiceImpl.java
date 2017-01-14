package me.jeekhan.leyi.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.jeekhan.leyi.common.PageCond;
import me.jeekhan.leyi.dao.ArticleBriefMapper;
import me.jeekhan.leyi.dao.ArticleContentMapper;
import me.jeekhan.leyi.dao.ReviewInfoMapper;
import me.jeekhan.leyi.model.ArticleBrief;
import me.jeekhan.leyi.model.ArticleContent;
import me.jeekhan.leyi.model.ReviewInfo;
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
	@Autowired
	private ReviewInfoMapper reviewInfoMapper;
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
		return articleBriefMapper.updateEnabledStatus(articleId,"D");
	}	
	
	/**
	 * 获取指定主题下的所有文章简介信息
	 */
	@Override
	public List<ArticleBrief> getArticlesByTheme(String logicId,boolean isSelf,PageCond pageCond){
		if(pageCond.getBegin() < 1){
			pageCond.setBegin(1);
		}
		pageCond.setBegin(pageCond.getBegin()-1);
		int cnt = articleBriefMapper.countArticlesByTheme(logicId, isSelf, pageCond);
		pageCond.setCount(cnt);
		if(cnt<1){
			return null;
		}
		List<ArticleBrief> list = articleBriefMapper.selectArticlesByTheme(logicId, isSelf,pageCond);
		pageCond.setBegin(pageCond.getBegin()+1);
		return list;
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
	 * 文章审核
	 * @param articleId	  文章ID
	 * @param result	  审核结果：0-通过，R-拒绝
	 * @param reviewInfo 审核信息
	 */
	public int reviewArticle(int articleId,String result,ReviewInfo reviewInfo) {
		String briefInfo = articleBriefMapper.selectByPrimaryKey(articleId).toString();
		reviewInfo.setObjName("tb_article_brief");
		reviewInfo.setKeyId(articleId);
		reviewInfo.setOriginalInfo(briefInfo);
		reviewInfo.setResult(result);
		reviewInfo.setReviewTime(new Date());
		reviewInfoMapper.insert(reviewInfo);
		
		String contentInfo = articleContentMapper.selectByPrimaryKey(articleId).toString();
		reviewInfo.setObjName("tb_article_content");
//		reviewInfo.setKeyId(articleId);
		reviewInfo.setOriginalInfo(contentInfo);
//		reviewInfo.setResult(new char[]{result}.toString());
//		reviewInfo.setReviewTime(new Date());
		reviewInfoMapper.insert(reviewInfo);
		
		return articleBriefMapper.updateEnabledStatus(articleId,result);
		
	}
	/**
	 * 取待审核文章数量
	 * @return
	 */
	@Override
	public int get4ReviewArticlesCnt() {
		return articleBriefMapper.countArticles4Review();
	}
	
}
