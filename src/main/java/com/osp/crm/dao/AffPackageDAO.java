/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.crm.dao;

import com.osp.crm.model.AFFPackageView;
import com.osp.db.config.DatabaseORACTVUtility;
import com.osp.db.config.DatabaseSQLReportUtility;
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
 */@Slf4j
public class AffPackageDAO {

    private SimpleDateFormat yyyyMMddhh24miss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    

    public List<AFFPackageView> getListPackage() {
        List<AFFPackageView> aFFPackageViews = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseORACTVUtility.getConnection();
            log.info(yyyyMMddhh24miss.format(new Date()) + "  AffPackageDAO.getListPackage.start query...");
            pstmt = connection.prepareStatement(" select pck_code, amount, pack_detail, num_expired, status, ratio from aff_package  ");
            resultSet = pstmt.executeQuery();
            log.info(yyyyMMddhh24miss.format(new Date()) + "  AffTrangsDAO.getPackagePackage.end query...");
            while (resultSet.next()) {
                AFFPackageView fFPackageView = new AFFPackageView(resultSet.getString(1), resultSet.getLong(2), resultSet.getString(3), resultSet.getLong(4), resultSet.getLong(5), resultSet.getDouble(6));
                aFFPackageViews.add(fFPackageView);
            }
            log.info(yyyyMMddhh24miss.format(new Date()) + "  AffTrangsDAO.getPackagePackage return list size:" + aFFPackageViews.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aFFPackageViews;
    }

    public Boolean insertPackageBatch(List<AFFPackageView> aFFPackageViews) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        int sizebatch = 500;
        int rowAdd = 0;
        try {
            connection = DatabaseSQLReportUtility.getConnection();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement("truncate table aff_package ");
            pstmt.execute();
            pstmt = connection.prepareStatement(" INSERT INTO aff_package(pck_code, amount, pack_detail, num_expired, status, ratio) "
                    + "  VALUES (?, ?, ?, ?, ?, ? ) ");
            for (AFFPackageView aFFPackageView : aFFPackageViews) {
                pstmt.setString(1, aFFPackageView.getPackCode());
                pstmt.setLong(2, aFFPackageView.getAmount());
                pstmt.setString(3, aFFPackageView.getPackDetail());
                pstmt.setLong(4, aFFPackageView.getNumberExpired());
                pstmt.setLong(5, aFFPackageView.getStatus());
                pstmt.setDouble(6, aFFPackageView.getRatio());
                pstmt.addBatch();
                rowAdd++;
                if (rowAdd >= sizebatch) {
                    rowAdd = 0;
                    int[] batch = pstmt.executeBatch();
                    log.info("AffPackageDAO.insertPackageBatch.executeBatch:" + rowAdd + "-" + batch.length);
                }
            }
            if (rowAdd > 0) {
                int[] batch = pstmt.executeBatch();
                log.info("AffPackageDAO.insertPackageBatch.executeBatch2:" + rowAdd + "-" + batch.length);
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
