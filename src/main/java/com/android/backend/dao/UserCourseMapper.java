package com.android.backend.dao;

import com.android.backend.domain.UserCourse;

import java.util.ArrayList;
import java.util.List;

public interface UserCourseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserCourse record);

    int insertSelective(UserCourse record);

    UserCourse selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserCourse record);

    int updateByPrimaryKey(UserCourse record);

    List<UserCourse> selectUserId(String userId);

    List<UserCourse> selectCid(Integer cid);
}