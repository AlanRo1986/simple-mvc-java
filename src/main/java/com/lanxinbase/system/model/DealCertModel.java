package com.lanxinbase.system.model;

/**
 * Created by alan on 2018/7/22.
 */
public class DealCertModel {
    private Integer id;
    private String dealCover;
    private String avatar;
    private String nickname;
    private String dealName;
    private String remark;
    private String loadCreateTime;
    private String loadType;
    private String loadMoney;
    private String dealTimes;
    private String dealQrCode;
    private boolean isSuccess;

    public DealCertModel(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDealCover() {
        return dealCover;
    }

    public void setDealCover(String dealCover) {
        this.dealCover = dealCover;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLoadCreateTime() {
        return loadCreateTime;
    }

    public void setLoadCreateTime(String loadCreateTime) {
        this.loadCreateTime = loadCreateTime;
    }

    public String getLoadType() {
        return loadType;
    }

    public void setLoadType(String loadType) {
        this.loadType = loadType;
    }

    public String getLoadMoney() {
        return loadMoney;
    }

    public void setLoadMoney(String loadMoney) {
        this.loadMoney = loadMoney;
    }

    public String getDealTimes() {
        return dealTimes;
    }

    public void setDealTimes(String dealTimes) {
        this.dealTimes = dealTimes;
    }

    public String getDealQrCode() {
        return dealQrCode;
    }

    public void setDealQrCode(String dealQrCode) {
        this.dealQrCode = dealQrCode;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
