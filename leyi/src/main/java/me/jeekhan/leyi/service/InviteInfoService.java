package me.jeekhan.leyi.service;

import me.jeekhan.leyi.model.InviteInfo;

public interface InviteInfoService {
	/**
	 * 根据邀请码获取邀请信息
	 * @param inviteCode
	 * @return
	 */
	public InviteInfo get(String inviteCode);
	/**
	 * 保存邀请信息
	 * @param inviteInfo
	 * @return
	 */
	public int insert(InviteInfo inviteInfo);
	
	/**
	 * 更新邀请信息
	 * @param inviteInfo
	 */
	public int update(InviteInfo inviteInfo);
	
	/**
	 * 获取用户最新一条邀请信息
	 * @param userId
	 * @return
	 */
	public InviteInfo get(int userId);

}
