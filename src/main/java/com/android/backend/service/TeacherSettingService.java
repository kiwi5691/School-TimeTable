package com.android.backend.service;

import com.android.backend.dao.ClassRoomOnDutyMapper;
import com.android.backend.dao.CourseDetailMapper;
import com.android.backend.dao.CourseInfoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther:kiwi
 * @Date: 2019/6/6 15:40
 */
@Service
public class TeacherSettingService {

    @Resource
    private CourseDetailService courseDetailService;
    @Resource
    private ClassRoomOnDutyMapper classRoomOnDutyMapper;
    @Resource
    private CourseInfoMapper courseInfoMapper;
    @Resource
    private CourseDetailMapper courseDetailMapper;



}
