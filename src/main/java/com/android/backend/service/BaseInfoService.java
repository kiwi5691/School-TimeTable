package com.android.backend.service;

import com.android.backend.dao.ClassRoomOnDutyMapper;
import com.android.backend.dao.CourseDetailMapper;
import com.android.backend.dao.CourseInfoMapper;
import com.android.backend.domain.ClassRoomOnDuty;
import com.android.backend.domain.CourseDetail;
import com.android.backend.domain.CourseInfo;
import com.android.backend.dtd.CourseDataDTD;
import com.android.backend.dtd.EvaluationInfoDTD;
import com.android.backend.dtd.HomeWorkInfoDTD;
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

    public EvaluationInfoDTD getEvalution(String userId,String courseName){

        EvaluationInfoDTD evaluationInfoDTD = new EvaluationInfoDTD();

        List<CourseDetail> courseDetails = new ArrayList<CourseDetail>();
        courseDetails=courseDetailMapper.selectFromUserId(userId);

        for(CourseDetail courseDetail:courseDetails) {

            EvaluationInfoDTD evaluationInfoDTDs = new EvaluationInfoDTD();

            CourseDetail courseDetail1 = new CourseDetail();

            CourseInfo courseInfo = new CourseInfo();   //获取courseName
            int cid = courseInfoMapper.selectCourseName(courseName);
            courseInfo=courseInfoMapper.selectfromCid(Integer.parseInt(courseDetail.getCourseId()));

            courseDetail1=courseDetailMapper.selectFromCid(String.valueOf(cid));
            evaluationInfoDTD.setCourseName(courseInfo.getCourseName());
            evaluationInfoDTD.setEvaluationInfo(courseDetail1.getEvaluationInfo());
            evaluationInfoDTD.setEvaluationScore(courseDetail1.getEvaluationScore());
            break;
        }


            return evaluationInfoDTD;
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


    /**
     *@Auther kiwi
     *@Data 2019/6/5
     * 获取出勤，作业
     @param  * @param userId
     *@reutn java.util.List<com.android.backend.dtd.HomeWorkInfoDTD>
    */
    public List<HomeWorkInfoDTD> getHomeWorkAndPart(String userId,String courseName){

        List<HomeWorkInfoDTD> homeWorkInfoDTDS = new ArrayList<HomeWorkInfoDTD>();
        List<ClassRoomOnDuty> classRoomOnDuties = new ArrayList<ClassRoomOnDuty>();

        int cid = courseInfoMapper.selectCourseName(courseName);

        ClassRoomOnDuty classRoomOnDut = new ClassRoomOnDuty();

        classRoomOnDut.setCourseId(String.valueOf(cid));
        classRoomOnDut.setStudentName(userId);

        classRoomOnDuties = classRoomOnDutyMapper.selectFromUserNameAndCourseId(classRoomOnDut);

        for(ClassRoomOnDuty classRoomOnDuty:classRoomOnDuties){

            HomeWorkInfoDTD homeWorkInfoDTD = new HomeWorkInfoDTD();

            CourseInfo courseInfo = new CourseInfo();
            courseInfo=courseInfoMapper.selectfromCid(Integer.parseInt(classRoomOnDuty.getCourseId()));

            homeWorkInfoDTD.setCourseName(courseInfo.getCourseName());
            homeWorkInfoDTD.setDay(classRoomOnDuty.getDay());
            homeWorkInfoDTD.setHomeworkGrade(classRoomOnDuty.getHomeworkGrade());
            homeWorkInfoDTD.setParticipation(classRoomOnDuty.getParticipation());

            homeWorkInfoDTDS.add(homeWorkInfoDTD);
        }

        return homeWorkInfoDTDS;
    }

    public boolean updateHomeWorkAndPart(HomeWorkInfoDTD homeWorkInfoDTD,String UserId){

        try{
            ClassRoomOnDuty classRoomOnDuty = new ClassRoomOnDuty();
            classRoomOnDuty.setStudentName(UserId);
            classRoomOnDuty.setCourseId(String.valueOf(courseInfoMapper.selectCourseName(homeWorkInfoDTD.getCourseName())));     //courseName 转id
            classRoomOnDuty.setHomeworkGrade(homeWorkInfoDTD.getHomeworkGrade());
            classRoomOnDuty.setParticipation(homeWorkInfoDTD.getParticipation());
            classRoomOnDuty.setDay(homeWorkInfoDTD.getDay());
            classRoomOnDutyMapper.insert(classRoomOnDuty);
        }catch (Exception e){
//            return false;
              e.printStackTrace();
        }
        return false;
    }
}
