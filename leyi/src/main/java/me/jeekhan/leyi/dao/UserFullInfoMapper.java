package me.jeekhan.leyi.dao;

import me.jeekhan.leyi.model.UserFullInfo;

public interface UserFullInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserFullInfo record);

    UserFullInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(UserFullInfo record);
    
    int logOffUserById(int id);
}