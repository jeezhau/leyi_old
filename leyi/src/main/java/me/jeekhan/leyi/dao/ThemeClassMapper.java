package me.jeekhan.leyi.dao;

import java.util.List;

import me.jeekhan.leyi.model.ThemeClass;

public interface ThemeClassMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ThemeClass record);

    ThemeClass selectByPrimaryKey(Integer id);
    
    ThemeClass selectByName(String name);

    int updateByPrimaryKey(ThemeClass record);
    
    List<ThemeClass> selectUserThemes(int userId);
    
    List<ThemeClass> selectChildThemes(int userId);
    
    List<ThemeClass> selectUserTopThemes(int userId);
    
    List<ThemeClass> selectThemeTreeUp(Integer themeId);
    
    int countUserTopTheme(int userId); 
}