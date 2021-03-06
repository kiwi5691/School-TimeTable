package com.android.backend.service;

import com.android.backend.dao.StudentInfoMapper;
import com.android.backend.dao.TeacherInfoMapper;
import com.android.backend.dao.UserLoginMapper;
import com.android.backend.domain.StudentInfo;
import com.android.backend.domain.TeacherInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Auther:kiwi
 * @Date: 2019/5/30 16:40
 */
@Service
public class UserInfoService {

    @Resource
    private StudentInfoMapper studentInfoMapper;
    @Resource
    private TeacherInfoMapper teacherInfoMapper;
    @Resource
    private UserLoginMapper userLoginMapper;

    public StudentInfo ShowStudent(String UserId){

        try {
             return studentInfoMapper.selectById(UserId);
        }catch (Exception e) {
            return null;
        }
    }

    public TeacherInfo ShowTeacher(String UserId){
        try {
            return teacherInfoMapper.selectById(UserId);
        }catch (Exception e){
            return null;
        }
    }

    public boolean UpdateStudentInfo(StudentInfo studentInfo){

        try {
            studentInfoMapper.updateByUserId(studentInfo);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean UpdateTeacherInfo(TeacherInfo teacherInfo){

        try {
            teacherInfoMapper.updateByUserId(teacherInfo);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public void UpdateLastLoginTime(String userId,int rid){
        if(rid==1){
            Date date = new Date();
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setUserId(userId);
            studentInfo.setLastLoginTime(date);
            studentInfoMapper.updateLoginTime(studentInfo);
        }
        else {
            Date date = new Date();
            TeacherInfo teacherInfo = new TeacherInfo();
            teacherInfo.setUserId(userId);
            teacherInfo.setLastLoginTime(date);
            teacherInfoMapper.updateLoginTime(teacherInfo);
        }
    }

}
