package com.android.backend.dao;

import com.android.backend.domain.TableStorage;

public interface TableStorageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TableStorage record);

    int insertSelective(TableStorage record);

    TableStorage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TableStorage record);

    int updateByPrimaryKey(TableStorage record);
}