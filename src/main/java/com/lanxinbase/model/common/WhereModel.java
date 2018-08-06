package com.lanxinbase.model.common;

import com.lanxinbase.constant.Constant;
import com.lanxinbase.model.basic.Model;

/**
 * Created by alan.luo on 2017/9/24.
 */
public class WhereModel extends Model {

    private Integer id;
    private String ids;

    private Long page;

    private Long pageSize;

    /**
     * descType:ASC|DESC
     * 目前只有用户管理使用，必须同时使用，否则会抛出异常
     */
    private String descType = "DESC";
    private String orderField;

    /**
     * 关键词，全部表通用
     */
    private String keywords;

    /**
     * 全局状态查询使用
     */
    private Integer isEffect;
    private Integer isShow;
    private Integer isDelete;
    private Integer status;
    private String statusIn;
    private Integer isRefund;
    private Integer loadType;
    private Integer isBest;
    private Integer isHot;
    private Integer isRead;
    private Integer isUse;

    /**
     * 角色ID使用
     */
    private Integer userId;
    private Integer adminId;
    private Integer pid;


    private Integer groupId;
    private Integer typeId;
    private String typeIn;
    private Integer boatId;
    private Integer cityId;
    private Integer roleId;
    private Integer navId;
    private Integer ticketId;
    private String advId;
    private Integer dateTimeId;

    /**
     * 全局手机查询使用
     */
    private String mobile;
    private String mobileIn;
    private String nickname;
    private String realName;
    private String idno;

    /**
     * 全局日期时间使用
     */
    private Integer startTime;
    private Integer endTime;
    private Integer timeType;
    private String startDate;

    /**
     * 订单支付接口表使用
     */
    private Integer orderId;
    private Integer dealId;
    private String paySn;
    private String targetSn;
    private String orderSn;
    private String pinyin;
    private String boatSn;
    private String ticketSn;
    private String apiType;
    private String paytype;


    /**
     * init
     */
    public WhereModel(){
        this.pageSize = Constant.PAGE_SIZE;
        this.setPage(1L);
    }

    /**
     *
     * @param page
     * @param pageSize 总页数
     */
    public WhereModel(long page, long pageSize){
        this.pageSize = pageSize;
        setPage(page);
    }

    public static WhereModel newInstance(){
        return new WhereModel();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = (page - 1) * pageSize;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public String getDescType() {
        return descType;
    }

    public void setDescType(String descType) {
        this.descType = descType;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getIsEffect() {
        return isEffect;
    }

    public void setIsEffect(Integer isEffect) {
        this.isEffect = isEffect;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getAdvId() {
        return advId;
    }

    public void setAdvId(String advId) {
        this.advId = advId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public String getPaySn() {
        return paySn;
    }

    public void setPaySn(String paySn) {
        this.paySn = paySn;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getIsRefund() {
        return isRefund;
    }

    public void setIsRefund(Integer isRefund) {
        this.isRefund = isRefund;
    }

    public Integer getLoadType() {
        return loadType;
    }

    public void setLoadType(Integer loadType) {
        this.loadType = loadType;
    }

    public Integer getIsBest() {
        return isBest;
    }

    public void setIsBest(Integer isBest) {
        this.isBest = isBest;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public Integer getBoatId() {
        return boatId;
    }

    public void setBoatId(Integer boatId) {
        this.boatId = boatId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getDealId() {
        return dealId;
    }

    public void setDealId(Integer dealId) {
        this.dealId = dealId;
    }

    public String getTargetSn() {
        return targetSn;
    }

    public void setTargetSn(String targetSn) {
        this.targetSn = targetSn;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getBoatSn() {
        return boatSn;
    }

    public void setBoatSn(String boatSn) {
        this.boatSn = boatSn;
    }

    public String getApiType() {
        return apiType;
    }

    public void setApiType(String apiType) {
        this.apiType = apiType;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public Integer getNavId() {
        return navId;
    }

    public void setNavId(Integer navId) {
        this.navId = navId;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketSn() {
        return ticketSn;
    }

    public void setTicketSn(String ticketSn) {
        this.ticketSn = ticketSn;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getIsUse() {
        return isUse;
    }

    public void setIsUse(Integer isUse) {
        this.isUse = isUse;
    }

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    public String getMobileIn() {
        return mobileIn;
    }

    public void setMobileIn(String mobileIn) {
        this.mobileIn = mobileIn;
    }

    public String getTypeIn() {
        return typeIn;
    }

    public void setTypeIn(String typeIn) {
        this.typeIn = typeIn;
    }

    public String getStatusIn() {
        return statusIn;
    }

    public void setStatusIn(String statusIn) {
        this.statusIn = statusIn;
    }


    public Integer getDateTimeId() {
        return dateTimeId;
    }

    public void setDateTimeId(Integer dateTimeId) {
        this.dateTimeId = dateTimeId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
