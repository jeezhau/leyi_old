package me.jeekhan.leyi.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

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
		return userFullInfoMapper.selectByName(username);
	}
	/**
	 * 用户身份验证
	 * @param	userBaseInfo	用户基本信息
	 * @param	passwd			用户密码（明文）
	 */
	@Override
	public boolean authentification(String username, String passwd) {
		UserFullInfo  userInfo = userFullInfoMapper.selectByName(username);
		try{
			if(userInfo == null){
				return false;
			}else{
				if(SunSHAUtils.encodeSHA512Hex(passwd).equals(userInfo.getPasswd())){
					return true;
				}
			}
			return false;
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
	 * @return  用户ID,0-缺少信息，-1-用户名已被使用,-2-邮箱已被使用
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@Override 
	public int saveUser(UserFullInfo userFullInfo) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		if(userFullInfo == null){
			return 0;
		}
		userFullInfo.setEnabled("1");
		userFullInfo.setRegistTime(new Date());
		userFullInfo.setUpdateTime(new Date());
		userFullInfo.setPasswd(SunSHAUtils.encodeSHA512Hex(userFullInfo.getPasswd()));
		if(userFullInfo.getId() == null){
			UserFullInfo info1 = userFullInfoMapper.selectByName(userFullInfo.getUsername());
			UserFullInfo info2 = userFullInfoMapper.selectByName(userFullInfo.getEmail());
			if(info1!=null ){
				return -1;
			}
			if(info2!=null){
				return -2;
			}
			userFullInfoMapper.insert(userFullInfo);
			return userFullInfoMapper.selectByName(userFullInfo.getUsername()).getId();
		}else{
			userFullInfoMapper.updateByPrimaryKey(userFullInfo);
			return userFullInfo.getId();
		}
	}
	/**
	 * 随机获取一个可用于显示于首页的用户
	 * @return
	 */
	@Override
	public UserFullInfo getIndexShowUser(){
		return userFullInfoMapper.selectIndexShowUser();
	}
}
