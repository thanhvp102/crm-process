/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.crm.service;

import com.osp.db.config.DatabaseORAUtility;
import com.osp.db.config.DatabaseSQLUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author OSP PC
 */
@Service
@Slf4j
public class ImportDataFile {

    //    public static void main1(String[] args) throws SQLException {
//        Connection connection = null;
//        PreparedStatement pstmt = null;
//        ResultSet resultSet = null;
//        try {
//            connection = DatabaseSQLUtility.getConnection();
//            pstmt = connection.prepareStatement("SELECT * FROM spk_mapping_msisdn");
//
//            log.info("The Connection Object is of Class: " + connection.getClass());
//
//            resultSet = pstmt.executeQuery();
//            while (resultSet.next()) {
//                log.info(resultSet.getString(1) + "," + resultSet.getString(2) + "," + resultSet.getString(3));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
    //@Scheduled(cron = "0 0 1 * * ?",zone ="Asia/Bangkok")
//    public static void importDataFile() throws SQLException {
//        Connection connection = null;
//        PreparedStatement pstmt = null;
//        ResultSet resultSet = null;
//        try {
//            connection = DatabaseORAUtility.getConnection();
//            pstmt = connection.prepareStatement("SELECT 1 FROM dual");
//
//            log.info("The Connection Object is of Class: " + connection.getClass());
//
//            resultSet = pstmt.executeQuery();
//            while (resultSet.next()) {
//                log.info(resultSet.getString(1) + "," + resultSet.getString(2) + "," + resultSet.getString(3));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Scheduled(cron = "0 0 * ? * *",zone ="Asia/Bangkok")
    public void checkProcess(){
        log.info("check process every hour ..............");
    }
}
