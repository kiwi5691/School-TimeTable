package com.android.backend.controller.course;

import com.android.backend.dao.*;
import com.android.backend.dtd.EvaluationInfoDTD;
import com.android.backend.dtd.HomeWorkInfoDTD;
import com.android.backend.service.BaseInfoService;
import com.android.backend.service.TeacherSettingService;
import com.android.backend.util.JSONChange;
import com.android.backend.util.Result;
import com.android.backend.util.ResultFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private TeacherSettingService teacherSettingService;
    @Autowired
    private BaseInfoService baseInfoService;


    /**
     *@Auther kiwi
     *@Data 2019/5/28
     * 获取分数
     @param  * @param userId
     *@reutn com.android.backend.util.Result
     */
    @RequestMapping(value = "user/search/checkgrade",method = RequestMethod.GET,produces = "application/json")
    public Result getGrade(String UserId) throws JsonProcessingException {

        String jsonStr = JSONChange.objToJson(baseInfoService.getAllRegularGrade(UserId));
        logger.info("json is"+jsonStr);
        logger.info("发送课程，分数----信息");
        return ResultFactory.buildSuccessResult(jsonStr);
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
    public Result getClassEvate(String UserId,String CourseName) throws JsonProcessingException {

        String jsonStr = JSONChange.objToJson(baseInfoService.getEvalution(UserId,CourseName));
        logger.info("json is"+jsonStr);
        logger.info("发送课程评价");
        return ResultFactory.buildSuccessResult(jsonStr);
    }

    /**
     *@Auther kiwi
     *@Data 2019/5/30
     * 上传课堂评价
     @param  * @param UserId
     *@reutn com.android.backend.util.Result
    */
    @RequestMapping(value = "user/search/updateClassEvate",method = RequestMethod.POST,produces = "application/json")
    public Result updateClassEvate(EvaluationInfoDTD evaluationInfoDTD,String UserId){

        logger.info(evaluationInfoDTD.toString());
        if(baseInfoService.updateEvaluation(evaluationInfoDTD,UserId)) {

            return ResultFactory.buildSuccessResult("更新成功");
        }
        else
            return ResultFactory.buildFailResult("更新失败");
    }


    /**
     *@Auther kiwi
     *@Data 2019/6/5
     * 老师更新分数
     @param  * @param grade
     * @param UserId
     * @param courseName
     *@reutn com.android.backend.util.Result
    */
    @RequestMapping(value = "user/search/updateGrade",method = RequestMethod.POST,produces = "application/json")
    public Result updateGrade(String grade,String UserId,String courseName){

        if(baseInfoService.updateGrade(grade,UserId,courseName)) {

            return ResultFactory.buildSuccessResult("更新成功");
        }
        else
            return ResultFactory.buildFailResult("更新失败");
    }


    /**
     *@Auther kiwi
     * 发送出勤和作业
     *@Data 2019/6/5
     @param  * @param UserId
     *@reutn com.android.backend.util.Result
    */
    @RequestMapping(value = "user/search/checkPartAndHomeWork",method = RequestMethod.GET,produces = "application/json")
    public Result getPartAndHomeWork(String UserId,String CourseName) throws JsonProcessingException {



        String jsonStr = JSONChange.objToJson(baseInfoService.getHomeWorkAndPart(UserId,CourseName));
        logger.info("json is"+jsonStr);
        logger.info("发送出勤，作业");
        return ResultFactory.buildSuccessResult(jsonStr);
    }


    /**
     *@Auther kiwi
     *@Data 2019/6/5
     * 更新出勤，作业
     @param  * @param homeWorkInfoDTD
     * @param UserId
     *@reutn com.android.backend.util.Result
    */
    @RequestMapping(value = "user/search/updatePartAndHomeWork",method = RequestMethod.POST,produces = "application/json")
    public Result updatePartAndHomeWork(HomeWorkInfoDTD homeWorkInfoDTD,String UserId){

        if(baseInfoService.updateHomeWorkAndPart(homeWorkInfoDTD,UserId)) {

            return ResultFactory.buildSuccessResult("更新成功");
        }
        else
            return ResultFactory.buildFailResult("更新失败");
    }



    /**
     *@Auther kiwi
     *@Data 2019/6/6
     * 老师获取所有学生id
     @param  * @param UserId
     *@reutn com.android.backend.util.Result
    */
    @RequestMapping(value = "user/search/getStudent",method = RequestMethod.GET,produces = "application/json")
    public Result getAllStudents(String UserId)throws JsonProcessingException {



        String jsonStr = JSONChange.objToJson(teacherSettingService.getAllStudent(UserId));
        logger.info("json is"+jsonStr);
        logger.info("发送课程评价");
        return ResultFactory.buildSuccessResult(jsonStr);
    }

    /**
     *@Auther kiwi
     *@Data 2019/6/7
     * 获取老师上的课
     @param  * @param UserId
     *@reutn com.android.backend.util.Result
    */
    @RequestMapping(value = "user/search/getTeacherCourseName",method = RequestMethod.GET,produces = "application/json")
    public Result getTeacherCourseName(String UserId){

        String courseName="";

        courseName=teacherSettingService.getTeacherCourse(UserId);

        if(courseName.isEmpty()){
            return ResultFactory.buildFailResult("此老师还没有课程");
        }else {
        return ResultFactory.buildSuccessResult(courseName);
        }
    }

}
