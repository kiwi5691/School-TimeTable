package com.android.backend.controller;

import com.android.backend.controller.UserController;
import com.android.backend.dao.CourseBaseinfoMapper;
import com.android.backend.dao.CourseDetailMapper;
import com.android.backend.dao.CourseInfoMapper;
import com.android.backend.domain.CourseBaseinfo;
import com.android.backend.util.Result;
import com.android.backend.util.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther:kiwi
 * @Date: 2019/5/27 23:13
 */
@CrossOrigin
@RestController
public class CourseController {

    /**
     *@Auther kiwi
     *初始化logger变量
     */
    private static Logger logger = LoggerFactory.getLogger(CourseController.class);


    /**
     *@Auther kiwi
     *注入
     */
    @Autowired
    private CourseBaseinfoMapper courseBaseinfoMapper;
    @Autowired
    private CourseDetailMapper courseDetailMapper;
    @Autowired
    private CourseInfoMapper courseInfoMapper;



    @RequestMapping(value = "user/course/save",method = RequestMethod.POST,produces = "applciation/json")
    public Result courseSave(CourseBaseinfo cInfo, BindingResult bindingResult){



        cInfo.setWeekfrom(2);
        cInfo.setWeekto(18);
        cInfo.setWeektype(1);
      //  cInfo.setDay(3);
        cInfo.setLessonfrom(3);
        cInfo.setLessonto(4);
      //  cInfo.setCoursename("数据库原理");
    //    cInfo.setTeacher("李华");
        cInfo.setPlace("第一教学楼302");

   //     courseInfoList.add(cInfo);
     //   courseInfoList.add(cInfo2);


        return ResultFactory.buildFailResult("1");
    }


    @RequestMapping(value = "user/course/get",method = RequestMethod.GET,produces = "applciation/json")
    public Result courseGet(CourseBaseinfo cInfo, BindingResult bindingResult){

        return ResultFactory.buildFailResult("1");
    }
}

