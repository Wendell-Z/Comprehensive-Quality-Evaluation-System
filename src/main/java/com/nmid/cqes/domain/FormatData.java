package com.nmid.cqes.domain;

import org.springframework.stereotype.Component;

@Component
public class FormatData<T> {
    private final static String SERVER_CODE_500 = "服务器内部错误，无法完成请求";
    private String status;  //请求响应的状态码
    private String info;    //响应概述
    private T data;         //具体信息

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{\"status\":\"" + this.status + "\",\"info\":\"" + this.info + "\",\"data\":\"" + this.data + "\"}";
    }

    public void set500() {
        this.setStatus("500");
        this.setInfo("Internal Server Error");
        this.setData((T) SERVER_CODE_500);
    }

    public void set200(T data) {
        this.setStatus("200");
        this.setInfo("successfully");
        this.setData(data);
    }
}
