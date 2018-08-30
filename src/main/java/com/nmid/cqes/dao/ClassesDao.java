package com.nmid.cqes.dao;

import com.nmid.cqes.domain.FormatData;

public interface ClassesDao {

    //返回所有班级
    //ArrayList<Classes> getClassesList();

    //返回指定班级 封装了班级所有成员
    //Classes getClasses(String classNum);

    //返回所有的班级号
    FormatData getClassList();
}
