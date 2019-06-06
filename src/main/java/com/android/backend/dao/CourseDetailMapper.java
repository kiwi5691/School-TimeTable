package com.android.backend.dao;

import com.android.backend.domain.CourseDetail;

import java.util.ArrayList;
import java.util.List;

public interface CourseDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CourseDetail record);

    int insertSelective(CourseDetail record);

    CourseDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CourseDetail record);

    int updateByPrimaryKey(CourseDetail record);

    CourseDetail selectFromCid(String cid);


    List<CourseDetail> selectFromUserId(String userId);

    int updateEvaluateByUserId(CourseDetail courseDetail);

    int updateGradeByCourseName(CourseDetail courseDetail);
}