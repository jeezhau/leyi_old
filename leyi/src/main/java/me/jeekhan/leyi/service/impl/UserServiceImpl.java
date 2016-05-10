package me.jeekhan.leyi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	@Override
	public UserBaseInfo getUserBaseInfo(int id){
		return userBaseInfoMapper.selectByPrimaryKey(id);
	}
	/**
	 * 用户身份验证
	 * @param	userBaseInfo	用户基本信息
	 * @param	passwd			用户密码（明文）
	 */
	@Override
	public boolean authentification(UserBaseInfo userBaseInfo, String passwd) {
		
		return false;
	}
	@Override
	public UserFullInfo getUserFullInfo(int id) {
		return userFullInfoMapper.selectByPrimaryKey(id);
	}
	
	@Override 
	public int addUser(UserFullInfo userFullInfo){
		return userFullInfoMapper.insert(userFullInfo);
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
