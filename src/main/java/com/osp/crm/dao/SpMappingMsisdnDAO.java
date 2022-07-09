/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.crm.dao;

import com.osp.crm.model.SPMappingMsisdnView;
import com.osp.db.config.DatabaseORAUtility;
import com.osp.db.config.DatabaseSQLUtility;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author OSP PC
 */
@Slf4j
public class SpMappingMsisdnDAO {

    private SimpleDateFormat yyyyMMddhh24miss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     *
     * @param partitionName ==> partition(SYS_P7012)
     * @return
     */
    public void syncDataMapping(String partitionName) {
        List<SPMappingMsisdnView> sPMappingMsisdnViews = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        long timeDebug = System.currentTimeMillis();
        int i = 0;
        int countFile = 1000;
        try {
            connection = DatabaseORAUtility.getConnection();
            log.info(yyyyMMddhh24miss.format(new Date()) + "  SpMappingMsisdnDAO.getSPMappingMsisdnViewList.start query..." + partitionName);
            pstmt = connection.prepareStatement(" select msisdn, msisdn_osp, msisdn_mbf, user_name, to_char(gen_date, 'ddmmyyyy') as gen_date from SPACK.SPK_MAPPING_MSISDN " + partitionName);
            resultSet = pstmt.executeQuery();
            log.info(yyyyMMddhh24miss.format(new Date()) + "  SpMappingMsisdnDAO.getSPMappingMsisdnViewList.end query..." + partitionName + " in (" + (System.currentTimeMillis() - timeDebug) + ") ms");
            timeDebug = System.currentTimeMillis();
            while (resultSet.next()) {
                SPMappingMsisdnView sPMappingMsisdnView = new SPMappingMsisdnView(resultSet.getString("msisdn"), resultSet.getString("msisdn_osp"), resultSet.getString("msisdn_mbf"), resultSet.getString("user_name"),
                        resultSet.getString("gen_date"));
                sPMappingMsisdnViews.add(sPMappingMsisdnView);
                i++;
                if (i % 1000 == 0) {
//                    genFileCDr(sPMappingMsisdnViews, countFile);
                    insertMappingBatch(sPMappingMsisdnViews);
                    log.info(yyyyMMddhh24miss.format(new Date()) + "  SpMappingMsisdnDAO.getSPMappingMsisdnViewList add list:" + i);
                    sPMappingMsisdnViews.clear();
                    countFile = countFile + 1000;
                    Thread.sleep(100);
                }
            }
            if (sPMappingMsisdnViews != null && sPMappingMsisdnViews.size() > 0) {
//                genFileCDr(sPMappingMsisdnViews, countFile);
                insertMappingBatch(sPMappingMsisdnViews);
                countFile = countFile + 1000;
            }
            log.info(yyyyMMddhh24miss.format(new Date()) + "  SpMappingMsisdnDAO.getSPMappingMsisdnViewList return list size:" + sPMappingMsisdnViews.size() + "-" + partitionName + " in (" + (System.currentTimeMillis() - timeDebug) + ") ms");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            DatabaseORAUtility.releaseAll(connection, pstmt, resultSet);
        }
    }

    public void genFileCDr(List<SPMappingMsisdnView> sPMappingMsisdnViews, int i) {
        try {
            java.io.File f = new java.io.File("/app/spack/processor/genFile/file/Mapping_" + i + ".txt");
            java.io.FileWriter writer = new java.io.FileWriter(f, true);
            for (SPMappingMsisdnView mappingMsisdnView : sPMappingMsisdnViews) {
                writer.write(mappingMsisdnView.getMsisdn() + ";" + mappingMsisdnView.getMsisdnOsp() + ";" + mappingMsisdnView.getMsisdnMbf() + ";" + mappingMsisdnView.getUserName() + ";" + mappingMsisdnView.getGenDate()+ "\r\n");
            }
            writer.flush();
            writer.close();
            log.info(yyyyMMddhh24miss.format(new Date()) + "  genFile:/app/spack/processor/genFile/file/Mapping_" + i + ".txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Boolean insertMappingBatch(List<SPMappingMsisdnView> sPMappingMsisdnViews) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        int sizebatch = 1000;
        int rowAdd = 0;
        long timeDebug = System.currentTimeMillis();
        try {
            connection = DatabaseSQLUtility.getConnection();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(" INSERT INTO spk_mapping_msisdn( msisdn, msisdn_osp, msisdn_mbf, user_name, gen_date ) VALUES (?, ?, ?, ?, STR_TO_DATE(?, '%d%m%Y')) ");
            for (SPMappingMsisdnView mappingMsisdnView : sPMappingMsisdnViews) {
                pstmt.setString(1, mappingMsisdnView.getMsisdn());
                pstmt.setString(2, mappingMsisdnView.getMsisdnOsp());
                pstmt.setString(3, mappingMsisdnView.getMsisdnMbf());
                pstmt.setString(4, mappingMsisdnView.getUserName());
                pstmt.setString(5, mappingMsisdnView.getGenDate());
                pstmt.addBatch();
                rowAdd++;
                if (rowAdd >= sizebatch) {
                    rowAdd = 0;
                    int[] batch = pstmt.executeBatch();
                    log.info(yyyyMMddhh24miss.format(new Date()) + "  SpMappingMsisdnDAO.insertMappingBatch.executeBatch:" + rowAdd + "-" + batch.length + " in (" + (System.currentTimeMillis() - timeDebug) + ") ms");
                    timeDebug = System.currentTimeMillis();
                }
            }
            if (rowAdd > 0) {
                int[] batch = pstmt.executeBatch();
                log.info(yyyyMMddhh24miss.format(new Date()) + "  SpMappingMsisdnDAO.insertMappingBatch.executeBatch2:" + rowAdd + "-" + batch.length + " in (" + (System.currentTimeMillis() - timeDebug) + ") ms");
                timeDebug = System.currentTimeMillis();
            }
            connection.commit();
            connection.setAutoCommit(true);
            log.info(yyyyMMddhh24miss.format(new Date()) + "  SpMappingMsisdnDAO.insertMappingBatch.commit done." + " in (" + (System.currentTimeMillis() - timeDebug) + ") ms");
//            connection.setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally{
//            DatabaseSQLUtility.releaseAll(connection, pstmt, null);
        }
        return true;
    }
}
