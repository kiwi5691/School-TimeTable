package com.android.backend.dao;

import com.android.backend.domain.CourseDetail;

import java.util.ArrayList;

public interface CourseDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CourseDetail record);

    int insertSelective(CourseDetail record);

    CourseDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CourseDetail record);

    int updateByPrimaryKey(CourseDetail record);

    ArrayList<CourseDetail> selectFromUserId(String userId);
}