package me.jeekhan.leyi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import me.jeekhan.leyi.common.PageCond;
import me.jeekhan.leyi.model.ArticleBrief;

public interface ArticleBriefMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ArticleBrief record);

    ArticleBrief selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(ArticleBrief record);
    
    List<ArticleBrief> selectArticlesByUser(@Param("userId") int userId,boolean reviewing,@Param("pageCond") PageCond pageCond);
    
    List<ArticleBrief> selectArticles(boolean reviewing,@Param("pageCond") PageCond pageCond);
    
    List<ArticleBrief> selectArticlesByTheme(@Param("themeId") int themeId,boolean reviewing,@Param("pageCond") PageCond pageCond);
    //取最近保存的一条记录
    ArticleBrief selectLatestRecrod(ArticleBrief record);
    //取最新待审核的20条记录
    List<ArticleBrief> selectArticles4Review();
}