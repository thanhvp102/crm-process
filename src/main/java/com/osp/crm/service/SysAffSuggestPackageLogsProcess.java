package com.osp.crm.service;

import com.osp.crm.dao.AffSuggestPackageLogDAO;
import com.osp.crm.model.AFFTransPackageView;
import com.osp.crm.model.AffSuggestPackageLogs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
@Service
@Slf4j
public class SysAffSuggestPackageLogsProcess {

    private String configFilename = "conf/config.conf";

    static AffSuggestPackageLogDAO affSuggestPackageDAO = new AffSuggestPackageLogDAO();

    static String SUGGEST_PACKAGE_TIME_RELOAD_DATA = null;
    static boolean SUGGEST_PACKAGE_CHECK_RELOAD_DATA_BY_CONFIG = false;

    public SysAffSuggestPackageLogsProcess() {
        try {
            loadProperties();
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("SUGGEST_PACKAGE_TIME_RELOAD_DATA=" + SUGGEST_PACKAGE_TIME_RELOAD_DATA);
        log.info("SUGGEST_PACKAGE_CHECK_RELOAD_DATA_BY_CONFIG=" + SUGGEST_PACKAGE_CHECK_RELOAD_DATA_BY_CONFIG);
    }

    @Scheduled(cron = "0 0 1 * * ?",zone ="Asia/Bangkok")
//    @Scheduled(fixedDelay = 86400000)
    public static void sysAffSuggestPackageLogsProcess() {
        try {

            List<AffSuggestPackageLogs> affSuggestPackageLogs = new ArrayList<>();
            affSuggestPackageLogs = affSuggestPackageDAO.getAffSuggestPackageLogs(SUGGEST_PACKAGE_TIME_RELOAD_DATA, SUGGEST_PACKAGE_CHECK_RELOAD_DATA_BY_CONFIG);
            if (affSuggestPackageLogs != null && affSuggestPackageLogs.size() > 0) {
                affSuggestPackageDAO.insertAffSuggestPackageLogsBatch(affSuggestPackageLogs);
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
                SUGGEST_PACKAGE_CHECK_RELOAD_DATA_BY_CONFIG = "1".equalsIgnoreCase(properties.getProperty("SUGGEST_PACKAGE_CHECK_RELOAD_DATA_BY_CONFIG"));
                SUGGEST_PACKAGE_TIME_RELOAD_DATA = properties.getProperty("SUGGEST_PACKAGE_TIME_RELOAD_DATA");
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
