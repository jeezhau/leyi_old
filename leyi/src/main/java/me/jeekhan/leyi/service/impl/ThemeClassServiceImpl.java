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
 * 主题分类服务
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
	 * 保存主题
	 *  0、顶层主题最多为6个；
	 *  1、存在ID则更信息；无ID则新增；
	 *  2、同名同层主题使用更新；
	 */
	@Override
	public Integer saveThemeClass(ThemeClass themeClass) {
		if(themeClass == null ){	//数据为空
			return -1;
		}
		themeClass.setEnabled("1");
		themeClass.setUpdateTime(new Date());
		ThemeClass old = themeClassMapper.selectByName(themeClass.getName(),themeClass.getParentId());
		if(themeClass.getId() == null){
			int  topcount = 0;
			if(themeClass.getParentId() == null){
				topcount = themeClassMapper.countUserTopTheme(themeClass.getUpdateOpr());
				if(topcount >= 6){  //顶层主题个数大于6个
					return -2;  
				}
			}
			
			if(old != null){
				if("D".equals(old.getEnabled())){
					themeClass.setId(old.getId());
					themeClassMapper.updateByPrimaryKey(themeClass);
					return old.getId();
				}else{	//存在同层同名的活动主题
					return -3;	
				}
			}else{
				themeClassMapper.insert(themeClass);
				ThemeClass lastest = themeClassMapper.selectByName(themeClass.getName(),themeClass.getParentId());
				return lastest.getId();
			}
			
		}else{
			ThemeClass tmp = themeClassMapper.selectByPrimaryKey(themeClass.getId());
			if(tmp == null || tmp.getParentId() != themeClass.getParentId()){ //主题为空或上层主题不一致，数据有问题
				return -1;
			}else{
				if( old == null || old != null && themeClass.getId() == old.getId()  ){	//同名同ID或无同名
					themeClassMapper.updateByPrimaryKey(themeClass);
					return old.getId();
				}else{ //存在同层同名的活动主题
					if("D".equals(old.getEnabled())){
						themeClassMapper.updateByPrimaryKey(themeClass);
						return old.getId();
					}else{	//存在同层同名的活动主题
						return -3;	
					}
				}
			}
		}
		
	}
	/**
	 * 逻辑删除主题
	 *  设置主题状态为'D'
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
	 * 获取待审核的10条主题
	 * @return
	 */
	@Override
	public List<ThemeClass> getThemes4Review(){
		return themeClassMapper.selectThemes4Review();
	}
	
	/**
	 * 主题审核
	 * @param themeId   主题ID
	 * @param result	审核结果:0-通过,R-拒绝
	 * @param reviewInfo	审核说明
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
