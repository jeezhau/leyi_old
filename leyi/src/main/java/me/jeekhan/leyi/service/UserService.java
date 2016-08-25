package me.jeekhan.leyi.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import me.jeekhan.leyi.model.ReviewInfo;
import me.jeekhan.leyi.model.UserBaseInfo;
import me.jeekhan.leyi.model.UserFullInfo;

public interface UserService {
	public UserBaseInfo getUserBaseInfo(int id);
	
	public boolean authentification(String username,String passwd);
	
	public UserFullInfo getUserFullInfo(String username);
	
	public UserFullInfo getUserFullInfo(int id);
	
	public int saveUser(UserFullInfo userFullInfo) throws NoSuchAlgorithmException, UnsupportedEncodingException;
	
	public UserFullInfo getIndexShowUser();

	/**
	 * 取待审核用户数量
	 * @return
	 */
	public int get4ReviewUsersCnt();
	
	/**
	 * 取10条待审核的用户
	 * @return
	 */
	public List<UserFullInfo> getUsers4Review();
	
	/**
	 * 用户审核
	 * @param userId   用户ID
	 * @param result	审核结果:0-通过,R-拒绝
	 * @param reviewInfo	审核说明
	 */
	public int reviewUser(int userId,String result,ReviewInfo reviewInfo);

}
