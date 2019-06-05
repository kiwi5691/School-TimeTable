package com.android.backend.service;

import com.android.backend.dao.*;
import com.android.backend.domain.*;
import com.android.backend.dtd.CourseDataDTD;
import com.android.backend.dtd.TeacherAllDTD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther:kiwi
 * @Date: 2019/5/30 10:23
 */
@Service
public class CourseDetailService {

    /**
     *@Auther kiwi
     *logger 初始化
    */
    private static Logger logger = LoggerFactory.getLogger(CourseDetailService.class);

    @Resource
    private ClassRoomOnDutyMapper classRoomOnDutyMapper;
    @Resource
    private CourseDetailMapper courseDetailMapper;
    @Resource
    private CourseInfoMapper courseInfoMapper;
    @Resource
    private CourseBaseinfoMapper courseBaseinfoMapper;
    @Resource
    private UserCourseMapper userCourseMapper;
    @Resource
    private UserLoginMapper userLoginMapper;

    /**
     *@Auther kiwi
     *@Data 2019/5/30
     @param  * @param courseBaseinfo
     * @param courseInfo
     * @param StudentName
     *@reutn void
    */
    public void saveCourse(CourseBaseinfo courseBaseinfo,CourseInfo courseInfo,String StudentName){

        try {
            /**
             *单纯数据存表联结
             */
            courseBaseinfoMapper.insert(courseBaseinfo);
            courseBaseinfo.setCid(courseInfo.getId());
            courseInfoMapper.insert(courseInfo);

            /**
             *学生的单纯课程信息
             * courseDetail表
             */
            CourseDetail courseDetail = new CourseDetail();
            courseDetail.setCourseId(courseBaseinfo.getId().toString());
            courseDetail.setStudentName(StudentName);
            courseDetailMapper.insert(courseDetail);

            /**
             *class on duty 表
            */

            ClassRoomOnDuty classRoomOnDuty = new ClassRoomOnDuty();
            classRoomOnDuty.setCourseId(courseBaseinfo.getId().toString());
            classRoomOnDuty.setStudentName(StudentName);
            classRoomOnDutyMapper.insert(classRoomOnDuty);
            /**
             *学生，课程数据联结
             */
            UserCourse userCourse = new UserCourse();
            userCourse.setCid(courseBaseinfo.getId()); //课程id
            userCourse.setUid(userLoginMapper.selectAllByName(StudentName).getUserName());//学生id
            userCourseMapper.insert(userCourse);


        }catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     *@Auther kiwi
     *@Data 2019/6/4
     * 获取全部课程
     @param  * @param userId
     *@reutn java.util.ArrayList<com.android.backend.dtd.CourseDataDTD>
    */


    public List<CourseDataDTD> getAllCourse(String userId){


            List<UserCourse> uidList =new ArrayList<UserCourse>();

            uidList= userCourseMapper.selectUserId(userId);   //得到cid列

            List<CourseDataDTD> courseDataDTDS = new ArrayList<CourseDataDTD>(uidList.size());

            for(UserCourse s:uidList) {

                CourseInfo courseInfo =new CourseInfo();
                CourseBaseinfo courseBaseinfo =new CourseBaseinfo();
                courseInfo=courseInfoMapper.selectfromCid(s.getCid()); //info ---cid
                courseBaseinfo=courseBaseinfoMapper.selectFromCourseId(s.getCid());//baseinfo---cid

                CourseDataDTD courseDataDTD = new CourseDataDTD();

                courseDataDTD.setCid(s.getCid());
                courseDataDTD.setCourseName(courseInfo.getCourseName());
                courseDataDTD.setTeacher(courseInfo.getTeacher());
                courseDataDTD.setDay(courseBaseinfo.getDay());
                courseDataDTD.setLessonfrom(courseBaseinfo.getLessonfrom());
                courseDataDTD.setLessonto(courseBaseinfo.getLessonto());
                courseDataDTD.setPlace(courseBaseinfo.getPlace());
                courseDataDTD.setWeekfrom(courseBaseinfo.getWeekfrom());
                courseDataDTD.setWeekto(courseBaseinfo.getWeekto());
                courseDataDTD.setWeektype(courseBaseinfo.getWeektype());
                courseDataDTDS.add(courseDataDTD);

            }
            logger.info(courseDataDTDS.get(0).getCourseName());
            return courseDataDTDS;


    }


    /**
     *@Auther kiwi
     *@Data 2019/6/4
     * 获取教师名字
     @param  * @param userId
     *@reutn java.util.ArrayList<com.android.backend.dtd.TeacherAllDTD>
    */
    public List<TeacherAllDTD> getAllTeachers(String userId){

        List<TeacherAllDTD> allTeachers = new ArrayList<TeacherAllDTD>();

        List<UserCourse> uidList = new ArrayList<UserCourse>();
        uidList= userCourseMapper.selectUserId(userId);   //得到cid列

        for(UserCourse s:uidList) {
            CourseInfo courseInfo =new CourseInfo();
            courseInfo=courseInfoMapper.selectfromCid(s.getCid()); //info ---cid
            TeacherAllDTD teacherAllDTD =new TeacherAllDTD();
            teacherAllDTD.setCourseName(courseInfo.getCourseName());
            teacherAllDTD.setTeacherName(courseInfo.getTeacher());
            allTeachers.add(teacherAllDTD);
        }


        return allTeachers;
    }


    /**
     *@Auther kiwi
     *@Data 2019/6/4
     * 获取课程名字
     @param  * @param userId
     *@reutn java.util.ArrayList<java.lang.String>
    */
    public List<String> getAllCourseName(String userId){

        List<String> allCourses = new ArrayList<String>();
        try {
            List<UserCourse> uidList = new ArrayList<UserCourse>();
            uidList= userCourseMapper.selectUserId(userId);   //得到cid列

            for(UserCourse s:uidList) {
                CourseInfo courseInfo =new CourseInfo();
                courseInfo=courseInfoMapper.selectfromCid(s.getCid()); //info ---cid
                allCourses.add(courseInfo.getCourseName());
            }
        }catch (Exception e){
            allCourses.add("");

        }

        return allCourses;
    }

}
