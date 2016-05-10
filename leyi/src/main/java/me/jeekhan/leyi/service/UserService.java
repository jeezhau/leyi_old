package me.jeekhan.leyi.service;

import me.jeekhan.leyi.model.UserBaseInfo;
import me.jeekhan.leyi.model.UserFullInfo;

public interface UserService {
	public UserBaseInfo getUserBaseInfo(int id);
	
	public boolean authentification(UserBaseInfo userBaseInfo,String passwd);
	
	public UserFullInfo getUserFullInfo(int id);
	
	public int addUser(UserFullInfo userFullInfo);
	
	public int logOffUser(int id);

}
