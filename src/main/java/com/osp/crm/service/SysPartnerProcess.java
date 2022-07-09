/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.crm.service;

import com.osp.crm.dao.AffPartnerDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author OSP PC
 */
@Service
@Slf4j
public class SysPartnerProcess {

    static AffPartnerDAO affPartnerDAO = new AffPartnerDAO();
    private String configFilename = "conf/config.conf";

    static String PARTNER_TIME_RELOAD_DATA = null;
    static boolean PARTNER_CHECK_RELOAD_DATA_BY_CONFIG = false;

    public SysPartnerProcess() {
        try {
            loadProperties();
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("PARTNER_TIME_RELOAD_DATA=" + PARTNER_TIME_RELOAD_DATA);
        log.info("PARTNER_CHECK_RELOAD_DATA_BY_CONFIG=" + PARTNER_CHECK_RELOAD_DATA_BY_CONFIG);
    }
    @Scheduled(cron = "0 0 1 * * ?",zone ="Asia/Bangkok")
//    @Scheduled(fixedDelay = 86400000)
    public static void sysPartnerProcess() {
        try {
            SysPartnerProcess partnerProcess = new SysPartnerProcess();
            affPartnerDAO.syncPartner(PARTNER_TIME_RELOAD_DATA, PARTNER_CHECK_RELOAD_DATA_BY_CONFIG);
//            affPartnerDAO.syncPartnerAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadProperties() throws IOException {
        Properties properties = new Properties();
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader(configFilename));
            properties.load(input);
            try {
                PARTNER_CHECK_RELOAD_DATA_BY_CONFIG = "1".equalsIgnoreCase(properties.getProperty("PARTNER_CHECK_RELOAD_DATA_BY_CONFIG"));
                PARTNER_TIME_RELOAD_DATA = properties.getProperty("PARTNER_TIME_RELOAD_DATA");
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
