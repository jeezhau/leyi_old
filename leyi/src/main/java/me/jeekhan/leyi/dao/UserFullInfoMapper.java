package me.jeekhan.leyi.dao;

import java.util.List;

import me.jeekhan.leyi.model.UserFullInfo;

public interface UserFullInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UserFullInfo record);

    UserFullInfo selectByPrimaryKey(Integer id);
    
    UserFullInfo selectByName(String username);

    int updateByPrimaryKey(UserFullInfo record);
    
    UserFullInfo selectIndexShowUser();
    
    List<UserFullInfo> selectUsers4Review();
    
    int countUsers4Review();
}