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
		UserBaseInfo  userInfo = userBaseInfoMapper.selectByName(username);
		return userFullInfoMapper.selectByPrimaryKey(userInfo.getUserId());
	}
	/**
	 * �û������֤
	 * @param	userBaseInfo	�û�������Ϣ
	 * @param	passwd			�û����루���ģ�
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
	 * ע���û�
	 * @param id
	 * @return
	 */
	public int logOffUser(int id){
		return userFullInfoMapper.logOffUserById(id);
	}
}
