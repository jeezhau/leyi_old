package me.jeekhan.leyi.dao;

import java.util.List;
import me.jeekhan.leyi.model.ThemeClass;
import me.jeekhan.leyi.model.ThemeClassExample;
import org.apache.ibatis.annotations.Param;

public interface ThemeClassMapper {
    int countByExample(ThemeClassExample example);

    int deleteByExample(ThemeClassExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ThemeClass record);

    int insertSelective(ThemeClass record);

    List<ThemeClass> selectByExample(ThemeClassExample example);

    ThemeClass selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ThemeClass record, @Param("example") ThemeClassExample example);

    int updateByExample(@Param("record") ThemeClass record, @Param("example") ThemeClassExample example);

    int updateByPrimaryKeySelective(ThemeClass record);

    int updateByPrimaryKey(ThemeClass record);
}