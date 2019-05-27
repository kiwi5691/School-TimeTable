package com.android.backend.dao;

import com.android.backend.domain.CourseDetail;

public interface CourseDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CourseDetail record);

    int insertSelective(CourseDetail record);

    CourseDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CourseDetail record);

    int updateByPrimaryKey(CourseDetail record);
}