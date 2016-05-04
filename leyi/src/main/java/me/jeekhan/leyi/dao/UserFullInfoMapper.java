package me.jeekhan.leyi.dao;

import java.util.List;
import me.jeekhan.leyi.model.UserFullInfo;
import me.jeekhan.leyi.model.UserFullInfoExample;
import org.apache.ibatis.annotations.Param;

public interface UserFullInfoMapper {
    int countByExample(UserFullInfoExample example);

    int deleteByExample(UserFullInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserFullInfo record);

    int insertSelective(UserFullInfo record);

    List<UserFullInfo> selectByExampleWithBLOBs(UserFullInfoExample example);

    List<UserFullInfo> selectByExample(UserFullInfoExample example);

    UserFullInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserFullInfo record, @Param("example") UserFullInfoExample example);

    int updateByExampleWithBLOBs(@Param("record") UserFullInfo record, @Param("example") UserFullInfoExample example);

    int updateByExample(@Param("record") UserFullInfo record, @Param("example") UserFullInfoExample example);

    int updateByPrimaryKeySelective(UserFullInfo record);

    int updateByPrimaryKeyWithBLOBs(UserFullInfo record);

    int updateByPrimaryKey(UserFullInfo record);
}