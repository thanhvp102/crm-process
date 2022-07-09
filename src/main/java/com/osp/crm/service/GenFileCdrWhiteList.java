/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.crm.service;

import com.osp.crm.dao.CdrWhitelistDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author OSP PC
*/
@Service
@Slf4j
public class GenFileCdrWhiteList {

    static CdrWhitelistDAO cdrWhitelistDAO = new CdrWhitelistDAO();
    private static List<String> partitions = new ArrayList();
    static {
        partitions.add("CDR_WHITELIST_P_PL");
        partitions.add("CDR_WHITELIST_P_SL");
        partitions.add("CDR_WHITELIST_P_0");
        partitions.add("CDR_WHITELIST_P_1");
        partitions.add("CDR_WHITELIST_P_2");
        partitions.add("CDR_WHITELIST_P_3");
        partitions.add("CDR_WHITELIST_P_4");
        partitions.add("CDR_WHITELIST_P_5");
        partitions.add("CDR_WHITELIST_P_6");
        partitions.add("CDR_WHITELIST_P_7");
        partitions.add("CDR_WHITELIST_P_8");
        partitions.add("CDR_WHITELIST_P_9");
        partitions.add("CDR_WHITELIST_P_A");
        partitions.add("CDR_WHITELIST_P_B");
        partitions.add("CDR_WHITELIST_P_C");
        partitions.add("CDR_WHITELIST_P_D");
        partitions.add("CDR_WHITELIST_P_E");
        partitions.add("CDR_WHITELIST_P_F");
        partitions.add("CDR_WHITELIST_P_G");
        partitions.add("CDR_WHITELIST_P_H");
        partitions.add("CDR_WHITELIST_P_I");
        partitions.add("CDR_WHITELIST_P_J");
        partitions.add("CDR_WHITELIST_P_K");
        partitions.add("CDR_WHITELIST_P_L");
        partitions.add("CDR_WHITELIST_P_M");
        partitions.add("CDR_WHITELIST_P_N");
        partitions.add("CDR_WHITELIST_P_O");
        partitions.add("CDR_WHITELIST_P_P");
        partitions.add("CDR_WHITELIST_P_Q");
        partitions.add("CDR_WHITELIST_P_R");
        partitions.add("CDR_WHITELIST_P_S");
        partitions.add("CDR_WHITELIST_P_T");
        partitions.add("CDR_WHITELIST_P_U");
        partitions.add("CDR_WHITELIST_P_V");
        partitions.add("CDR_WHITELIST_P_W");
        partitions.add("CDR_WHITELIST_P_X");
        partitions.add("CDR_WHITELIST_P_Y");
        partitions.add("CDR_WHITELIST_P_Z");
        partitions.add("CDR_WHITELIST_PL_A");
        partitions.add("CDR_WHITELIST_PL_B");
        partitions.add("CDR_WHITELIST_PL_C");
        partitions.add("CDR_WHITELIST_PL_D");
        partitions.add("CDR_WHITELIST_PL_E");
        partitions.add("CDR_WHITELIST_PL_F");
        partitions.add("CDR_WHITELIST_PL_G");
        partitions.add("CDR_WHITELIST_PL_H");
        partitions.add("CDR_WHITELIST_PL_I");
        partitions.add("CDR_WHITELIST_PL_J");
        partitions.add("CDR_WHITELIST_PL_K");
        partitions.add("CDR_WHITELIST_PL_L");
        partitions.add("CDR_WHITELIST_PL_M");
        partitions.add("CDR_WHITELIST_PL_N");
        partitions.add("CDR_WHITELIST_PL_O");
        partitions.add("CDR_WHITELIST_PL_P");
        partitions.add("CDR_WHITELIST_PL_Q");
        partitions.add("CDR_WHITELIST_PL_R");
        partitions.add("CDR_WHITELIST_PL_S");
        partitions.add("CDR_WHITELIST_PL_T");
        partitions.add("CDR_WHITELIST_PL_U");
        partitions.add("CDR_WHITELIST_PL_V");
        partitions.add("CDR_WHITELIST_PL_W");
        partitions.add("CDR_WHITELIST_PL_X");
        partitions.add("CDR_WHITELIST_PL_Y");
        partitions.add("CDR_WHITELIST_PL_Z");
    }

    //@Scheduled(cron = "0 0 1 * * ?",zone ="Asia/Bangkok")
    public static void genFileCdrWhiteList() {
        try {
            for(int i=0; i< partitions.size(); i++ ){
                log.info("genFileCdrWhiteList "+partitions.get(i));
                cdrWhitelistDAO.syncGenFileCdrByPartition(partitions.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
