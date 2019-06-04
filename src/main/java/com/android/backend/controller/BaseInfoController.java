package com.android.backend.controller;

import com.android.backend.dao.*;
import com.android.backend.domain.UserLogin;
import com.android.backend.util.Result;
import com.android.backend.util.ResultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Auther:kiwi
 * @Date: 2019/5/27 23:13
 */
public class BaseInfoController {

    /**
     *@Auther kiwi
     *初始化logger变量
     */
    private static Logger logger = LoggerFactory.getLogger(BaseInfoController.class);


    /**
     *@Auther kiwi
     *注入
     */
    @Autowired
    private ClassRoomOnDutyMapper classRoomOnDutyMapper;
    @Autowired
    private UserCourseMapper userCourseMapper;
    @Autowired
    private CourseDetailMapper courseDetailMapper;



    /**
     *@Auther kiwi
     *@Data 2019/5/28
     * 获取分数
     @param  * @param userId
     *@reutn com.android.backend.util.Result
     */

    @RequestMapping(value = "user/search/checkgrade",method = RequestMethod.GET,produces = "application/json")
    public Result getGrade(String UserId){


        return ResultFactory.buildSuccessResult("1");
    }

    /**
     *@Auther kiwi
     *@Data 2019/5/30
     * 获取课堂评价
     @param  * @param UserId
     * @param CourseName
     *@reutn com.android.backend.util.Result
    */
    @RequestMapping(value = "user/search/checkClassEvate",method = RequestMethod.GET,produces = "application/json")
    public Result getClassEvate(String UserId,String CourseName){


        return ResultFactory.buildSuccessResult("1");
    }

    /**
     *@Auther kiwi
     *@Data 2019/5/30
     * 上传课堂评价
     @param  * @param UserId
     * @param CourseName
     *@reutn com.android.backend.util.Result
    */
    @RequestMapping(value = "user/search/updateClassEvate",method = RequestMethod.GET,produces = "application/json")
    public Result updateClassEvate(String UserId,String CourseName){


        return ResultFactory.buildSuccessResult("1");
    }







}
