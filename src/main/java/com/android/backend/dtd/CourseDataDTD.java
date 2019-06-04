package com.android.backend.dtd;

import lombok.Data;

/**
 * @Auther:kiwi
 * @Date: 2019/6/4 14:52
 */
@Data
public class CourseDataDTD {
    private int cid;		// 课程标记id
    private String courseName; //课程名
    private String teacher;		// 教师
    private int weekfrom;	// 起始周
    private int weekto;		// 结束周
    private int weektype;	// 周类型：1普通；2单周；3双周
    private String  day;			// 星期几上课
    private int lessonfrom;	// 开始节次
    private int lessonto;	// 结束节次
    private String place;	//地点
}
