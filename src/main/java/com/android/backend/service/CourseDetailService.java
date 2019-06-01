package com.android.backend.service;

import com.android.backend.dao.*;
import com.android.backend.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
            userCourse.setId(userLoginMapper.selectAllByName(StudentName).getId());//学生id
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

}
