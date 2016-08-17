package me.jeekhan.leyi.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.jeekhan.leyi.dao.ReviewInfoMapper;
import me.jeekhan.leyi.dao.ThemeClassMapper;
import me.jeekhan.leyi.model.ReviewInfo;
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
	@Autowired
	private ReviewInfoMapper reviewInfoMapper;
	/**
	 * ��������
	 *  0�������������Ϊ6����
	 *  1������ID�����Ϣ����ID��������
	 *  2��ͬ��ͬ������ʹ�ø��£�
	 */
	@Override
	public Integer saveThemeClass(ThemeClass themeClass) {
		if(themeClass == null ){	//����Ϊ��
			return -1;
		}
		themeClass.setEnabled("1");
		themeClass.setUpdateTime(new Date());
		ThemeClass old = themeClassMapper.selectByName(themeClass.getName(),themeClass.getParentId());
		if(themeClass.getId() == null){
			int  topcount = 0;
			if(themeClass.getParentId() == null){
				topcount = themeClassMapper.countUserTopTheme(themeClass.getUpdateOpr());
				if(topcount >= 6){  //���������������6��
					return -2;  
				}
			}
			
			if(old != null){
				if("D".equals(old.getEnabled())){
					themeClass.setId(old.getId());
					themeClassMapper.updateByPrimaryKey(themeClass);
					return old.getId();
				}else{	//����ͬ��ͬ���Ļ����
					return -3;	
				}
			}else{
				themeClassMapper.insert(themeClass);
				ThemeClass lastest = themeClassMapper.selectByName(themeClass.getName(),themeClass.getParentId());
				return lastest.getId();
			}
			
		}else{
			ThemeClass tmp = themeClassMapper.selectByPrimaryKey(themeClass.getId());
			if(tmp == null || tmp.getParentId() != themeClass.getParentId()){ //����Ϊ�ջ��ϲ����ⲻһ�£�����������
				return -1;
			}else{
				if( old == null || old != null && themeClass.getId() == old.getId()  ){	//ͬ��ͬID����ͬ��
					themeClassMapper.updateByPrimaryKey(themeClass);
					return old.getId();
				}else{ //����ͬ��ͬ���Ļ����
					if("D".equals(old.getEnabled())){
						themeClassMapper.updateByPrimaryKey(themeClass);
						return old.getId();
					}else{	//����ͬ��ͬ���Ļ����
						return -3;	
					}
				}
			}
		}
		
	}
	/**
	 * �߼�ɾ������
	 *  ��������״̬Ϊ'D'
	 */
	@Override
	public Integer deleteThemeClass(int themeClassId) {
		ThemeClass t = themeClassMapper.selectByPrimaryKey(themeClassId);
		themeClassMapper.updateEnabledStatus(themeClassId,"D");
		return t.getParentId();
	}

	@Override
	public ThemeClass getThemeClass(int themeId) {
		return themeClassMapper.selectByPrimaryKey(themeId);
	}
	@Override
	public ThemeClass getThemeClass(String themeName,int parentId) {
		return themeClassMapper.selectByName(themeName,parentId);
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
	
	/**
	 * �������
	 * @param themeId   ����ID
	 * @param result	��˽��:0-ͨ��,R-�ܾ�
	 * @param reviewInfo	���˵��
	 */
	@Override
	public int reviewTheme(int themeId,String result,ReviewInfo reviewInfo){
		String themeInfo = themeClassMapper.selectByPrimaryKey(themeId).toString();
		reviewInfo.setObjName("tb_theme_class");
		reviewInfo.setKeyId(themeId);
		reviewInfo.setOriginalInfo(themeInfo);
		reviewInfo.setResult(result);
		reviewInfo.setReviewTime(new Date());
		reviewInfoMapper.insert(reviewInfo);
		
		return themeClassMapper.updateEnabledStatus(themeId, result);
	}
}
