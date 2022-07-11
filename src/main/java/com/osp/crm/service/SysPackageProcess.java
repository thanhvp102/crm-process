/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.crm.service;

import com.osp.crm.dao.AffPackageDAO;
import com.osp.crm.model.AFFPackageView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author OSP PC
 */
@Service
@Slf4j
public class SysPackageProcess {

    static AffPackageDAO affPackageDAO = new AffPackageDAO();

        @Scheduled(cron = "0 0 1 * * ?",zone ="Asia/Bangkok")
//    @Scheduled(fixedDelay = 86400000)
    public static void SysPackageProcess() {
        try {
            List<AFFPackageView> aFFPackageViews = new ArrayList<>();
            aFFPackageViews = affPackageDAO.getListPackage();
            if (aFFPackageViews != null && aFFPackageViews.size() > 0) {
                affPackageDAO.insertPackageBatch(aFFPackageViews);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
