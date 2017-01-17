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
	 *  0、一级主题分类最多为3个；
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
		//检查是否有同级同名的分类
		ThemeClass old = themeClassMapper.selectByNameAndOpr(themeClass.getName(),themeClass.getClassLvl(),themeClass.getUpdateOpr());
		if(themeClass.getId() == null){//
			int  topcount = 0;
			if(themeClass.getClassLvl() == 1){ //顶层主题
				themeClass.setLogicId("");
				topcount = themeClassMapper.countUserTopTheme(themeClass.getUpdateOpr());
				if(topcount >= 3){  //顶层主题个数大于6个
					return -2;  
				}
			}
			
			if(old != null){ //修改
				themeClass.setId(old.getId());
				themeClassMapper.update(themeClass);
				return old.getId();
			}else{//新增
				themeClassMapper.insert(themeClass);
				ThemeClass lastest = themeClassMapper.selectByNameAndOpr(themeClass.getName(),themeClass.getClassLvl(),themeClass.getUpdateOpr());
				String logicId = "";
				if(lastest.getLogicId() == null){
					logicId = lastest.getId().toString();
				}else{
					logicId = lastest.getLogicId() + "_" + lastest.getId().toString();
				}
				lastest.setLogicId(logicId);
				themeClassMapper.update(lastest);
				return lastest.getId();
			}
			
		}else{
			ThemeClass tmp = themeClassMapper.selectByPrimaryKey(themeClass.getId());
			if(tmp == null){ //主题不一致，数据有问题
				return -1;
			}else{//修改
				themeClassMapper.update(themeClass);
				return tmp.getId();
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
		return t.getId();
	}
	/**
	 * 根据主题ID获取主题信息
	 */
	@Override
	public ThemeClass getThemeClass(int themeId) {
		return themeClassMapper.selectByPrimaryKey(themeId);
	}
	/**
	 * 根据主题名称、级别、用户获取主题信息
	 */
	@Override
	public ThemeClass getThemeClass(String themeName,int classLvl,int userId) {
		return themeClassMapper.selectByNameAndOpr(themeName,classLvl,userId);
	}
	/**
	 * 获取指定用户的主题信息，判断是否为本人获取
	 */
	@Override
	public List<ThemeClass> getUserThemes(int userId,boolean isSlef) {
		return themeClassMapper.selectUserThemes(userId,isSlef);
	}
	/**
	 * 获取用户的一级主题，判断是否为本人获取
	 */
	@Override
	public List<ThemeClass> getUserTopThemes(int userId,boolean isSlef) {
		return themeClassMapper.selectUserTopThemes(userId,isSlef);
	}
	/**
	 * 获取指定主题的所有下级主题，判断是否为本人获取
	 */
	@Override
	public List<ThemeClass> getChildThemes(String logicId,boolean isSlef) {
		return themeClassMapper.selectChildThemes(logicId,isSlef);
	}
	/**
	 * 获取主题的向上主题层次树
	 */
	@Override
	public List<ThemeClass> getThemeTreeUp(String logicId){
		return themeClassMapper.selectThemeTreeUp(logicId);
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
	
	/**
	 * 取待审核主题数量
	 * @return
	 */
	@Override
	public int get4ReviewThemesCnt() {
		return themeClassMapper.countThemes4Review();
	}
}
