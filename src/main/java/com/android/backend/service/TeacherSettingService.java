package com.android.backend.service;

import com.android.backend.dao.*;
import com.android.backend.domain.UserCourse;
import com.android.backend.dtd.StudentBaseDTD;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther:kiwi
 * @Date: 2019/6/6 15:40
 */
@Service
public class TeacherSettingService {

    @Resource
    private StudentInfoMapper studentInfoMapper;
    @Resource
    private CourseDetailService courseDetailService;
    @Resource
    private ClassRoomOnDutyMapper classRoomOnDutyMapper;
    @Resource
    private CourseInfoMapper courseInfoMapper;
    @Resource
    private CourseDetailMapper courseDetailMapper;
    @Resource
    private TeacherInfoMapper teacherInfoMapper;
    @Resource
    private UserCourseMapper userCourseMapper;



    public String getTeacherCourse(String userId){
        return courseInfoMapper.selectCourseNameByName(teacherInfoMapper.selectNameFromId(userId));
    }



    public List<StudentBaseDTD> getAllStudent(String userId){

        List<StudentBaseDTD> studentBaseDTDS =new ArrayList<StudentBaseDTD>();


        int cid = courseInfoMapper.selectCourseName(courseInfoMapper.selectCourseNameByName(teacherInfoMapper.selectNameFromId(userId)));

        List<UserCourse> userCourses = new ArrayList<UserCourse>();

        userCourses = userCourseMapper.selectCid(cid);
        for(UserCourse userCourse:userCourses){

            StudentBaseDTD studentBaseDTD = new StudentBaseDTD();
            studentBaseDTD.setName((studentInfoMapper.selectById(userCourse.getUid()).getNickName()));
            studentBaseDTD.setUserId(userCourse.getUid());
            studentBaseDTDS.add(studentBaseDTD);
        }
        return studentBaseDTDS;
    }

}
