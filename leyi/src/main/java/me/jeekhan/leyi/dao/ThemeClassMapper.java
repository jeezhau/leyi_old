package me.jeekhan.leyi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import me.jeekhan.leyi.model.ThemeClass;

public interface ThemeClassMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ThemeClass record);

    ThemeClass selectByPrimaryKey(Integer id);
    
    ThemeClass selectByName(String name);

    int updateByPrimaryKey(ThemeClass record);
    
    List<ThemeClass> selectUserThemes(@Param("userId")int userId,@Param("isSelf")boolean isSelf);
    
    List<ThemeClass> selectChildThemes(@Param("themeId")int themeId,@Param("isSelf")boolean isSelf);
    
    List<ThemeClass> selectUserTopThemes(@Param("userId")int userId,@Param("isSelf")boolean isSelf);
    
    List<ThemeClass> selectThemeTreeUp(Integer themeId);
    
    int countUserTopTheme(int userId); 
    
    List<ThemeClass> selectThemes4Review();
}