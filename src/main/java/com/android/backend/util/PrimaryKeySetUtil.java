package com.android.backend.util;

import com.android.backend.dao.CourseInfoMapper;
import com.android.backend.domain.CourseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Auther:kiwi
 * @Date: 2019/6/5 19:28
 */
@Component
public class PrimaryKeySetUtil {


    @Autowired
    private static CourseInfoMapper courseInfoMapper;


    @PostConstruct
    public static Integer setKey(){

        CourseInfo courseInfo = new CourseInfo();
        int rand=0;
        boolean repeat = false;
        while (repeat == false) {
            rand = (int) ((Math.random() * 9 + 1) * 100000);
            courseInfo.setId(rand);
            try{
                courseInfoMapper.findId(courseInfo);
            } catch (Exception r){
                repeat = true;
            }
        }
        return rand;
    }
}
