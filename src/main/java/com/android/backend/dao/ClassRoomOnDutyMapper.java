package com.android.backend.dao;

import com.android.backend.domain.ClassRoomOnDuty;

public interface ClassRoomOnDutyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ClassRoomOnDuty record);

    int insertSelective(ClassRoomOnDuty record);

    ClassRoomOnDuty selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ClassRoomOnDuty record);

    int updateByPrimaryKey(ClassRoomOnDuty record);
}