package me.jeekhan.leyi.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.jeekhan.leyi.dao.ThemeClassMapper;
import me.jeekhan.leyi.model.ThemeClass;
import me.jeekhan.leyi.service.ThemeClassService;

/**
 * ����������
 * @author Jee Khan
 *
 */
@Service
public class ThemeClassServiceImpl implements ThemeClassService {
	@Autowired
	private ThemeClassMapper themeClassMapper;
	@Override
	public Integer saveThemeClass(ThemeClass themeClass) {
		if(themeClass == null ){
			return -1;
		}
		themeClass.setEnabled("0");
		themeClass.setUpdateTime(new Date());
		ThemeClass t = themeClassMapper.selectByName(themeClass.getName());
		if(t==null && themeClass.getId() == null){//����
			int count = themeClassMapper.countUserTopTheme(themeClass.getUpdateOpr());//userid
			if(count >= 6 && themeClass.getParentId() == null){
				return -3;
			}
			int c = themeClassMapper.insert(themeClass);
			t = themeClassMapper.selectByName(themeClass.getName());
			if(c>0){
				return t.getId();
			}else{
				return -2;
			}
		}
		else {
			if(t == null && themeClass.getId() != null){//�޸�������
				t = themeClassMapper.selectByPrimaryKey(themeClass.getId());
			}
			themeClass.setId(t.getId());
			themeClass.setParentId(t.getParentId());
			int c = themeClassMapper.updateByPrimaryKey(themeClass);
			if(c>0){
				return themeClass.getId();
			}else{
				return -2;
			}
		}
	}

	@Override
	public Integer deleteThemeClass(int themeClassId) {
		ThemeClass t = themeClassMapper.selectByPrimaryKey(themeClassId);
		themeClassMapper.deleteByPrimaryKey(themeClassId);
		return t.getParentId();
	}

	@Override
	public ThemeClass getThemeClass(int themeId) {
		return themeClassMapper.selectByPrimaryKey(themeId);
	}
	@Override
	public ThemeClass getThemeClass(String themeName) {
		return themeClassMapper.selectByName(themeName);
	}
	
	@Override
	public List<ThemeClass> getUserThemes(int userId,boolean isSlef) {
		return themeClassMapper.selectUserThemes(userId,isSlef);
	}

	@Override
	public List<ThemeClass> getUserTopThemes(int userId,boolean isSlef) {
		return themeClassMapper.selectUserTopThemes(userId,isSlef);
	}
	
	@Override
	public List<ThemeClass> getChildThemes(int parentId,boolean isSlef) {
		return themeClassMapper.selectChildThemes(parentId,isSlef);
	}
	@Override
	public List<ThemeClass> getThemeTreeUp(int themeId){
		return themeClassMapper.selectThemeTreeUp(themeId);
	}
	
	/**
	 * ��ȡ����˵�10������
	 * @return
	 */
	@Override
	public List<ThemeClass> getThemes4Review(){
		return themeClassMapper.selectThemes4Review();
	}
}
