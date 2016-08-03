package me.jeekhan.leyi.service;

import me.jeekhan.leyi.model.UserBaseInfo;
import me.jeekhan.leyi.model.UserFullInfo;

public interface UserService {
	public UserBaseInfo getUserBaseInfo(int id);
	
	public boolean authentification(String username,String passwd);
	
	public UserFullInfo getUserFullInfo(String username);
	
	public UserFullInfo getUserFullInfo(int id);
	
	public int saveUser(UserFullInfo userFullInfo);
	
	public UserFullInfo getIndexShowUser();

	
}
