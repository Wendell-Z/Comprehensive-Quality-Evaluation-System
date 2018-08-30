package com.nmid.cqes.dao;

import com.nmid.cqes.domain.FormatData;

public interface StudentInfoDao {


    //登录
    FormatData signIn(int stuId, String pwd, String lastIp);


    //返回指定学生的信息
    FormatData getStudentInfo(int stuId);


    //返回所有学生信息
    FormatData getStudentInfoList();

    //返回指定班的学生信息
    FormatData getClassStudent(String classNum);
}
