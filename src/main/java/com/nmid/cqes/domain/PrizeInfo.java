package com.nmid.cqes.domain;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class PrizeInfo implements Comparable {
    private int prizeId;        //该条奖项在数据库中唯一ID
    private int stuId;          //学号
    private String prizeName;   //奖项名称
    private int typeCode;       //奖项类别
    private byte score;         //奖项对应分数
    private String materialUrl; //奖项证明材料链接
    private byte status;        //奖项目前审核状态码
    private String lastUpdate;//该条目最近一次更新的时间

    public int getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(int typeCode) {
        this.typeCode = typeCode;
    }

    public int getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(int prizeId) {
        this.prizeId = prizeId;
    }

    public int getStuId() {
        return stuId;
    }

    public void setStuId(int stuId) {
        this.stuId = stuId;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public byte getScore() {
        return score;
    }

    public void setScore(byte score) {
        this.score = score;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate.toString();
    }

    public String getMaterialUrl() {
        return materialUrl;
    }

    public void setMaterialUrl(String materialUrl) {
        this.materialUrl = materialUrl;
    }


    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }


    @Override
    public int compareTo(Object o) {
        PrizeInfo prizeInfo = (PrizeInfo) o;
        if (this.typeCode >= prizeInfo.typeCode) {
            return 1;
        }
        return -1;
    }
}
