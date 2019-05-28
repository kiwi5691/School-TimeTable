package com.android.backend.service;

import com.android.backend.dao.StudentInfoMapper;
import com.android.backend.dao.TeacherInfoMapper;
import com.android.backend.dao.UserLoginMapper;
import com.android.backend.domain.StudentInfo;
import com.android.backend.domain.TeacherInfo;
import com.android.backend.domain.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther:kiwi
 * @Date: 2019/5/28 8:42
 */
@Service
public class TimetableUserInfo {

    @Resource
    private UserLoginMapper userLoginMapper;
    @Resource
    private StudentInfoMapper studentInfoMapper;
    @Resource
    private TeacherInfoMapper teacherInfoMapper;
    @Resource
    private StudentInfo studentInfo;
    @Resource
    private TeacherInfo teacherInfo;
    //rid , username
    public void register(int rid,String userName){

        if(rid==1){
            studentInfo.setNickName(userName);
            studentInfoMapper.insert(studentInfo);
        }
        else if(rid==2){
            teacherInfo.setNickName(userName);
            teacherInfoMapper.insert(teacherInfo);
        }
    }
}
