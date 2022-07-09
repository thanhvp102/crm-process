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
public class SPMappingMsisdnView {

    private int id;
    private String msisdn;
    private String msisdnOsp;
    private String msisdnMbf;
    private String userName;
    private String genDate;

    public SPMappingMsisdnView() {
    }

    public SPMappingMsisdnView(int id, String msisdn, String msisdnOsp, String msisdnMbf, String userName, String genDate) {
        this.id = id;
        this.msisdn = msisdn;
        this.msisdnOsp = msisdnOsp;
        this.msisdnMbf = msisdnMbf;
        this.userName = userName;
        this.genDate = genDate;
    }
    public SPMappingMsisdnView(String msisdn, String msisdnOsp, String msisdnMbf, String userName, String genDate) {
        this.msisdn = msisdn;
        this.msisdnOsp = msisdnOsp;
        this.msisdnMbf = msisdnMbf;
        this.userName = userName;
        this.genDate = genDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getMsisdnOsp() {
        return msisdnOsp;
    }

    public void setMsisdnOsp(String msisdnOsp) {
        this.msisdnOsp = msisdnOsp;
    }

    public String getMsisdnMbf() {
        return msisdnMbf;
    }

    public void setMsisdnMbf(String msisdnMbf) {
        this.msisdnMbf = msisdnMbf;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGenDate() {
        return genDate;
    }

    public void setGenDate(String genDate) {
        this.genDate = genDate;
    }

}
