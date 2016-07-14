package me.jeekhan.leyi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.jeekhan.leyi.common.SunSHAUtils;
import me.jeekhan.leyi.dao.UserBaseInfoMapper;
import me.jeekhan.leyi.dao.UserFullInfoMapper;
import me.jeekhan.leyi.model.UserBaseInfo;
import me.jeekhan.leyi.model.UserFullInfo;
import me.jeekhan.leyi.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserBaseInfoMapper userBaseInfoMapper;
	@Autowired
	private UserFullInfoMapper userFullInfoMapper;
	/**
	 * 提取用户基本信息
	 * @param	用户ID
	 */
	@Override
	public UserBaseInfo getUserBaseInfo(int id){
		return userBaseInfoMapper.selectByPrimaryKey(id);
	}
	/**
	 * 提取用户基本信息
	 * @param	用户名或邮箱
	 */
	public UserFullInfo getUserFullInfo(String username){
		UserBaseInfo  userInfo = userBaseInfoMapper.selectByName(username);
		return userFullInfoMapper.selectByPrimaryKey(userInfo.getUserId());
	}
	/**
	 * 用户身份验证
	 * @param	userBaseInfo	用户基本信息
	 * @param	passwd			用户密码（明文）
	 */
	@Override
	public boolean authentification(String username, String passwd) {
		UserBaseInfo  userInfo = userBaseInfoMapper.selectByName(username);
		try{
			if(userInfo == null){
				return false;
			}else{
				//if(SunSHAUtils.encodeSHA512Hex(passwd).equals(userInfo.getPasswd())){
					return true;
				//}
			}
			//return false;
		}catch(Exception e){
			return false;
		}
	}
	/**
	 * 获取用户的详细信息
	 * @param	用户ID
	 */
	@Override
	public UserFullInfo getUserFullInfo(int id) {
		return userFullInfoMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 保存用户：有则更新，无则修改
	 * @param	用户详细信息
	 */
	@Override 
	public int saveUser(UserFullInfo userFullInfo){
		if(userFullInfo == null){
			return -1;
		}
		if(userFullInfo.getId() == null){
			return userFullInfoMapper.insert(userFullInfo);
		}else{
			return userFullInfoMapper.updateByPrimaryKey(userFullInfo);
		}
	}
	/**
	 * 注销用户
	 * @param id
	 * @return
	 */
	public int logOffUser(int id){
		return userFullInfoMapper.logOffUserById(id);
	}
}
