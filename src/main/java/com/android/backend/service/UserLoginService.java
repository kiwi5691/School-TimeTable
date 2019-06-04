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
 * @Date: 2019/5/28 8:42
 */
@Service
public class UserLoginService {

    @Resource
    private UserLoginMapper userLoginMapper;
    @Resource
    private StudentInfoMapper studentInfoMapper;
    @Resource
    private TeacherInfoMapper teacherInfoMapper;

    //rid , username
    public void register(int rid,String userName,String Nickname){

        try {

            StudentInfo studentInfo = new StudentInfo();
            TeacherInfo teacherInfo = new TeacherInfo();

            Date date = new Date();
            if (rid == 1) {
                studentInfo.setUserId(userName);
                studentInfo.setAddTime(date);
                studentInfo.setNickName(Nickname);
                studentInfo.setLastLoginTime(date);
                studentInfoMapper.insert(studentInfo);
            } else if (rid == 2) {
                teacherInfo.setUserId(userName);
                teacherInfo.setAddTime(date);
                teacherInfo.setLastLoginTime(date);
                teacherInfo.setNickName(userName);
                teacherInfoMapper.insert(teacherInfo);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
