package com.android.backend.dtd;

import lombok.Data;

/**
 * @Auther:kiwi
 * @Date: 2019/6/4 17:28
 */
@Data
public class HomeWorkInfoDTD {
    private String courseName;

    private String homeworkGrade; //作业成绩

    private Integer participation;    // '出勤, 0未到，1有到',

    private Integer day; //第{}节课'
}
