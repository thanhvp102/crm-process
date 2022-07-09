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
public class AFFPartnerView {

    private String username;
    private String partnerName;
    private int status;
    private int type;
    private String genDate;
    private String lastUpdate;
    private String presenter;
    private int gender;
    private String dateBirth;
    private String email;
    private String shopRegion;
    private String province;
    private int pocketMoney;
    private String shopCode;
    private String shareCode;

    public AFFPartnerView() {
    }

    public AFFPartnerView(String username, String partnerName, int status, int type, String genDate, String lastUpdate, String presenter, int gender, String dateBirth, String email, String shopRegion, String province, int pocketMoney, String shopCode, String shareCode) {
        this.username = username;
        this.partnerName = partnerName;
        this.status = status;
        this.type = type;
        this.genDate = genDate;
        this.lastUpdate = lastUpdate;
        this.presenter = presenter;
        this.gender = gender;
        this.dateBirth = dateBirth;
        this.email = email;
        this.shopRegion = shopRegion;
        this.province = province;
        this.pocketMoney = pocketMoney;
        this.shopCode = shopCode;
        this.shareCode = shareCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGenDate() {
        return genDate;
    }

    public void setGenDate(String genDate) {
        this.genDate = genDate;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getPresenter() {
        return presenter;
    }

    public void setPresenter(String presenter) {
        this.presenter = presenter;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShopRegion() {
        return shopRegion;
    }

    public void setShopRegion(String shopRegion) {
        this.shopRegion = shopRegion;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getPocketMoney() {
        return pocketMoney;
    }

    public void setPocketMoney(int pocketMoney) {
        this.pocketMoney = pocketMoney;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }




}
