package com.nmid.cqes.service;

import com.nmid.cqes.domain.FormatData;

public interface StuService {

    //登录
    FormatData signIn(int stuId, String pwd, String lastIp);


    //返回指定学生的信息
    FormatData getStudentInfo(int stuId);


    //返回所有学生信息
    FormatData getStudentInfoList();

    //返回指定班的学生信息
    FormatData getClassStudent(String classNum);

    //返回指定学生的所有获奖 学生信息封装，奖项信息封装
    FormatData getStudentPrizes(int stuId);

    //返回指定学生的登录记录
    FormatData getStudentLoginLog(int stuId);
}
