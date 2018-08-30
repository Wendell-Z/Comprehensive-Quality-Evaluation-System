package com.nmid.cqes.domain;

import org.springframework.stereotype.Component;

@Component
public class StudentInfo {
    private int ID;             //学生在数据库中唯一ID
    private int stuId;         //学生学号
    private String pwd;         //密码 身份证后六位
    private short totalScores;  //通过审核奖项的总分数
    private String stuName;     //姓名
    private String gender;      //性别
    private String college;     //学院
    private int major;          //专业代码
    private short grade;       //入学年份
    private String stuclass;    //班级号+班 形如: 01011501班
    private String classNum;    //班级号 0101501

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getStuId() {
        return stuId;
    }

    public void setStuId(int stuId) {
        this.stuId = stuId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public short getTotalScores() {
        return totalScores;
    }

    public void setTotalScores(short totalScores) {
        this.totalScores = totalScores;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public short getGrade() {
        return grade;
    }

    public void setGrade(short grade) {
        this.grade = grade;
    }

    public String getStuclass() {
        return stuclass;
    }

    public void setStuclass(String stuclass) {
        this.stuclass = stuclass;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }
}
