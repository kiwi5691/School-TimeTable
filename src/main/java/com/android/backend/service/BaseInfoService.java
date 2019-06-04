package com.android.backend.service;

import com.android.backend.dao.ClassRoomOnDutyMapper;
import com.android.backend.dao.CourseInfoMapper;
import com.android.backend.dtd.RegularGradeDTD;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @Auther:kiwi
 * @Date: 2019/6/4 17:17
 */
@Service
public class BaseInfoService {

    @Resource
    private CourseDetailService courseDetailService;
    @Resource
    private ClassRoomOnDutyMapper classRoomOnDutyMapper;
    @Resource
    private CourseInfoMapper courseInfoMapper;


    public ArrayList<RegularGradeDTD> getAllRegularGrade(){
        ArrayList<RegularGradeDTD> regularGradeDTDS = new ArrayList<RegularGradeDTD>();


        return regularGradeDTDS;
    }
}
