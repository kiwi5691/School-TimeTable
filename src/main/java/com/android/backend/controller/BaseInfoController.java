package com.android.backend.controller;

import com.android.backend.controller.UserController;
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
     @param  * @param userLogin
     *@reutn com.android.backend.util.Result
     */

    @RequestMapping(value = "user/search/checkgrade",method = RequestMethod.GET,produces = "applciation/json")
    public Result getGrade(UserLogin userLogin){

        return ResultFactory.buildSuccessResult("1");
    }

}
