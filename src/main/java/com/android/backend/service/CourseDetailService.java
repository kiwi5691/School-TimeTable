package com.android.backend.service;

import com.android.backend.dao.*;
import com.android.backend.domain.*;
import com.android.backend.dtd.CourseDataDTD;
import com.android.backend.dtd.TeacherAllDTD;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @Auther:kiwi
 * @Date: 2019/5/30 10:23
 */
@Service
public class CourseDetailService {

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
             */
            CourseDetail courseDetail = new CourseDetail();
            courseDetail.setCourseId(courseBaseinfo.getId().toString());
            courseDetail.setStudentName(StudentName);
            courseDetailMapper.insert(courseDetail);

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


    public ArrayList<CourseDataDTD> getAllCourse(String userId){

        /**
         *@Auther kiwi
         *dtd参数初始化
        */
        Integer cid =0;
        String Teacher = "";
        String courseName="";
        Integer weekfrom=0;
        Integer weekto=0;
        Integer weektype=0;
        String day="";
        Integer lessonfrom=0;
        Integer lessonto=0;
        String place="";

        ArrayList<CourseDataDTD> courseDataDTDS = new ArrayList<CourseDataDTD>();


        try{

            ArrayList<Integer> uidList = new ArrayList<Integer>();
            uidList= userCourseMapper.selectUserId(userId);   //得到cid列

            int flag =0;
            ///////////
            for(Integer s:uidList) {
                CourseInfo courseInfo =new CourseInfo();
                CourseBaseinfo courseBaseinfo =new CourseBaseinfo();
                courseInfo=courseInfoMapper.selectfromCid(s); //info ---cid
                courseBaseinfo=courseBaseinfoMapper.selectFromCourseId(s);//baseinfo---cid

                courseDataDTDS.get(flag).setCid(s);
                courseDataDTDS.get(flag).setCourseName(courseInfo.getCourseName());
                courseDataDTDS.get(flag).setTeacher(courseInfo.getTeacher());
                courseDataDTDS.get(flag).setDay(courseBaseinfo.getDay());
                courseDataDTDS.get(flag).setLessonfrom(courseBaseinfo.getLessonfrom());
                courseDataDTDS.get(flag).setLessonto(courseBaseinfo.getLessonto());
                courseDataDTDS.get(flag).setPlace(courseBaseinfo.getPlace());
                courseDataDTDS.get(flag).setWeekfrom(courseBaseinfo.getWeekfrom());
                courseDataDTDS.get(flag).setWeekto(courseBaseinfo.getWeekto());
                courseDataDTDS.get(flag).setWeektype(courseBaseinfo.getWeektype());

                flag++;

            }
            return courseDataDTDS;

        }catch (Exception e){
            courseDataDTDS.get(0).setWeektype(weektype);
            courseDataDTDS.get(0).setWeekto(weekto);
            courseDataDTDS.get(0).setWeekfrom(weekfrom);
            courseDataDTDS.get(0).setPlace(place);
            courseDataDTDS.get(0).setLessonto(lessonto);
            courseDataDTDS.get(0).setTeacher("");
            courseDataDTDS.get(0).setCid(0);
            courseDataDTDS.get(0).setDay(day);
            courseDataDTDS.get(0).setCourseName(courseName);
        }
        return courseDataDTDS;
    }


    /**
     *@Auther kiwi
     *@Data 2019/6/4
     * 获取教师名字
     @param  * @param userId
     *@reutn java.util.ArrayList<com.android.backend.dtd.TeacherAllDTD>
    */
    public ArrayList<TeacherAllDTD> getAllTeachers(String userId){

        ArrayList<TeacherAllDTD> allTeachers = new ArrayList<TeacherAllDTD>();
        try {
            ArrayList<Integer> uidList = new ArrayList<Integer>();
            uidList= userCourseMapper.selectUserId(userId);   //得到cid列

            Integer flag =0;
            for(Integer s:uidList) {
                CourseInfo courseInfo =new CourseInfo();
                courseInfo=courseInfoMapper.selectfromCid(s); //info ---cid
                allTeachers.get(flag).setCourseName(courseInfo.getCourseName());
                allTeachers.get(flag).setTeacherName(courseInfo.getTeacher());
            }
        }catch (Exception e){
            allTeachers.get(0).setCourseName("");
            allTeachers.get(0).setTeacherName("");
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
    public ArrayList<String> getAllCourseName(String userId){

        ArrayList<String> allCourses = new ArrayList<String>();
        try {
            ArrayList<Integer> uidList = new ArrayList<Integer>();
            uidList= userCourseMapper.selectUserId(userId);   //得到cid列

            for(Integer s:uidList) {
                CourseInfo courseInfo =new CourseInfo();
                courseInfo=courseInfoMapper.selectfromCid(s); //info ---cid
                allCourses.add(courseInfo.getCourseName());
            }
        }catch (Exception e){
            allCourses.add("");

        }

        return allCourses;
    }

}
