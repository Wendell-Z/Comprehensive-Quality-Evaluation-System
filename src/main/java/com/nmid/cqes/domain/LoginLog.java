package com.nmid.cqes.domain;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class LoginLog {

    private int loginId;        //该登录记录在数据库中唯一ID
    private int stuId;          //学号
    private String lastIp;      //最近一次登录的IP地址
    private String lastLogin;   //最近一次登录的时间

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin.toString();
    }

    public int getLoginId() {
        return loginId;
    }

    public void setLoginId(int loginId) {
        this.loginId = loginId;
    }

    public int getStuId() {
        return stuId;
    }

    public void setStuId(int stuId) {
        this.stuId = stuId;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

}
