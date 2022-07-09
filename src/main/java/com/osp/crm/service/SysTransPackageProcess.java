/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.crm.service;

import com.osp.crm.dao.AffTrangsDAO;
import com.osp.crm.model.AFFTransPackageView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author OSP PC
 */
@Service
@Slf4j
public class SysTransPackageProcess {

    private String configFilename = "conf/config.conf";

    static AffTrangsDAO affTrangsDAO = new AffTrangsDAO();

    static String TRANS_TIME_RELOAD_DATA = null;
    static boolean TRANS_CHECK_RELOAD_DATA_BY_CONFIG = false;

    public SysTransPackageProcess() {
        try {
            loadProperties();
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("TRANS_TIME_RELOAD_DATA=" + TRANS_TIME_RELOAD_DATA);
        log.info("TRANS_CHECK_RELOAD_DATA_BY_CONFIG=" + TRANS_CHECK_RELOAD_DATA_BY_CONFIG);
    }

        @Scheduled(cron = "0 0 1 * * ?",zone ="Asia/Bangkok")
//    @Scheduled(fixedDelay = 86400000)
    public static void sysTransPackageProcess() {
        try {
            SysTransPackageProcess packageProcess = new SysTransPackageProcess();

            List<AFFTransPackageView> aFFTransPackageViews = new ArrayList<>();
            aFFTransPackageViews = affTrangsDAO.getTransPackage(TRANS_TIME_RELOAD_DATA, TRANS_CHECK_RELOAD_DATA_BY_CONFIG);
            if (aFFTransPackageViews != null && aFFTransPackageViews.size() > 0) {
                affTrangsDAO.insertTransBatch(aFFTransPackageViews);
            }
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
                TRANS_CHECK_RELOAD_DATA_BY_CONFIG = "1".equalsIgnoreCase(properties.getProperty("TRANS_CHECK_RELOAD_DATA_BY_CONFIG"));
                TRANS_TIME_RELOAD_DATA = properties.getProperty("TRANS_TIME_RELOAD_DATA");
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
