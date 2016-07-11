package me.jeekhan.leyi.dao;

import me.jeekhan.leyi.model.ArticleContent;

public interface ArticleContentMapper {

    int delete(Integer articleId);

    int insert(ArticleContent record);

    ArticleContent selectByPrimaryKey(Integer articleId);

    int update(ArticleContent record);

}