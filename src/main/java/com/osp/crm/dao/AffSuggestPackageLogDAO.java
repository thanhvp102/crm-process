/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.crm.dao;

import com.osp.common.DateUtils;
import com.osp.crm.model.AffSuggestPackageLogs;
import com.osp.crm.model.AffSuggestPackageLogs;
import com.osp.db.config.DatabaseORACTVUtility;
import com.osp.db.config.DatabaseSQLReportUtility;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 *
 * @author OSP PC
 */
@Slf4j
public class AffSuggestPackageLogDAO {

    private SimpleDateFormat yyyyMMddhh24miss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     *
     * @param partitionName ==> partition(SYS_P7012)
     * @return
     */
    public List<AffSuggestPackageLogs> getAffSuggestPackageLogs(String yyyyMMdd, boolean checkSyncTime) {
        List<AffSuggestPackageLogs> affSuggestPackageLogs = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseORACTVUtility.getConnection();
            log.info("AffSuggestPackageLogDAO.getSUGGEST_PACKAGE.start query...");
            String strWhere = "";
            if (checkSyncTime) {
                strWhere = " AND GEN_DATE > to_date('" + yyyyMMdd + "','yyyyMMdd') ";
            }else{
                strWhere = " AND GEN_DATE > trunc(sysDate - 1) ";
            }
            pstmt = connection.prepareStatement(" SELECT MSISDN,USER_NAME,IP,MODULE_ID,ACTIONS,STATUS,GEN_DATE,LAST_UPDATED,CREATE_BY,UPDATE_BY "
                    + "        FROM "
                    + "            AFFILIATE.AFF_SUGGEST_PACKAGE_LOGS  "
                    + "        WHERE "
                    + "             1=1 " + strWhere );
            resultSet = pstmt.executeQuery();
            log.info(yyyyMMddhh24miss.format(new Date()) + "  AffSuggestPackageLogDAO.getTransPackage.end query...");
            while (resultSet.next()) {
                AffSuggestPackageLogs packageLogs = new AffSuggestPackageLogs(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getLong(6),
                         resultSet.getString(7),resultSet.getString(8), resultSet.getString(9),resultSet.getString(10));
                affSuggestPackageLogs.add(packageLogs);
            }
            log.info(yyyyMMddhh24miss.format(new Date()) + "  AffSuggestPackageLogDAO.getSUGGEST_PACKAGE return list size:" + affSuggestPackageLogs.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return affSuggestPackageLogs;
    }

    public Boolean insertAffSuggestPackageLogsBatch(List<AffSuggestPackageLogs> AffSuggestPackageLogss) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        int sizebatch = 500;
        int rowAdd = 0;
        try {
            connection = DatabaseSQLReportUtility.getConnection();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(" INSERT INTO AFF_SUGGEST_PACKAGE_LOGS (MSISDN,USER_NAME,IP,MODULE_ID,ACTIONS,STATUS,GEN_DATE,LAST_UPDATED,CREATE_BY,UPDATE_BY)"
                    + "  VALUES (?, ?, ?, ?, ?,"
                    + " ?, STR_TO_DATE(?, '%d-%m-%Y %H:%i:%s'), STR_TO_DATE(?, '%d-%m-%Y %H:%i:%s'), ?, ?) ");
            for (AffSuggestPackageLogs affSuggestPackageLogs : AffSuggestPackageLogss) {
                pstmt.setString(1, affSuggestPackageLogs.getMsisdn());
                pstmt.setString(2, affSuggestPackageLogs.getUsername());
                pstmt.setString(3, affSuggestPackageLogs.getIp());
                pstmt.setString(4, affSuggestPackageLogs.getModuleId());
                pstmt.setString(5, affSuggestPackageLogs.getActions());
                pstmt.setLong(6, affSuggestPackageLogs.getStatus());
                pstmt.setString(7, DateUtils.formatDate(affSuggestPackageLogs.getGenDate(),"yyyy-mm-dd HH:mm:ss","dd-MM-yyyy HH:mm:ss") );
                pstmt.setString(8,  DateUtils.formatDate(affSuggestPackageLogs.getLastUpdated(),"yyyy-mm-dd HH:mm:ss","dd-MM-yyyy HH:mm:ss") );
                pstmt.setString(9, affSuggestPackageLogs.getCreateBy());
                pstmt.setString(10, affSuggestPackageLogs.getUpdateBy());
                pstmt.addBatch();
                rowAdd++;
                if (rowAdd >= sizebatch) {
                    rowAdd = 0;
                    int[] batch = pstmt.executeBatch();
                    log.info(yyyyMMddhh24miss.format(new Date()) + "  AffSuggestPackageLogDAO.insertTransBatch.executeBatch:" + rowAdd + "-" + batch.length);
                }
            }
            if (rowAdd > 0) {
                int[] batch = pstmt.executeBatch();
                log.info(yyyyMMddhh24miss.format(new Date()) + "  AffSuggestPackageLogDAO.insertTransBatch.executeBatch2:" + rowAdd + "-" + batch.length);
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    

    
}
