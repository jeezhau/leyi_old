package me.jeekhan.leyi.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.jeekhan.leyi.common.SunSHAUtils;
import me.jeekhan.leyi.dao.InviteInfoMapper;
import me.jeekhan.leyi.dao.ReviewInfoMapper;
import me.jeekhan.leyi.dao.UserBaseInfoMapper;
import me.jeekhan.leyi.dao.UserFullInfoMapper;
import me.jeekhan.leyi.model.InviteInfo;
import me.jeekhan.leyi.model.ReviewInfo;
import me.jeekhan.leyi.model.UserBaseInfo;
import me.jeekhan.leyi.model.UserFullInfo;
import me.jeekhan.leyi.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserBaseInfoMapper userBaseInfoMapper;
	@Autowired
	private UserFullInfoMapper userFullInfoMapper;
	@Autowired
	private ReviewInfoMapper reviewInfoMapper;
	@Autowired
	private InviteInfoMapper inviteInfoMapper;
	
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
		UserFullInfo info1 = userFullInfoMapper.selectByName(userFullInfo.getUsername());
		UserFullInfo info2 = userFullInfoMapper.selectByName(userFullInfo.getEmail());
		if(info1!=null && info1.getId() != userFullInfo.getId()){
			return -1;
		}
		if(info2!=null && info1.getId() != userFullInfo.getId()){
			return -2;
		}
		if(userFullInfo.getId() == null){//新增
			userFullInfo.setPasswd(SunSHAUtils.encodeSHA512Hex(userFullInfo.getPasswd()));
			userFullInfo.setRegistTime(new Date());
			userFullInfo.setUpdateTime(new Date());
			userFullInfoMapper.insert(userFullInfo);
			int userId= userFullInfoMapper.selectByName(userFullInfo.getUsername()).getId();
			//修改邀请码的使用状态
			String inviteCode = userFullInfo.getInviteCode();
			InviteInfo inviteInfo = inviteInfoMapper.selectByPrimaryKey(inviteCode);
			inviteInfo.setStatus("1");
			inviteInfo.setUseTime(new Date());
			inviteInfoMapper.updateByPrimaryKey(inviteInfo);
			//为用户补充一个邀请码
			InviteInfo Invite = new InviteInfo();
			Invite.setCrtTime(new Date());
			Invite.setCrtUser(inviteInfo.getCrtUser());
			inviteInfoMapper.insert(Invite);
			//为新增用户添加一个邀请码
			InviteInfo newInvite = new InviteInfo();
			newInvite.setCrtTime(new Date());
			newInvite.setCrtUser(userId);
			inviteInfoMapper.insert(newInvite);
			return userId;
		}else{	//修改
			userFullInfo.setRegistTime(info1.getRegistTime());
			userFullInfo.setUpdateTime(new Date());
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
	
	/**
	 * 取待审核用户数量
	 * @return
	 */
	@Override
	public int get4ReviewUsersCnt() {
		return userFullInfoMapper.countUsers4Review();
	}
	
	/**
	 * 取10条待审核的用户
	 * @return
	 */
	public List<UserFullInfo> getUsers4Review(){
		return userFullInfoMapper.selectUsers4Review();
	}
	
	/**
	 * 用户审核
	 * @param userId   用户ID
	 * @param result	审核结果:0-通过,R-拒绝
	 * @param reviewInfo	审核说明
	 */
	@Override
	public int reviewUser(int userId,String result,ReviewInfo reviewInfo){
		String usrInfo = userFullInfoMapper.selectByPrimaryKey(userId).toString();
		reviewInfo.setObjName("tb_user_full_info");
		reviewInfo.setKeyId(userId);
		reviewInfo.setOriginalInfo(usrInfo);
		reviewInfo.setResult(result);
		reviewInfo.setReviewTime(new Date());
		reviewInfoMapper.insert(reviewInfo);
		return userFullInfoMapper.updateEnabledStatus(userId, result);
	}

}
