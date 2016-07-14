package me.jeekhan.leyi.dao;

import me.jeekhan.leyi.model.UserBaseInfo;

public interface UserBaseInfoMapper {

    int deleteByPrimaryKey(Integer userId);

    int insert(UserBaseInfo record);

    UserBaseInfo selectByPrimaryKey(Integer userId);

    int updateByPrimaryKey(UserBaseInfo record);
    
    UserBaseInfo selectByName(String username);
}