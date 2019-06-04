package com.android.backend.dao;

import com.android.backend.domain.TeacherInfo;

import java.util.Date;

public interface TeacherInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TeacherInfo record);

    int insertSelective(TeacherInfo record);

    TeacherInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TeacherInfo record);

    int updateByPrimaryKey(TeacherInfo record);

    TeacherInfo selectById(String UserId);

    int updateByUserId(TeacherInfo teacherInfo);

    int updateLoginTime(TeacherInfo teacherInfo);

}