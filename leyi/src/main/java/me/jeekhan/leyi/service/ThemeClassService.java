package me.jeekhan.leyi.service;

import java.util.List;

import me.jeekhan.leyi.model.ThemeClass;
/**
 *  ������������
 * @author Jee Khan
 *
 */
public interface ThemeClassService {
	/**
	 * �����������
	 * @param theme
	 * @return ����ID
	 */
	public Integer saveThemeClass(ThemeClass theme);
	/**
	 * ɾ���������
	 * @param themeId
	 * @return ������ID
	 */
	public Integer deleteThemeClass(int themeClassId);
	/**
	 * ��ȡָ�����������
	 * @param ThemeClassId
	 * @return
	 */
	public ThemeClass getThemeClass(int themeClassId);
	public ThemeClass getThemeClass(String themeName);
	/**
	 * ��ȡָ���û����е��������
	 * @return
	 */
	public List<ThemeClass> getUserThemes(int userId);
	
	/**
	 * ��ȡָ���û����ж����������
	 * @return
	 */
	public List<ThemeClass> getUserTopThemes(int userId);
	
	/**
	 * ��ȡָ�������µ����е����������
	 * @return
	 */
	public List<ThemeClass> getChildThemes(int parentId);
	/**
	 * ���ϻ�ȡ���������
	 * @param themeId
	 * @return
	 */
	public List<ThemeClass> getThemeTreeUp(int themeId);
	
	
}
