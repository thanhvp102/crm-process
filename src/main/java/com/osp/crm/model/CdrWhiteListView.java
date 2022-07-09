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
public class CdrWhiteListView {
    private String msisdn;
    private String msisdnId;
    private String genDate;
    private String typeWL;
    private int type;

    public CdrWhiteListView(){
    }
    
    public CdrWhiteListView(String msisdnId, String genDate, String typeWL, int type, String msisdn){
        this.msisdnId = msisdnId;
        this.genDate = genDate;
        this.typeWL = typeWL;
        this.type = type;
        this.msisdn = msisdn;
    }
    public CdrWhiteListView(String msisdnId, String typeWL, String msisdn){
        this.msisdnId = msisdnId;
        this.typeWL = typeWL;
        if(typeWL!=null && typeWL.trim().equals("C120")){
            this.type = 1;
        }
        this.msisdn = msisdn;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
    
    public String getMsisdnId() {
        return msisdnId;
    }

    public void setMsisdnId(String msisdnId) {
        this.msisdnId = msisdnId;
    }

    public String getGenDate() {
        return genDate;
    }

    public void setGenDate(String genDate) {
        this.genDate = genDate;
    }

    public String getTypeWL() {
        return typeWL;
    }

    public void setTypeWL(String typeWL) {
        this.typeWL = typeWL;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
}
