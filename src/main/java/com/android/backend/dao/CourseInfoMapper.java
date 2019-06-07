package com.android.backend.dao;

import com.android.backend.domain.CourseInfo;

public interface CourseInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CourseInfo record);

    int insertSelective(CourseInfo record);

    CourseInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CourseInfo record);

    int updateByPrimaryKey(CourseInfo record);

    CourseInfo selectfromCid(Integer cid);

    int selectCourseName(String courseName);

    int findId(CourseInfo courseInfo);

    String selectCourseNameByName(String name);

}