/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.crm.service;

import com.osp.crm.dao.SpMappingMsisdnDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author OSP PC
 */
@Service
@Slf4j
public class SysMappingMsisdnProcess {

    static SpMappingMsisdnDAO spMappingMsisdnDAO = new SpMappingMsisdnDAO();

    public SysMappingMsisdnProcess() {
//        try {
//            for (int i = 12; i < 44; i++) {
//                new SyncMappingThread(i + "").start();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    //@Scheduled(cron = "0 0 1 * * ?",zone ="Asia/Bangkok")
    public static void sysMappingMsisdnProcess() {
        for (int i = 12; i < 44; i++) {
            String partitionName = " partition(SYS_P70" + i + ") ";
            spMappingMsisdnDAO.syncDataMapping(partitionName);
        }
//        SysMappingMsisdnProcess mappingMsisdnProcess = new SysMappingMsisdnProcess();
    }

    class SyncMappingThread extends Thread {

        private String partitionName = "";

        public SyncMappingThread(String id) {
            this.partitionName = " partition(SYS_P70" + id + ") ";
        }

        public void run() {
            try {
                spMappingMsisdnDAO.syncDataMapping(partitionName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
