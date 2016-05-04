package me.jeekhan.leyi.dao;

import java.util.List;
import me.jeekhan.leyi.model.UserBaseInfo;
import me.jeekhan.leyi.model.UserBaseInfoExample;
import org.apache.ibatis.annotations.Param;

public interface UserBaseInfoMapper {
    int countByExample(UserBaseInfoExample example);

    int deleteByExample(UserBaseInfoExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(UserBaseInfo record);

    int insertSelective(UserBaseInfo record);

    List<UserBaseInfo> selectByExample(UserBaseInfoExample example);

    UserBaseInfo selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") UserBaseInfo record, @Param("example") UserBaseInfoExample example);

    int updateByExample(@Param("record") UserBaseInfo record, @Param("example") UserBaseInfoExample example);

    int updateByPrimaryKeySelective(UserBaseInfo record);

    int updateByPrimaryKey(UserBaseInfo record);
}