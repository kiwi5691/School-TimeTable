package com.android.backend.dao;

import com.android.backend.domain.GlobalInfo;

public interface GlobalInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GlobalInfo record);

    int insertSelective(GlobalInfo record);

    GlobalInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GlobalInfo record);

    int updateByPrimaryKey(GlobalInfo record);
}