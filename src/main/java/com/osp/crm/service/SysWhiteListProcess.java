/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.crm.service;

import com.osp.crm.dao.CdrWhitelistDAO;
import com.osp.crm.model.CdrWhiteListView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author OSP PC
 */
@Service
@Slf4j
public class SysWhiteListProcess {

    static CdrWhitelistDAO cdrWhitelistDAO = new CdrWhitelistDAO();

    private  List<String>  loadPartition() {
        List<String>  partitions=new ArrayList<>();
        partitions.add("SYNC_CDR_WHITELIST_P_PL");
        partitions.add("SYNC_CDR_WHITELIST_P_SL");
        partitions.add("SYNC_CDR_WHITELIST_P_0");
        partitions.add("SYNC_CDR_WHITELIST_P_1");
        partitions.add("SYNC_CDR_WHITELIST_P_2");
        partitions.add("SYNC_CDR_WHITELIST_P_3");
        partitions.add("SYNC_CDR_WHITELIST_P_4");
        partitions.add("SYNC_CDR_WHITELIST_P_5");
        partitions.add("SYNC_CDR_WHITELIST_P_6");
        partitions.add("SYNC_CDR_WHITELIST_P_7");
        partitions.add("SYNC_CDR_WHITELIST_P_8");
        partitions.add("SYNC_CDR_WHITELIST_P_9");
        partitions.add("SYNC_CDR_WHITELIST_P_A");
        partitions.add("SYNC_CDR_WHITELIST_P_B");
        partitions.add("SYNC_CDR_WHITELIST_P_C");
        partitions.add("SYNC_CDR_WHITELIST_P_D");
        partitions.add("SYNC_CDR_WHITELIST_P_E");
        partitions.add("SYNC_CDR_WHITELIST_P_F");
        partitions.add("SYNC_CDR_WHITELIST_P_G");
        partitions.add("SYNC_CDR_WHITELIST_P_H");
        partitions.add("SYNC_CDR_WHITELIST_P_I");
        partitions.add("SYNC_CDR_WHITELIST_P_J");
        partitions.add("SYNC_CDR_WHITELIST_P_K");
        partitions.add("SYNC_CDR_WHITELIST_P_L");
        partitions.add("SYNC_CDR_WHITELIST_P_M");
        partitions.add("SYNC_CDR_WHITELIST_P_N");
        partitions.add("SYNC_CDR_WHITELIST_P_O");
        partitions.add("SYNC_CDR_WHITELIST_P_P");
        partitions.add("SYNC_CDR_WHITELIST_P_Q");
        partitions.add("SYNC_CDR_WHITELIST_P_R");
        partitions.add("SYNC_CDR_WHITELIST_P_S");
        partitions.add("SYNC_CDR_WHITELIST_P_T");
        partitions.add("SYNC_CDR_WHITELIST_P_U");
        partitions.add("SYNC_CDR_WHITELIST_P_V");
        partitions.add("SYNC_CDR_WHITELIST_P_W");
        partitions.add("SYNC_CDR_WHITELIST_P_X");
        partitions.add("SYNC_CDR_WHITELIST_P_Y");
        partitions.add("SYNC_CDR_WHITELIST_P_Z");
        partitions.add("SYNC_CDR_WHITELIST_PL_A");
        partitions.add("SYNC_CDR_WHITELIST_PL_B");
        partitions.add("SYNC_CDR_WHITELIST_PL_C");
        partitions.add("SYNC_CDR_WHITELIST_PL_D");
        partitions.add("SYNC_CDR_WHITELIST_PL_E");
        partitions.add("SYNC_CDR_WHITELIST_PL_F");
        partitions.add("SYNC_CDR_WHITELIST_PL_G");
        partitions.add("SYNC_CDR_WHITELIST_PL_H");
        partitions.add("SYNC_CDR_WHITELIST_PL_I");
        partitions.add("SYNC_CDR_WHITELIST_PL_J");
        partitions.add("SYNC_CDR_WHITELIST_PL_K");
        partitions.add("SYNC_CDR_WHITELIST_PL_L");
        partitions.add("SYNC_CDR_WHITELIST_PL_M");
        partitions.add("SYNC_CDR_WHITELIST_PL_N");
        partitions.add("SYNC_CDR_WHITELIST_PL_O");
        partitions.add("SYNC_CDR_WHITELIST_PL_P");
        partitions.add("SYNC_CDR_WHITELIST_PL_Q");
        partitions.add("SYNC_CDR_WHITELIST_PL_R");
        partitions.add("SYNC_CDR_WHITELIST_PL_S");
        partitions.add("SYNC_CDR_WHITELIST_PL_T");
        partitions.add("SYNC_CDR_WHITELIST_PL_U");
        partitions.add("SYNC_CDR_WHITELIST_PL_V");
        partitions.add("SYNC_CDR_WHITELIST_PL_W");
        partitions.add("SYNC_CDR_WHITELIST_PL_X");
        partitions.add("SYNC_CDR_WHITELIST_PL_Y");
        partitions.add("SYNC_CDR_WHITELIST_PL_Z");
        return partitions;
    }

    //@Scheduled(cron = "0 0 23 25 * ?",zone ="Asia/Bangkok")
    @Scheduled(fixedDelay = 864000000)
    public void sysWhiteListProcess() {
        try {
//            List<CdrWhiteListView> cdrWhiteListViews =  cdrWhitelistDAO.syncDataWhiteList();
//            if(cdrWhiteListViews!=null && cdrWhiteListViews.size()>0){
//                cdrWhitelistDAO.insertCdrWhiteListBatch(cdrWhiteListViews);
//            }
            loadProperties();
            List<String> partitions =loadPartition();

            SimpleDateFormat yyyyMM = new SimpleDateFormat("yyyyMM");
            String configWhiteList = CDR_WHITELIST;
            configWhiteList = ("".equals(configWhiteList)) ? yyyyMM.format(new Date()) : configWhiteList;
            try {
                while (partitions.size()!=0) {
                    log.info("sysWhiteListProcess " + partitions.get(0));
                    List<CdrWhiteListView> cdrWhiteListViews = cdrWhitelistDAO.syncCdrByPartitionConfig(partitions.get(0), CDR_WHITELIST);
                    String insertWhiteList = "INSERT INTO cdr_whitelist_" + configWhiteList + "(msisdn_id, gen_date, type_wl, type, msisdn ) VALUES (?, CURDATE() , ?, ?, ? )";
                    log.info(insertWhiteList);
                    if (cdrWhiteListViews != null && cdrWhiteListViews.size() > 0) {
                        cdrWhitelistDAO.insertCdrWhiteListBatchConfig(insertWhiteList, cdrWhiteListViews,partitions);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String CDR_WHITELIST = null;

    private static void loadProperties() throws IOException {
        String configFilename = "conf/config.conf";
        Properties properties = new Properties();
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader(configFilename));
            properties.load(input);
            try {
                CDR_WHITELIST = properties.getProperty("CDR_WHITELIST");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
