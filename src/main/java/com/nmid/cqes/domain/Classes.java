package com.nmid.cqes.domain;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Classes {
    private int classId;        //该班级在数据库中唯一ID
    private String college;     //学院
    private String classNum;    //班级代码
    private int major;          //专业代码
    //该班级所有的学生 (包含若干条StudentInfo的List集合)
    private ArrayList<StudentInfo> studentInfos;

    public ArrayList<StudentInfo> getStudentInfos() {
        return studentInfos;
    }

    public void setStudentInfos(ArrayList<StudentInfo> studentInfos) {
        this.studentInfos = studentInfos;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }
}
