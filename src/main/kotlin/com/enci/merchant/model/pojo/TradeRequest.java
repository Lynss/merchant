package com.enci.merchant.model.pojo;



import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: minhuayong
 * Date: 2017/8/22
 * Time: 下午5:10
 * To change this template use File | Settings | File Templates.
 */
public  class TradeRequest implements Serializable {

    private static final long serialVersionUID = 2203067580496371853L;
    private String merId;
    private String userId;
    private String outTradeNo;
    private String payeeCusOpenid;
    private String payerCusOpenid;
    private String payeeCusName;
    private String payerCusName;
    private String orderName;
    private char orderType;
    private Integer originalAmount;
    private Integer discountAmount;
    private Integer tradeAmount;
    private String remark;
    private String notifyUrl;
    private String limitPay;
    private String pmtTag;
    private String sn;

    public String getPayerCusOpenid() {
        return payerCusOpenid;
    }

    public void setPayerCusOpenid(String payerCusOpenid) {
        this.payerCusOpenid = payerCusOpenid;
    }

    public String getPayerCusName() {
        return payerCusName;
    }

    public void setPayerCusName(String payerCusName) {
        this.payerCusName = payerCusName;
    }

    public String getPmtTag() {
        return pmtTag;
    }

    public void setPmtTag(String pmtTag) {
        this.pmtTag = pmtTag;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getPayeeCusOpenid() {
        return payeeCusOpenid;
    }

    public void setPayeeCusOpenid(String payeeCusOpenid) {
        this.payeeCusOpenid = payeeCusOpenid;
    }

    public String getPayeeCusName() {
        return payeeCusName;
    }

    public void setPayeeCusName(String payeeCusName) {
        this.payeeCusName = payeeCusName;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public char getOrderType() {
        return orderType;
    }

    public void setOrderType(char orderType) {
        this.orderType = orderType;
    }

    public Integer getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(Integer originalAmount) {
        this.originalAmount = originalAmount;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Integer getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(Integer tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getLimitPay() {
        return limitPay;
    }

    public void setLimitPay(String limitPay) {
        this.limitPay = limitPay;
    }
}
