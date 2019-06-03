package com.android.backend.controller.user;

import com.android.backend.dao.RolePermissionMapper;
import com.android.backend.dao.UserLoginMapper;
import com.android.backend.domain.StudentInfo;
import com.android.backend.domain.TeacherInfo;
import com.android.backend.domain.UserLogin;
import com.android.backend.service.TimetableUserInfoService;
import com.android.backend.service.UserInfoService;
import com.android.backend.util.Result;
import com.android.backend.util.ResultFactory;
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
    public Result showStudentInfo(String UserId) {
        logger.info("Studentinfo is ",userInfoService.ShowStudent(UserId).toString());
        return ResultFactory.buildSuccessResult(userInfoService.ShowStudent(UserId).toString());
    }





    /**
     *@Auther kiwi
     *@Data 2019/5/30
     * 显示教师信息
     @param  * @param UserId
     * @param bindingResult
     * @param model
     *@reutn com.android.backend.util.Result
    */
    @RequestMapping(value = "/user/showTeacherInfo", method = RequestMethod.GET, produces = "application/json")
    public Result showTeacherInfo(String UserId,
                           BindingResult bindingResult, Model model) {

        // TODO 用josn传值对象错误，需要改成ResultFactory

        return ResultFactory.buildFailResult("1");
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
            return ResultFactory.buildFailResult("修改成功");
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
            return ResultFactory.buildFailResult("修改成功");
        else
            return ResultFactory.buildFailResult("修改失败");
    }
}



