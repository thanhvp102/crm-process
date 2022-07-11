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

    private  List<String>  loadPartition1() {
        List<String>  partitions=new ArrayList<>();
        partitions.add("SYNC_CDR_WHITELIST_P_PL");
        partitions.add("SYNC_CDR_WHITELIST_P_SL");
        partitions.add("SYNC_CDR_WHITELIST_P_0");
        partitions.add("SYNC_CDR_WHITELIST_P_1");
        partitions.add("SYNC_CDR_WHITELIST_P_2");
        partitions.add("SYNC_CDR_WHITELIST_P_3");
        partitions.add("SYNC_CDR_WHITELIST_P_4");
        partitions.add("SYNC_CDR_WHITELIST_P_5");


        return partitions;
    }
    private  List<String>  loadPartition2() {
        List<String>  partitions=new ArrayList<>();

        partitions.add("SYNC_CDR_WHITELIST_P_6");
        partitions.add("SYNC_CDR_WHITELIST_P_7");
        partitions.add("SYNC_CDR_WHITELIST_P_8");
        partitions.add("SYNC_CDR_WHITELIST_P_9");
        partitions.add("SYNC_CDR_WHITELIST_P_A");
        partitions.add("SYNC_CDR_WHITELIST_P_B");
        partitions.add("SYNC_CDR_WHITELIST_P_C");
        partitions.add("SYNC_CDR_WHITELIST_P_D");


        return partitions;
    }
    private  List<String>  loadPartition3() {
        List<String>  partitions=new ArrayList<>();

        partitions.add("SYNC_CDR_WHITELIST_P_E");
        partitions.add("SYNC_CDR_WHITELIST_P_F");
        partitions.add("SYNC_CDR_WHITELIST_P_G");
        partitions.add("SYNC_CDR_WHITELIST_P_H");
        partitions.add("SYNC_CDR_WHITELIST_P_I");
        partitions.add("SYNC_CDR_WHITELIST_P_J");
        partitions.add("SYNC_CDR_WHITELIST_P_K");
        partitions.add("SYNC_CDR_WHITELIST_P_L");


        return partitions;
    }
    private  List<String>  loadPartition4() {
        List<String>  partitions=new ArrayList<>();

        partitions.add("SYNC_CDR_WHITELIST_P_M");
        partitions.add("SYNC_CDR_WHITELIST_P_N");
        partitions.add("SYNC_CDR_WHITELIST_P_O");
        partitions.add("SYNC_CDR_WHITELIST_P_P");
        partitions.add("SYNC_CDR_WHITELIST_P_Q");
        partitions.add("SYNC_CDR_WHITELIST_P_R");
        partitions.add("SYNC_CDR_WHITELIST_P_S");
        partitions.add("SYNC_CDR_WHITELIST_P_T");


        return partitions;
    }
    private  List<String>  loadPartition5() {
        List<String>  partitions=new ArrayList<>();

        partitions.add("SYNC_CDR_WHITELIST_P_U");
        partitions.add("SYNC_CDR_WHITELIST_P_V");
        partitions.add("SYNC_CDR_WHITELIST_P_W");
        partitions.add("SYNC_CDR_WHITELIST_P_X");
        partitions.add("SYNC_CDR_WHITELIST_P_Y");
        partitions.add("SYNC_CDR_WHITELIST_P_Z");
        partitions.add("SYNC_CDR_WHITELIST_PL_A");
        partitions.add("SYNC_CDR_WHITELIST_PL_B");


        return partitions;
    }
    private  List<String>  loadPartition6() {
        List<String>  partitions=new ArrayList<>();

        partitions.add("SYNC_CDR_WHITELIST_PL_C");
        partitions.add("SYNC_CDR_WHITELIST_PL_D");
        partitions.add("SYNC_CDR_WHITELIST_PL_E");
        partitions.add("SYNC_CDR_WHITELIST_PL_F");
        partitions.add("SYNC_CDR_WHITELIST_PL_G");
        partitions.add("SYNC_CDR_WHITELIST_PL_H");
        partitions.add("SYNC_CDR_WHITELIST_PL_I");
        partitions.add("SYNC_CDR_WHITELIST_PL_J");

        return partitions;
    }
    private  List<String>  loadPartition7() {
        List<String>  partitions=new ArrayList<>();

        partitions.add("SYNC_CDR_WHITELIST_PL_K");
        partitions.add("SYNC_CDR_WHITELIST_PL_L");
        partitions.add("SYNC_CDR_WHITELIST_PL_M");
        partitions.add("SYNC_CDR_WHITELIST_PL_N");
        partitions.add("SYNC_CDR_WHITELIST_PL_O");
        partitions.add("SYNC_CDR_WHITELIST_PL_P");
        partitions.add("SYNC_CDR_WHITELIST_PL_Q");
        partitions.add("SYNC_CDR_WHITELIST_PL_R");


        return partitions;
    }
    private  List<String>  loadPartition8() {
        List<String>  partitions=new ArrayList<>();

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
//    private  List<String>  loadPartition() {
//        List<String>  partitions=new ArrayList<>();
//        partitions.add("SYNC_CDR_WHITELIST_P_PL");
//        partitions.add("SYNC_CDR_WHITELIST_P_SL");
//        partitions.add("SYNC_CDR_WHITELIST_P_0");
//        partitions.add("SYNC_CDR_WHITELIST_P_1");
//        partitions.add("SYNC_CDR_WHITELIST_P_2");
//        partitions.add("SYNC_CDR_WHITELIST_P_3");
//        partitions.add("SYNC_CDR_WHITELIST_P_4");
//        partitions.add("SYNC_CDR_WHITELIST_P_5");
//        partitions.add("SYNC_CDR_WHITELIST_P_6");
//        partitions.add("SYNC_CDR_WHITELIST_P_7");
//        partitions.add("SYNC_CDR_WHITELIST_P_8");
//        partitions.add("SYNC_CDR_WHITELIST_P_9");
//        partitions.add("SYNC_CDR_WHITELIST_P_A");
//        partitions.add("SYNC_CDR_WHITELIST_P_B");
//        partitions.add("SYNC_CDR_WHITELIST_P_C");
//        partitions.add("SYNC_CDR_WHITELIST_P_D");
//        partitions.add("SYNC_CDR_WHITELIST_P_E");
//        partitions.add("SYNC_CDR_WHITELIST_P_F");
//        partitions.add("SYNC_CDR_WHITELIST_P_G");
//        partitions.add("SYNC_CDR_WHITELIST_P_H");
//        partitions.add("SYNC_CDR_WHITELIST_P_I");
//        partitions.add("SYNC_CDR_WHITELIST_P_J");
//        partitions.add("SYNC_CDR_WHITELIST_P_K");
//        partitions.add("SYNC_CDR_WHITELIST_P_L");
//        partitions.add("SYNC_CDR_WHITELIST_P_M");
//        partitions.add("SYNC_CDR_WHITELIST_P_N");
//        partitions.add("SYNC_CDR_WHITELIST_P_O");
//        partitions.add("SYNC_CDR_WHITELIST_P_P");
//        partitions.add("SYNC_CDR_WHITELIST_P_Q");
//        partitions.add("SYNC_CDR_WHITELIST_P_R");
//        partitions.add("SYNC_CDR_WHITELIST_P_S");
//        partitions.add("SYNC_CDR_WHITELIST_P_T");
//        partitions.add("SYNC_CDR_WHITELIST_P_U");
//        partitions.add("SYNC_CDR_WHITELIST_P_V");
//        partitions.add("SYNC_CDR_WHITELIST_P_W");
//        partitions.add("SYNC_CDR_WHITELIST_P_X");
//        partitions.add("SYNC_CDR_WHITELIST_P_Y");
//        partitions.add("SYNC_CDR_WHITELIST_P_Z");
//        partitions.add("SYNC_CDR_WHITELIST_PL_A");
//        partitions.add("SYNC_CDR_WHITELIST_PL_B");
//        partitions.add("SYNC_CDR_WHITELIST_PL_C");
//        partitions.add("SYNC_CDR_WHITELIST_PL_D");
//        partitions.add("SYNC_CDR_WHITELIST_PL_E");
//        partitions.add("SYNC_CDR_WHITELIST_PL_F");
//        partitions.add("SYNC_CDR_WHITELIST_PL_G");
//        partitions.add("SYNC_CDR_WHITELIST_PL_H");
//        partitions.add("SYNC_CDR_WHITELIST_PL_I");
//        partitions.add("SYNC_CDR_WHITELIST_PL_J");
//        partitions.add("SYNC_CDR_WHITELIST_PL_K");
//        partitions.add("SYNC_CDR_WHITELIST_PL_L");
//        partitions.add("SYNC_CDR_WHITELIST_PL_M");
//        partitions.add("SYNC_CDR_WHITELIST_PL_N");
//        partitions.add("SYNC_CDR_WHITELIST_PL_O");
//        partitions.add("SYNC_CDR_WHITELIST_PL_P");
//        partitions.add("SYNC_CDR_WHITELIST_PL_Q");
//        partitions.add("SYNC_CDR_WHITELIST_PL_R");
//        partitions.add("SYNC_CDR_WHITELIST_PL_S");
//        partitions.add("SYNC_CDR_WHITELIST_PL_T");
//        partitions.add("SYNC_CDR_WHITELIST_PL_U");
//        partitions.add("SYNC_CDR_WHITELIST_PL_V");
//        partitions.add("SYNC_CDR_WHITELIST_PL_W");
//        partitions.add("SYNC_CDR_WHITELIST_PL_X");
//        partitions.add("SYNC_CDR_WHITELIST_PL_Y");
//        partitions.add("SYNC_CDR_WHITELIST_PL_Z");
//        return partitions;
//    }

    //ngay 25 hang thang
    //@Scheduled(cron = "0 0 23 25 * ?",zone ="Asia/Bangkok")

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
    public void process(List<String> partitions){
        try {
            while (partitions.size()!=0) {
                log.info("sysWhiteListProcess " + partitions.get(0));
                cdrWhitelistDAO.syncCdrByPartitionConfig(partitions.get(0), CDR_WHITELIST,partitions);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 17 * * ?",zone ="Asia/Bangkok")
    public void sysWhiteListProcess1() {
        try {
            log.info("sysWhiteListProcess1----------");
            loadProperties();
            List<String> partitions =loadPartition1();
            process(partitions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 18 * * ?",zone ="Asia/Bangkok")
    public void sysWhiteListProcess2() {
        try {
            log.info("sysWhiteListProcess2----------");
            loadProperties();
            List<String> partitions =loadPartition2();

            process(partitions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 19 * * ?",zone ="Asia/Bangkok")
    public void sysWhiteListProcess3() {
        try {
            log.info("sysWhiteListProcess3----------");
            loadProperties();
            List<String> partitions =loadPartition3();

            process(partitions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 20 * * ?",zone ="Asia/Bangkok")
    public void sysWhiteListProcess4() {
        try {
            log.info("sysWhiteListProcess4----------");
            loadProperties();
            List<String> partitions =loadPartition4();

            process(partitions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 21 * * ?",zone ="Asia/Bangkok")
    public void sysWhiteListProcess5() {
        try {
            log.info("sysWhiteListProcess5----------");
            loadProperties();
            List<String> partitions =loadPartition5();

            process(partitions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 22 * * ?",zone ="Asia/Bangkok")
    public void sysWhiteListProcess6() {
        try {
            log.info("sysWhiteListProcess6----------");
            loadProperties();
            List<String> partitions =loadPartition6();

            process(partitions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 23 * * ?",zone ="Asia/Bangkok")
    public void sysWhiteListProcess7() {
        try {
            log.info("sysWhiteListProcess7----------");
            loadProperties();
            List<String> partitions =loadPartition7();


            process(partitions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0 1 * * ?",zone ="Asia/Bangkok")
    public void sysWhiteListProcess8() {
        try {
            log.info("sysWhiteListProcess8----------");
            loadProperties();
            List<String> partitions =loadPartition8();
            process(partitions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
