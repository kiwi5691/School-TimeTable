package com.android.backend.service;

import com.android.backend.controller.user.UserController;
import com.android.backend.dao.StudentInfoMapper;
import com.android.backend.dao.TeacherInfoMapper;
import com.android.backend.dao.UserLoginMapper;
import com.android.backend.domain.StudentInfo;
import com.android.backend.domain.TeacherInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Auther:kiwi
 * @Date: 2019/5/28 8:42
 */
@Service
public class UserLoginService {


    /**
     *@Auther kiwi
     *初始化logger变量
     */
    private static Logger logger = LoggerFactory.getLogger(UserLoginService.class);


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

            String ridt=String.valueOf(rid);
            logger.info("ridddddd is",ridt);

            Date date = new Date();
            if (String.valueOf(rid).equals("1")) {
                studentInfo.setUserId(userName);
                studentInfo.setAddTime(date);
                studentInfo.setGender((byte) 1);
                studentInfo.setNickName(Nickname);
                studentInfo.setUpdateTime(date);
                studentInfo.setLastLoginTime(date);
                studentInfoMapper.insert(studentInfo);
            } else{
                teacherInfo.setUserId(userName);
                teacherInfo.setAddTime(date);
                teacherInfo.setGender((byte)1);
                teacherInfo.setLastLoginTime(date);
                teacherInfo.setUpdateTime(date);
                teacherInfo.setNickName(Nickname);
                teacherInfoMapper.insert(teacherInfo);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
