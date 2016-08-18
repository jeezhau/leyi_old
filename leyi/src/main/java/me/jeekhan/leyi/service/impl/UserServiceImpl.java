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
	 * ��ȡ�û�������Ϣ
	 * @param	�û�ID
	 */
	@Override
	public UserBaseInfo getUserBaseInfo(int id){
		return userBaseInfoMapper.selectByPrimaryKey(id);
	}
	/**
	 * ��ȡ�û�������Ϣ
	 * @param	�û���������
	 */
	public UserFullInfo getUserFullInfo(String username){
		return userFullInfoMapper.selectByName(username);
	}
	/**
	 * �û������֤
	 * @param	userBaseInfo	�û�������Ϣ
	 * @param	passwd			�û����루���ģ�
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
	 * ��ȡ�û�����ϸ��Ϣ
	 * @param	�û�ID
	 */
	@Override
	public UserFullInfo getUserFullInfo(int id) {
		return userFullInfoMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * �����û���������£������޸�
	 * @param	�û���ϸ��Ϣ
	 * @return  �û�ID,0-ȱ����Ϣ��-1-�û����ѱ�ʹ��,-2-�����ѱ�ʹ��
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
	 * �����ȡһ����������ʾ����ҳ���û�
	 * @return
	 */
	@Override
	public UserFullInfo getIndexShowUser(){
		return userFullInfoMapper.selectIndexShowUser();
	}
}
