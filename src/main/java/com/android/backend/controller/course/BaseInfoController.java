package com.android.backend.controller.course;

import com.android.backend.dao.*;
import com.android.backend.dtd.EvaluationInfoDTD;
import com.android.backend.service.BaseInfoService;
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
    private UserCourseMapper userCourseMapper;
    @Autowired
    private CourseDetailMapper courseDetailMapper;
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




    @RequestMapping(value = "user/search/checkpartAndhomeWork",method = RequestMethod.GET,produces = "application/json")
    public Result getPartAndHomework(String UserId) throws JsonProcessingException {

        String jsonStr = JSONChange.objToJson(baseInfoService.getAllRegularGrade(UserId));
        logger.info("json is"+jsonStr);
        logger.info("发送课程，分数----信息");
        return ResultFactory.buildSuccessResult(jsonStr);
    }

}
