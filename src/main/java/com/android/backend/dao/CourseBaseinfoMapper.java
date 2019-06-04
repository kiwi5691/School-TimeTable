package com.android.backend.dao;

import com.android.backend.domain.CourseBaseinfo;

public interface CourseBaseinfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CourseBaseinfo record);

    int insertSelective(CourseBaseinfo record);

    CourseBaseinfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CourseBaseinfo record);

    int updateByPrimaryKey(CourseBaseinfo record);

    CourseBaseinfo selectFromCourseId(int cid);
}