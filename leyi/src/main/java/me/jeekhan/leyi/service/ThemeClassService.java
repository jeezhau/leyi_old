package me.jeekhan.leyi.service;

import java.util.List;

import me.jeekhan.leyi.model.ReviewInfo;
import me.jeekhan.leyi.model.ThemeClass;
/**
 *  主题分类服务类
 * @author Jee Khan
 *
 */
public interface ThemeClassService {
	/**
	 * 保存主题分类
	 * @param theme
	 * @return 主题ID
	 */
	public Integer saveThemeClass(ThemeClass theme);
	/**
	 * 删除主题分类
	 * @param themeId
	 * @return 父主题ID
	 */
	public Integer deleteThemeClass(int themeClassId);
	/**
	 * 获取指定的主题分类
	 * @param ThemeClassId
	 * @return
	 */
	public ThemeClass getThemeClass(int themeClassId);
	public ThemeClass getThemeClass(String themeName,int parentId,int userId);
	/**
	 * 获取指定用户所有的主题分类
	 * @return
	 */
	public List<ThemeClass> getUserThemes(int userId,boolean isSlef);
	
	/**
	 * 获取指定用户所有顶层主题分类
	 * @return
	 */
	public List<ThemeClass> getUserTopThemes(int userId,boolean isSlef);
	
	/**
	 * 获取指定主题下的所有的子主题分类
	 * @return
	 */
	public List<ThemeClass> getChildThemes(int parentId,boolean isSlef);
	/**
	 * 向上获取主题分类属
	 * @param themeId
	 * @return
	 */
	public List<ThemeClass> getThemeTreeUp(int themeId);
	
	
	/**
	 * 获取待审核的10条主题
	 * @return
	 */
	public List<ThemeClass> getThemes4Review();
	
	/**
	 * 主题审核
	 * @param themeId   主题ID
	 * @param result	审核结果:0-通过,R-拒绝
	 * @param reviewInfo	审核说明
	 */
	public int reviewTheme(int themeId,String result,ReviewInfo reviewInfo);
	
	/**
	 * 取待审核主题数量
	 * @return
	 */
	public int get4ReviewThemesCnt();
	
}
