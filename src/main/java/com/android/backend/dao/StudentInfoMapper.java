package com.android.backend.dao;

import com.android.backend.domain.StudentInfo;

import java.util.Date;
import java.util.List;

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

    int selectIsRid(String userId);

    List<StudentInfo> getAll();

}