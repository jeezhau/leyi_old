package me.jeekhan.leyi.dao;

import me.jeekhan.leyi.model.ReviewInfo;

public interface ReviewInfoMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(ReviewInfo record);

    int insertSelective(ReviewInfo record);

    ReviewInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ReviewInfo record);

    int updateByPrimaryKeyWithBLOBs(ReviewInfo record);

    int updateByPrimaryKey(ReviewInfo record);
}