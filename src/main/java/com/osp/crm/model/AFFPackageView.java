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
public class AFFPackageView {

    private String packCode;
    private Long amount;
    private String packDetail;
    private Long numberExpired;
    private Long status;
    private Double ratio;

    public AFFPackageView() {
    }

    public AFFPackageView(String packCode, Long amount, String packDetail, Long numberExpired, Long status, Double ratio) {
        this.packCode = packCode;
        this.amount = amount;
        this.packDetail = packDetail;
        this.numberExpired = numberExpired;
        this.status = status;
        this.ratio = ratio;
    }

    public String getPackCode() {
        return packCode;
    }

    public void setPackCode(String packCode) {
        this.packCode = packCode;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getPackDetail() {
        return packDetail;
    }

    public void setPackDetail(String packDetail) {
        this.packDetail = packDetail;
    }

    public Long getNumberExpired() {
        return numberExpired;
    }

    public void setNumberExpired(Long numberExpired) {
        this.numberExpired = numberExpired;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    
}
