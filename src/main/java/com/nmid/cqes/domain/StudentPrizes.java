package com.nmid.cqes.domain;

import org.springframework.stereotype.Component;

import java.util.TreeSet;

@Component
public class StudentPrizes {

    private int ID;                         //学生在数据库中唯一ID
    private int stuId;                      //学号
    private short totalScores;              //通过审核奖项总分
    private String stuName;                 //姓名
    private TreeSet<PrizeInfo> prizeInfos;  //该学生的所有上传的奖项信息 (一个包含若干条prizeInfo的List集合)

    public TreeSet<PrizeInfo> getPrizeInfos() {
        return prizeInfos;
    }

    public void setPrizeInfos(TreeSet<PrizeInfo> prizeInfos) {
        this.prizeInfos = prizeInfos;
    }

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

}
