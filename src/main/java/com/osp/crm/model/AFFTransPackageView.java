/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.crm.model;

/**
 *
 * @author OSP PC
 */
public class AFFTransPackageView {

    private String partnerName;
    private String username;
    private String msisdn;
    private String typeCtv;
    private String source;
    private String typePackage;
    private String pckCode;
    private Long amount;
    private String regTime;
    private String status;
    private String shopRegion;
    private Double ratio;
    private Long shareValue;
    private Long referValue;

    public AFFTransPackageView() {
    }

    public AFFTransPackageView(String partnerName, String username, String msisdn, String typeCtv, String source, String typePackage
    , String pckCode, Long amount, String regTime, String status, String shopRegion, Double ratio, Long shareValue, Long referValue) {
        this.partnerName = partnerName;
        this.username = username;
        this.msisdn = msisdn;
        this.typeCtv = typeCtv;
        this.source = source;
        this.typePackage = typePackage;
        this.pckCode = pckCode;
        this.amount = amount;
        this.regTime = regTime;
        this.status = status;
        this.shopRegion = shopRegion;
        this.ratio = ratio;
        this.shareValue = shareValue;
        this.referValue = referValue;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getTypeCtv() {
        return typeCtv;
    }

    public void setTypeCtv(String typeCtv) {
        this.typeCtv = typeCtv;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTypePackage() {
        return typePackage;
    }

    public void setTypePackage(String typePackage) {
        this.typePackage = typePackage;
    }

    public String getPckCode() {
        return pckCode;
    }

    public void setPckCode(String pckCode) {
        this.pckCode = pckCode;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShopRegion() {
        return shopRegion;
    }

    public void setShopRegion(String shopRegion) {
        this.shopRegion = shopRegion;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    public Long getShareValue() {
        return shareValue;
    }

    public void setShareValue(Long shareValue) {
        this.shareValue = shareValue;
    }

    public Long getReferValue() {
        return referValue;
    }

    public void setReferValue(Long referValue) {
        this.referValue = referValue;
    }


}
