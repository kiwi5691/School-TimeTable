package com.android.backend.controller;

import com.android.backend.dao.CourseBaseinfoMapper;
import com.android.backend.dao.CourseDetailMapper;
import com.android.backend.dao.CourseInfoMapper;
import com.android.backend.domain.CourseBaseinfo;
import com.android.backend.domain.CourseInfo;
import com.android.backend.service.CourseDetailService;
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
            return ResultFactory.buildFailResult("保存成功");
        }catch (Exception e) {
            return ResultFactory.buildFailResult("保存失败");
        }


    }


    /**
     *@Auther kiwi
     *@Data 2019/5/30
     * 获取课表信息
     @param  * @param UserId
     * @param bindingResult
     *@reutn com.android.backend.util.Result
    */
    @RequestMapping(value = "user/course/get",method = RequestMethod.GET,produces = "applciation/json")
    public Result courseGet(String UserId, BindingResult bindingResult){

        return ResultFactory.buildFailResult("1");
    }



    /**
     *@Auther kiwi
     *@Data 2019/5/30
     * 获取所有教师
     @param  * @param UserId
     * @param bindingResult
     *@reutn com.android.backend.util.Result
    */
    @RequestMapping(value = "user/course/getAllteacher",method = RequestMethod.GET,produces = "applciation/json")
    public Result findAllTeacher(String UserId, BindingResult bindingResult){

        return ResultFactory.buildFailResult("1");
    }



}

