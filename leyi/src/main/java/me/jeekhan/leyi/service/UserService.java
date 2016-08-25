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
	 * ȡ������û�����
	 * @return
	 */
	public int get4ReviewUsersCnt();
	
	/**
	 * ȡ10������˵��û�
	 * @return
	 */
	public List<UserFullInfo> getUsers4Review();
	
	/**
	 * �û����
	 * @param userId   �û�ID
	 * @param result	��˽��:0-ͨ��,R-�ܾ�
	 * @param reviewInfo	���˵��
	 */
	public int reviewUser(int userId,String result,ReviewInfo reviewInfo);

}
