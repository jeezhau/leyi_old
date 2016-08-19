package me.jeekhan.leyi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import me.jeekhan.leyi.common.PageCond;
import me.jeekhan.leyi.model.ArticleBrief;

public interface ArticleBriefMapper {
	//变更文章状态
    int updateEnabledStatus(@Param("id")Integer id,@Param("enabled")String enabled);
    //新插入一条文章记录
    int insert(ArticleBrief record);
    
    ArticleBrief selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(ArticleBrief record);
    
    List<ArticleBrief> selectArticlesByUser(@Param("userId") int userId,@Param("isSelf")boolean isSelf,@Param("pageCond") PageCond pageCond);
    
    List<ArticleBrief> selectArticles(@Param("isSelf")boolean isSelf,@Param("pageCond") PageCond pageCond);
    
    List<ArticleBrief> selectArticlesByTheme(@Param("themeId") int themeId,@Param("isSelf")boolean isSelf,@Param("pageCond") PageCond pageCond);
    //取最近保存的一条记录
    ArticleBrief selectLatestRecrod(ArticleBrief record);
    //取最新待审核的20条记录
    List<ArticleBrief> selectArticles4Review();
    //统计待审核文章数量
    int countArticles4Review();
}