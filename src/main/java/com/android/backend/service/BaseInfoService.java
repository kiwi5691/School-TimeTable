package com.android.backend.service;

import com.android.backend.dao.ClassRoomOnDutyMapper;
import com.android.backend.dao.CourseDetailMapper;
import com.android.backend.dao.CourseInfoMapper;
import com.android.backend.domain.CourseDetail;
import com.android.backend.domain.CourseInfo;
import com.android.backend.dtd.CourseDataDTD;
import com.android.backend.dtd.EvaluationInfoDTD;
import com.android.backend.dtd.RegularGradeDTD;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    @Resource
    private CourseDetailMapper courseDetailMapper;

    /**
     *@Auther kiwi
     *@Data 2019/6/5
     * 获取课程--分数
     @param  * @param userId
     *@reutn java.util.List<com.android.backend.dtd.RegularGradeDTD>
    */
    public List<RegularGradeDTD> getAllRegularGrade(String userId){
        List<RegularGradeDTD> regularGradeDTDS = new ArrayList<RegularGradeDTD>();

        List<CourseDetail> courseDetails = new ArrayList<CourseDetail>();
        courseDetails=courseDetailMapper.selectFromUserId(userId);

        for(CourseDetail courseDetail:courseDetails){


            CourseInfo courseInfo = new CourseInfo();
            courseInfo=courseInfoMapper.selectfromCid(Integer.parseInt(courseDetail.getCourseId()));

            RegularGradeDTD regularGradeDTD = new RegularGradeDTD();
            regularGradeDTD.setRegularGrade(courseDetail.getRegularGrade());
            regularGradeDTD.setCourseName(courseInfo.getCourseName());

            regularGradeDTDS.add(regularGradeDTD);
        }
        return regularGradeDTDS;
    }

    /**
     *@Auther kiwi
     *@Data 2019/6/5
     * 获取课堂评价和分数
     @param  * @param userId
     * @param courseName
     *@reutn java.util.List<com.android.backend.dtd.EvaluationInfoDTD>
    */

    public List<EvaluationInfoDTD> getEvalution(String userId,String courseName){

        List<EvaluationInfoDTD> evaluationInfoDTDS = new ArrayList<EvaluationInfoDTD>();

        List<CourseDetail> courseDetails = new ArrayList<CourseDetail>();
        courseDetails=courseDetailMapper.selectFromUserId(userId);

        for(CourseDetail courseDetail:courseDetails) {

            EvaluationInfoDTD evaluationInfoDTD = new EvaluationInfoDTD();


            CourseInfo courseInfo = new CourseInfo();
            courseInfo=courseInfoMapper.selectfromCid(Integer.parseInt(courseDetail.getCourseId()));

            evaluationInfoDTD.setCourseName(courseInfo.getCourseName());
            evaluationInfoDTD.setEvaluationInfo(courseDetail.getEvaluationInfo());
            evaluationInfoDTD.setEvaluationScore(courseDetail.getEvaluationScore());

            evaluationInfoDTDS.add(evaluationInfoDTD);
        }


            return evaluationInfoDTDS;
    }


    /**
     *@Auther kiwi
     *@Data 2019/6/5
     * 更新课堂评价
     @param  * @param evaluationInfoDTD
     * @param userId
     *@reutn boolean
    */
    public boolean updateEvaluation(EvaluationInfoDTD evaluationInfoDTD,String userId){
        CourseDetail courseDetail = new CourseDetail();
        courseDetail.setStudentName(userId);
        courseDetail.setEvaluationInfo(evaluationInfoDTD.getEvaluationInfo());
        courseDetail.setEvaluationScore(evaluationInfoDTD.getEvaluationScore());

       if(courseDetailMapper.updateEvaluateByUserId(courseDetail)==1){
           return true;
       }
       else
           return false;
    }

    /**
     *@Auther kiwi
     *@Data 2019/6/5
     * 更新分数
     @param  * @param regularGradeDTD
     * @param userId
     * @param courseName
     *@reutn boolean
    */
    public boolean updateGrade(String grade ,String userId,String courseName){

        CourseDetail courseDetail = new CourseDetail();
        courseDetail.setStudentName(userId); //userid
        courseDetail.setCourseId(String.valueOf(courseInfoMapper.selectCourseName(courseName))); //id
        courseDetail.setRegularGrade(grade);
        if(courseDetailMapper.updateGradeByCourseName(courseDetail)==1){
            return true;
        }
        else
            return false;
    }
}
