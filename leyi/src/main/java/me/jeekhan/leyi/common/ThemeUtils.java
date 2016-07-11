package me.jeekhan.leyi.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.jeekhan.leyi.model.ThemeClass;

public class ThemeUtils {
	/**
	 * 判断指定主题是否有下属主题
	 * @param themeId	主题ID
	 * @param list	所有主题信息
	 * @return
	 */
	public static boolean hasChildren(int themeId,List<ThemeClass> list){
		if(list == null){
			return false;
		}
		for(ThemeClass theme : list){
			if(theme.getParentId()!= null && (theme.getParentId().intValue() == themeId)){
				return true;
			}
		}
		return false;
	}
	
	private static ThemeClass getThemeClassById(int themeId,List<ThemeClass> list){
		if(list == null){
			return null;
		}
		for(ThemeClass theme : list){
			if(theme.getId().intValue() == themeId){
				return theme;
			}
		}
		return null;
	}
}
	
	
	
	

