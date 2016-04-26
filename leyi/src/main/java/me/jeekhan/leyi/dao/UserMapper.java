package me.jeekhan.leyi.dao;

import me.jeekhan.leyi.model.User;

public interface UserMapper {


    int deleteByPrimaryKey(Integer recid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer recid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}