package com.android.backend.dtd;

import lombok.Data;

/**
 * @Auther:kiwi
 * @Date: 2019/6/4 17:23
 */
@Data
public class EvaluationInfoDTD {
    private String courseName;
    private Integer evaluationScore;        //课堂评价
    private String evaluationInfo;      //评价信息
}
