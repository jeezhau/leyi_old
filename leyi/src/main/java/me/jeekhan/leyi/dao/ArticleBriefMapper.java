package me.jeekhan.leyi.dao;

import java.util.List;
import me.jeekhan.leyi.model.ArticleBrief;
import me.jeekhan.leyi.model.ArticleBriefExample;
import org.apache.ibatis.annotations.Param;

public interface ArticleBriefMapper {
    int countByExample(ArticleBriefExample example);

    int deleteByExample(ArticleBriefExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ArticleBrief record);

    int insertSelective(ArticleBrief record);

    List<ArticleBrief> selectByExample(ArticleBriefExample example);

    ArticleBrief selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ArticleBrief record, @Param("example") ArticleBriefExample example);

    int updateByExample(@Param("record") ArticleBrief record, @Param("example") ArticleBriefExample example);

    int updateByPrimaryKeySelective(ArticleBrief record);

    int updateByPrimaryKey(ArticleBrief record);
}