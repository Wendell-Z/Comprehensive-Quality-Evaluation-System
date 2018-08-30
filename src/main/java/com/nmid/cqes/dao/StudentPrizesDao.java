package com.nmid.cqes.dao;

import com.nmid.cqes.domain.FormatData;

public interface StudentPrizesDao {

    //返回指定学生的所有获奖 学生信息封装，奖项信息封装
    FormatData getStudentPrizes(int stuId);
}
