package me.jeekhan.leyi.service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

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
	
}
