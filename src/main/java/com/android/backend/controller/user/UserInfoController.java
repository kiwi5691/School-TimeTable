package com.android.backend.controller.user;

import com.android.backend.domain.StudentInfo;
import com.android.backend.domain.TeacherInfo;
import com.android.backend.service.UserInfoService;
import com.android.backend.util.JSONChange;
import com.android.backend.util.Result;
import com.android.backend.util.ResultFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther:kiwi
 * @Date: 2019/5/30 15:35
 */
@RestController
@CrossOrigin
public class UserInfoController {

    /**
     * @Auther kiwi
     * 初始化logger变量
     */
    private static Logger logger = LoggerFactory.getLogger(UserInfoController.class);


    /**
     * @Auther kiwi
     * 注入
     */
    @Autowired
    private UserInfoService userInfoService;

    /**
     *@Auther kiwi
     *@Data 2019/5/30
     * 显示学生信息
     @param  * @param UserId
     *@reutn com.android.backend.util.Result
    */
    @RequestMapping(value = "/user/showStudentInfo", method = RequestMethod.GET, produces = "application/json")
    public Result showStudentInfo(String UserId) throws JsonProcessingException {



        String jsonStr = JSONChange.objToJson(userInfoService.ShowStudent(UserId));


        logger.info("开始发送学生json格式信息，下例");
        logger.info(jsonStr);
        logger.info("--------------------");
        logger.info("学生tostring 格式");
        logger.info(userInfoService.ShowStudent(UserId).toString());

        return ResultFactory.buildSuccessResult(jsonStr);
    }





    /**
     *@Auther kiwi
     *@Data 2019/5/30
     * 显示教师信息
     @param  * @param UserId
     *@reutn com.android.backend.util.Result
    */
    @RequestMapping(value = "/user/showTeacherInfo", method = RequestMethod.GET, produces = "application/json")
    public Result showTeacherInfo(String UserId) throws JsonProcessingException {

        String jsonStr = JSONChange.objToJson(userInfoService.ShowTeacher(UserId));


        logger.info("开始发送教师json格式信息，下例");
        logger.info(jsonStr);
        logger.info("--------------------");
        logger.info("教师tostring 格式");
        logger.info(userInfoService.ShowTeacher(UserId).toString());

        return ResultFactory.buildSuccessResult(jsonStr);
    }


    /**
     *@Auther kiwi
     *@Data 2019/5/30
     * 修改学生信息
     @param  * @param studentInfo
     * @param bindingResult
     * @param model
     *@reutn com.android.backend.util.Result
    */
    @RequestMapping(value = "/user/UpdateStudentInfo", method = RequestMethod.POST, produces = "application/json")
    public Result updateStudentInfo(StudentInfo studentInfo,
                        BindingResult bindingResult, Model model) {

        if(userInfoService.UpdateStudentInfo(studentInfo))
            return ResultFactory.buildSuccessResult("修改成功");
        else
            return ResultFactory.buildFailResult("修改失败");
    }


    /**
     *@Auther kiwi
     *@Data 2019/5/30
     * 修改教师信息
     @param  * @param teacherInfo
     * @param bindingResult
     * @param model
     *@reutn com.android.backend.util.Result
    */
    @RequestMapping(value = "/user/UpdateTeacherInfo", method = RequestMethod.POST, produces = "application/json")
    public Result updateTeacherInfo(TeacherInfo teacherInfo,
                                    BindingResult bindingResult, Model model) {

        if(userInfoService.UpdateTeacherInfo(teacherInfo))
            return ResultFactory.buildSuccessResult("修改成功");
        else
            return ResultFactory.buildFailResult("修改失败");
    }
}



