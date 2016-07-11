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
    
    List<ArticleBrief> selectArticlesByUser(@Param("userId") int userId,@Param("pageCond") PageCond pageCond);
    
    List<ArticleBrief> selectArticles(@Param("pageCond") PageCond pageCond);
    
    List<ArticleBrief> selectArticlesByTheme(@Param("themeId") int themeId,@Param("pageCond") PageCond pageCond);
    

}