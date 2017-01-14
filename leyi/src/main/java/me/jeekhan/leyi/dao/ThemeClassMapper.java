package me.jeekhan.leyi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import me.jeekhan.leyi.model.ThemeClass;

public interface ThemeClassMapper {

    int updateEnabledStatus(@Param("id")Integer id,@Param("status") String status);

    int insert(ThemeClass record);

    ThemeClass selectByPrimaryKey(Integer id);
    
    ThemeClass selectByNameAndOpr(@Param("name")String name,@Param("classLvl")int classLvl ,@Param("oprId")int oprId);

    int update(ThemeClass record);
    
    List<ThemeClass> selectUserThemes(@Param("userId")int userId,@Param("isSelf")boolean isSelf);
    
    List<ThemeClass> selectChildThemes(@Param("logicId")String logicId,@Param("isSelf")boolean isSelf);
    
    List<ThemeClass> selectUserTopThemes(@Param("userId")int userId,@Param("isSelf")boolean isSelf);
    
    List<ThemeClass> selectThemeTreeUp(@Param("logicId")String logicId);
    
    int countUserTopTheme(@Param("userId")int userId); 
    
    List<ThemeClass> selectThemes4Review();
    
    int countThemes4Review();
}