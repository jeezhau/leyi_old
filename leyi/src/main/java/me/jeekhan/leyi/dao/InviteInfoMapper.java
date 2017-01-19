package me.jeekhan.leyi.dao;

import me.jeekhan.leyi.model.InviteInfo;

public interface InviteInfoMapper {


    int insert(InviteInfo record);

    InviteInfo selectByPrimaryKey(String inviteCode);

    int updateByPrimaryKey(InviteInfo record);
    
    InviteInfo selectByUserId(int userId);
}