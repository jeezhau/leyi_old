package me.jeekhan.leyi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import me.jeekhan.leyi.model.UserFullInfo;

public interface UserFullInfoMapper {

    int updateEnabledStatus(@Param("id")Integer id,@Param("status") String status);

    int insert(UserFullInfo record);

    UserFullInfo selectByPrimaryKey(Integer id);
    
    UserFullInfo selectByName(String username);

    int updateByPrimaryKey(UserFullInfo record);
    
    UserFullInfo selectIndexShowUser();
    
    List<UserFullInfo> selectUsers4Review();
    
    int countUsers4Review();
    
}