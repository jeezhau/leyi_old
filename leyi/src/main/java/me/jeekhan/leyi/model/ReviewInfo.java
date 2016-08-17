package me.jeekhan.leyi.model;

import java.util.Date;

public class ReviewInfo {
    private Integer id;

    private String objName;

    private Integer keyId;

    private String reviewInfo;

    private Integer reviewOpr;

    private Date reviewTime;

    private String result;

    private String originalInfo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObjName() {
        return objName;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public Integer getKeyId() {
        return keyId;
    }

    public void setKeyId(Integer keyId) {
        this.keyId = keyId;
    }

    public String getReviewInfo() {
        return reviewInfo;
    }

    public void setReviewInfo(String reviewInfo) {
        this.reviewInfo = reviewInfo;
    }

    public Integer getReviewOpr() {
        return reviewOpr;
    }

    public void setReviewOpr(Integer reviewOpr) {
        this.reviewOpr = reviewOpr;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getOriginalInfo() {
        return originalInfo;
    }

    public void setOriginalInfo(String originalInfo) {
        this.originalInfo = originalInfo;
    }
}