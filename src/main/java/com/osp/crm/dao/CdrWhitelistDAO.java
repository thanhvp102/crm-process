/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.crm.dao;

import com.osp.crm.model.CdrWhiteListView;
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
public class CdrWhitelistDAO {
    
    private SimpleDateFormat yyyyMMddhh24miss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat yyyyMM = new SimpleDateFormat("yyyyMM");

    public List<CdrWhiteListView> syncDataWhiteList() {
        List<CdrWhiteListView> cdrWhiteListViews = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        long timeDebug = System.currentTimeMillis();
        try {
            connection = DatabaseORAUtility.getConnection();
            pstmt = connection.prepareStatement(" select msisdn_id, to_char(gen_date, 'ddmmyyyy'), TYPE_WL from cdr_whitelist_202205 ");
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
//                CdrWhiteListView cdrWhiteListView = new CdrWhiteListView(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(3).equals("C120") ? 1 : 0);
//                cdrWhiteListViews.add(cdrWhiteListView);
            }
            log.info(yyyyMMddhh24miss.format(new Date()) + "  CdrWhitelistDAO.syncDataWhiteList size:" + cdrWhiteListViews.size() + " in (" + (System.currentTimeMillis() - timeDebug) + " ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cdrWhiteListViews;
    }
    
    
    public Boolean syncGenFileCdrByPartition(String partitionName) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        long timeDebug = System.currentTimeMillis();
        List<CdrWhiteListView> cdrWhiteListViews = new ArrayList<>();
        int i = 0;
        int countFile = 100000;
        try {
            connection = DatabaseORAUtility.getConnection();
            pstmt = connection.prepareStatement(" select msisdn_id, type_wl, msisdn from cdr_whiteList_"+yyyyMM.format(new Date())+" partition("+partitionName+") ");
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                CdrWhiteListView cdrWhiteListView = new CdrWhiteListView(resultSet.getString(1), yyyyMMdd.format(new Date()), resultSet.getString(2), resultSet.getString(2).equals("C120") ? 1 : 0, resultSet.getString(3));
                cdrWhiteListViews.add(cdrWhiteListView);
                i++;
                if (i % 100000 == 0) {
                    genFileCDr(cdrWhiteListViews, countFile);
//                    insertCdrWhiteListBatch(cdrWhiteListViews);
                    log.info(yyyyMMddhh24miss.format(new Date()) + "  SpMappingMsisdnDAO.syncCdrByPartition add list:" + i);
                    cdrWhiteListViews.clear();
                    countFile = countFile + 100000;
                    Thread.sleep(100);
                }
            }
            if (cdrWhiteListViews != null && cdrWhiteListViews.size() > 0) {
                genFileCDr(cdrWhiteListViews, countFile);
//                insertCdrWhiteListBatch(cdrWhiteListViews);
                countFile = countFile + 100000;
            }
            
            log.info(yyyyMMddhh24miss.format(new Date()) + "  CdrWhitelistDAO.syncDataWhiteList size:" + cdrWhiteListViews.size() + " - " +countFile+ " in (" + (System.currentTimeMillis() - timeDebug) + " ms");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    public void genFileCDr(List<CdrWhiteListView> cdrWhiteListViews, int i) {
        try {
            java.io.File f = new java.io.File("/app/spack/processor/genFile/file_C120/WhiteList_" + i + ".txt");
            java.io.FileWriter writer = new java.io.FileWriter(f, true);
            for (CdrWhiteListView cdrWhiteListView : cdrWhiteListViews) {
                writer.write(cdrWhiteListView.getMsisdnId()+ ";" + cdrWhiteListView.getTypeWL()+ ";" + cdrWhiteListView.getMsisdn()+ "\n");
            }
            writer.flush();
            writer.close();
            log.info(yyyyMMddhh24miss.format(new Date()) + "  genFile:/app/spack/processor/genFile/file/Mapping_" + i + ".txt");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    

    public Boolean insertCdrWhiteListBatch(List<CdrWhiteListView> cdrWhiteListViews) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        int sizebatch = 1000;
        int rowAdd = 0;
        long timeDebug = System.currentTimeMillis();
        try {
            connection = DatabaseSQLUtility.getConnection();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(" INSERT INTO cdr_whitelist_202205_2(msisdn_id, gen_date, type_wl, type, msisdn ) VALUES (?, CURDATE() , ?, ?, ? ) ");
            for (CdrWhiteListView cdrWhiteListView : cdrWhiteListViews) {
                pstmt.setString(1, cdrWhiteListView.getMsisdnId());
                pstmt.setString(2, cdrWhiteListView.getTypeWL());
                pstmt.setInt(3, cdrWhiteListView.getType());
                pstmt.setString(4, cdrWhiteListView.getMsisdn());
                pstmt.addBatch();
                rowAdd++;
                if (rowAdd >= sizebatch) {
                    rowAdd = 0;
                    int[] batch = pstmt.executeBatch();
                    log.info(yyyyMMddhh24miss.format(new Date()) + "  SpMappingMsisdnDAO.insertCdrWhiteListBatch.executeBatch:" + rowAdd + "-" + batch.length + " in (" + (System.currentTimeMillis() - timeDebug) + " ms");
                    timeDebug = System.currentTimeMillis();
                }
            }
            if (rowAdd > 0) {
                int[] batch = pstmt.executeBatch();
                log.info(yyyyMMddhh24miss.format(new Date()) + "  SpMappingMsisdnDAO.insertCdrWhiteListBatch.executeBatch2:" + rowAdd + "-" + batch.length + " in (" + (System.currentTimeMillis() - timeDebug) + " ms");
                timeDebug = System.currentTimeMillis();
            }
            connection.commit();
            log.info(yyyyMMddhh24miss.format(new Date()) + "  SpMappingMsisdnDAO.insertCdrWhiteListBatch.commit done." + " in (" + (System.currentTimeMillis() - timeDebug) + " ms");
            connection.setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<CdrWhiteListView> syncCdrByPartitionConfig(String partitionName,String configWhiteList) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        long timeDebug = System.currentTimeMillis();
        List<CdrWhiteListView> cdrWhiteListViews = new ArrayList<>();

        configWhiteList=("".equals(configWhiteList)) ? yyyyMM.format(new Date()) :configWhiteList;
        int i = 0;
        int countFile = 100000;
        try {
            connection = DatabaseORAUtility.getConnection();
//            pstmt = connection.prepareStatement(" select /*+ PARALLEL(4) */ a.msisdn_id, a.type_wl, b.msisdn from cdr_whiteList_"+configWhiteList+" partition("+partitionName+") a left join spk_mapping_msisdn_mbf b on a.msisdn_id=b.msisdn_mbf ");
            pstmt = connection.prepareStatement(" select /*+ PARALLEL(4) */ a.msisdn_id, a.type_wl, a.msisdn from sync_cdr_whitelist_"+configWhiteList+" partition("+partitionName+") a ");
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                CdrWhiteListView cdrWhiteListView = new CdrWhiteListView(resultSet.getString(1), yyyyMMdd.format(new Date()), resultSet.getString(2), resultSet.getString(2).equals("C120") ? 1 : 0, resultSet.getString(3));
                cdrWhiteListViews.add(cdrWhiteListView);
            }

            log.info(yyyyMMddhh24miss.format(new Date()) + "  CdrWhitelistDAO.syncCdrByPartitionConfig size:" + cdrWhiteListViews.size() + " - " +countFile+ " in (" + (System.currentTimeMillis() - timeDebug) + " ms");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cdrWhiteListViews;
    }

    public Boolean insertCdrWhiteListBatchConfig(String insertWhitelist,List<CdrWhiteListView> cdrWhiteListViews) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        int sizebatch = 1000;
        int rowAdd = 0;
        long timeDebug = System.currentTimeMillis();



        try {
            connection = DatabaseSQLUtility.getConnection();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(insertWhitelist);
            for (CdrWhiteListView cdrWhiteListView : cdrWhiteListViews) {
                pstmt.setString(1, cdrWhiteListView.getMsisdnId());
                pstmt.setString(2, cdrWhiteListView.getTypeWL());
                pstmt.setInt(3, cdrWhiteListView.getType());
                pstmt.setString(4, cdrWhiteListView.getMsisdn());
                pstmt.addBatch();
                rowAdd++;
                if (rowAdd >= sizebatch) {
                    rowAdd = 0;
                    int[] batch = pstmt.executeBatch();
                    log.info(yyyyMMddhh24miss.format(new Date()) + "  SpMappingMsisdnDAO.insertCdrWhiteListBatchConfig.executeBatch:" + rowAdd + "-" + batch.length + " in (" + (System.currentTimeMillis() - timeDebug) + " ms");
                    timeDebug = System.currentTimeMillis();
                }
            }
            if (rowAdd > 0) {
                int[] batch = pstmt.executeBatch();
                log.info(yyyyMMddhh24miss.format(new Date()) + "  SpMappingMsisdnDAO.insertCdrWhiteListBatchConfig.executeBatch2:" + rowAdd + "-" + batch.length + " in (" + (System.currentTimeMillis() - timeDebug) + " ms");
                timeDebug = System.currentTimeMillis();
            }
            connection.commit();
            log.info(yyyyMMddhh24miss.format(new Date()) + "  SpMappingMsisdnDAO.insertCdrWhiteListBatchConfig.commit done." + " in (" + (System.currentTimeMillis() - timeDebug) + " ms");
            connection.setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
