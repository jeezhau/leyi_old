package me.jeekhan.leyi.dao;

import java.util.List;
import me.jeekhan.leyi.model.InviteInfo;
import me.jeekhan.leyi.model.InviteInfoExample;
import org.apache.ibatis.annotations.Param;

public interface InviteInfoMapper {
    int countByExample(InviteInfoExample example);

    int deleteByExample(InviteInfoExample example);

    int deleteByPrimaryKey(String inviteCode);

    int insert(InviteInfo record);

    int insertSelective(InviteInfo record);

    List<InviteInfo> selectByExample(InviteInfoExample example);

    InviteInfo selectByPrimaryKey(String inviteCode);

    int updateByExampleSelective(@Param("record") InviteInfo record, @Param("example") InviteInfoExample example);

    int updateByExample(@Param("record") InviteInfo record, @Param("example") InviteInfoExample example);

    int updateByPrimaryKeySelective(InviteInfo record);

    int updateByPrimaryKey(InviteInfo record);
}