package me.jeekhan.leyi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.jeekhan.leyi.dao.InviteInfoMapper;
import me.jeekhan.leyi.model.InviteInfo;
import me.jeekhan.leyi.service.InviteInfoService;

@Service
public class InviteInfoServiceImpl implements InviteInfoService{
	@Autowired
	private InviteInfoMapper inviteInfoMapper;
	
	@Override
	public InviteInfo get(String inviteCode){
		return inviteInfoMapper.selectByPrimaryKey(inviteCode);
	}

	@Override
	public int insert(InviteInfo inviteInfo) {
		return inviteInfoMapper.insert(inviteInfo);
	}

	@Override
	public int update(InviteInfo inviteInfo) {
		return inviteInfoMapper.updateByPrimaryKey(inviteInfo);
	}

}
