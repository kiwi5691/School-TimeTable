package com.android.backend.controller;

import com.android.backend.domain.CourseBaseinfo;
import com.android.backend.domain.CourseInfo;
import com.android.backend.service.CourseDetailService;
import com.android.backend.util.JSONChange;
import com.android.backend.util.Result;
import com.android.backend.util.ResultFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private CourseDetailService courseDetailService;

    /**
     *@Auther kiwi
     *@Data 2019/5/30
     * 保存课程
     @param  * @param courseBaseinfo
     * @param courseInfo
     * @param StudentName
     * @param bindingResult
     *@reutn com.android.backend.util.Result
    */
    @RequestMapping(value = "user/course/save",method = RequestMethod.POST,produces = "applciation/json")
    public Result courseSave(CourseBaseinfo courseBaseinfo, CourseInfo courseInfo, String StudentName, BindingResult bindingResult){

        try {
            courseDetailService.saveCourse(courseBaseinfo,courseInfo,StudentName);
            return ResultFactory.buildSuccessResult("保存成功");
        }catch (Exception e) {
            return ResultFactory.buildFailResult("保存失败");
        }


    }


    /**
     *@Auther kiwi
     *@Data 2019/5/30
     * 获取课表信息
     @param  * @param UserId
     *@reutn com.android.backend.util.Result
    */
    @RequestMapping(value = "user/course/get",method = RequestMethod.GET,produces = "applciation/json")
    public Result courseGet(String UserId) throws JsonProcessingException {

        String jsonStr = JSONChange.objToJson(courseDetailService.getAllCourse(UserId));
        logger.info("发送所有课程");
        return ResultFactory.buildSuccessResult(jsonStr);
    }



    /**
     *@Auther kiwi
     *@Data 2019/5/30
     * 获取所有教师
     @param  * @param UserId
     *@reutn com.android.backend.util.Result
    */
    @RequestMapping(value = "user/course/getAllteacher",method = RequestMethod.GET,produces = "applciation/json")
    public Result findAllTeacher(String UserId) throws JsonProcessingException {

        String jsonStr = JSONChange.objToJson(courseDetailService.getAllTeachers(UserId));
        logger.info("发送所有教师");
        return ResultFactory.buildSuccessResult(jsonStr);
    }

    /**
     *@Auther kiwi
     *@Data 2019/6/4
     * 获取所有的课程
     @param  * @param UserId
     *@reutn com.android.backend.util.Result
    */
    @RequestMapping(value = "user/course/getAllcourseName",method = RequestMethod.GET,produces = "applcaition/json")
    public Result getAllCourse(String UserId) throws JsonProcessingException{
        String jsonStr = JSONChange.objToJson(courseDetailService.getAllCourseName(UserId));
        logger.info("发送所有课程名字");
        return ResultFactory.buildSuccessResult(jsonStr);

    }
}

