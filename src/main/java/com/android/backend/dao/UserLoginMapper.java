package com.android.backend.dao;

import com.android.backend.domain.UserLogin;

public interface UserLoginMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLogin record);

    int insertSelective(UserLogin record);

    UserLogin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLogin record);

    int updateByPrimaryKey(UserLogin record);

    int Checklogin(UserLogin userLogin);
}