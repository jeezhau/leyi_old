package me.jeekhan.leyi.dao;

import java.util.List;
import me.jeekhan.leyi.model.ReviewInfo;
import me.jeekhan.leyi.model.ReviewInfoExample;
import org.apache.ibatis.annotations.Param;

public interface ReviewInfoMapper {
    int countByExample(ReviewInfoExample example);

    int deleteByExample(ReviewInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ReviewInfo record);

    int insertSelective(ReviewInfo record);

    List<ReviewInfo> selectByExampleWithBLOBs(ReviewInfoExample example);

    List<ReviewInfo> selectByExample(ReviewInfoExample example);

    ReviewInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ReviewInfo record, @Param("example") ReviewInfoExample example);

    int updateByExampleWithBLOBs(@Param("record") ReviewInfo record, @Param("example") ReviewInfoExample example);

    int updateByExample(@Param("record") ReviewInfo record, @Param("example") ReviewInfoExample example);

    int updateByPrimaryKeySelective(ReviewInfo record);

    int updateByPrimaryKeyWithBLOBs(ReviewInfo record);

    int updateByPrimaryKey(ReviewInfo record);
}