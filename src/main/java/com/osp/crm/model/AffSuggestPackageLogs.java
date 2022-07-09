package com.osp.crm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AffSuggestPackageLogs {


    private String msisdn;
    private String username;
    private String ip;
    private String moduleId;
    private String actions;
    private Long status;
    private String genDate;
    private String lastUpdated;
    private String createBy;
    private String updateBy;


}
