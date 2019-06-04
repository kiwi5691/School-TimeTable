package com.android.backend.dao;

import com.android.backend.domain.StudentInfo;

import java.util.Date;

public interface StudentInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StudentInfo record);

    int insertSelective(StudentInfo record);

    StudentInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentInfo record);

    int updateByPrimaryKey(StudentInfo record);

    StudentInfo selectById(String UserId);

    int updateByUserId(StudentInfo studentInfo);

    int updateLoginTime(StudentInfo studentInfo);

}