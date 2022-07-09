/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.crm.dao;

import com.osp.crm.model.AFFPartnerView;
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
 */
@Slf4j
public class AffPartnerDAO {

private SimpleDateFormat yyyyMMddhh24miss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     *
     * @param partitionName ==> partition(SYS_P7012)
     * @return
     */
    public void syncPartner(String yyyyMMdd, boolean  checkSyncTime) {
        List<AFFPartnerView> aFFPartnerViews = new ArrayList<>();
        List<AFFPartnerView> aFFPartnerViewsUpdate = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        int i = 0;
        try {
            connection = DatabaseORACTVUtility.getConnection();
            log.info(yyyyMMddhh24miss.format(new Date()) + "  AffTrangsDAO.getTransPackage.start query...");
            String strWhere = "";
            if (checkSyncTime) {
                strWhere = " and ((a.LAST_UPDATED is null and a.gen_date > to_date('"+yyyyMMdd+"','yyyyMMdd')) or a.LAST_UPDATED > to_date('"+yyyyMMdd+"','yyyyMMdd')) ";
            }else{
                strWhere = " and ((a.LAST_UPDATED is null and a.gen_date > trunc(sysDate-1)) or a.LAST_UPDATED > trunc(sysdate-1)) ";
            }
            pstmt = connection.prepareStatement(" select a.username, a.partner_name, a.status, a.type , to_char(a.gen_date,'ddMMyyyy hh24miss'), to_char(a.last_updated,'ddMMyyyy hh24miss'), a.presenter, a.gender,  "
                    + " to_char(a.date_birth,'ddMMyyyy hh24miss'), a.email, a.shop_region, b.name, a.pocket_money, a.shop_code, a.share_code  "
                    + " from AFF_PARTNER a left join  "
                    + " AFF_CAT_CATALOGY b on a.PROVINCE = b.id  "
                    + " where a.type in (1,2,3,7,8,10,11) and a.status = 1 and ((a.LAST_UPDATED is null and a.gen_date < trunc(sysDate)) or a.LAST_UPDATED < trunc(sysdate)) " + strWhere);
            resultSet = pstmt.executeQuery();
            log.info(yyyyMMddhh24miss.format(new Date()) + "  AffPartnerDAO.getTransPackage.end query...");
            while (resultSet.next()) {
                AFFPartnerView aFFPartnerView = new AFFPartnerView(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6),
                        resultSet.getString(7), resultSet.getInt(8), resultSet.getString(9), resultSet.getString(10), resultSet.getString(11), resultSet.getString(12), resultSet.getInt(13), resultSet.getString(14),
                        resultSet.getString(15));
                if(checkExitPartner(aFFPartnerView.getUsername())){
                    aFFPartnerViews.add(aFFPartnerView);
                }else{
                    aFFPartnerViewsUpdate.add(aFFPartnerView);
                }
                i++;
                if (i % 1000 == 0) {
                    insertPartnerBatch(aFFPartnerViews);
                    log.info(yyyyMMddhh24miss.format(new Date()) + "  SpMappingMsisdnDAO.getSPMappingMsisdnViewList add list:" + i);
                    aFFPartnerViews.clear();
                }
                if (i % 1000 == 0) {
                    updatePartnerBatch(aFFPartnerViewsUpdate);
                    log.info(yyyyMMddhh24miss.format(new Date()) + "  SpMappingMsisdnDAO.aFFPartnerViewsUpdate add list:" + i);
                    aFFPartnerViewsUpdate.clear();
                }
            }
            if (aFFPartnerViews != null && aFFPartnerViews.size() > 0) {
                log.info("aFFPartnerViews.size(): "+aFFPartnerViews.size());
                insertPartnerBatch(aFFPartnerViews);
            }
            if (aFFPartnerViewsUpdate != null && aFFPartnerViewsUpdate.size() > 0) {
                updatePartnerBatch(aFFPartnerViewsUpdate);
            }
            log.info(yyyyMMddhh24miss.format(new Date()) + "  AffPartnerDAO.getTransPackage return list size:" + aFFPartnerViews.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void syncPartnerAll() {
        List<AFFPartnerView> aFFPartnerViews = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        int i = 0;
        try {
            connection = DatabaseORACTVUtility.getConnection();
            log.info(yyyyMMddhh24miss.format(new Date()) + "  AffTrangsDAO.getTransPackage.start query...");
            pstmt = connection.prepareStatement(" select a.username, a.partner_name, a.status, a.type , to_char(a.gen_date,'ddMMyyyy hh24miss'), to_char(a.last_updated,'ddMMyyyy hh24miss'), a.presenter, a.gender,  "
                    + " to_char(a.date_birth,'ddMMyyyy hh24miss'), a.email, a.shop_region, b.name, a.pocket_money, a.shop_code, a.share_code  "
                    + " from AFF_PARTNER a left join  "
                    + " AFF_CAT_CATALOGY b on a.PROVINCE = b.id  "
                    + " where a.type in (1,2,3,7,8,10,11) and a.status = 1  ");
            resultSet = pstmt.executeQuery();
            log.info(yyyyMMddhh24miss.format(new Date()) + "  AffPartnerDAO.getTransPackage.end query...");
            while (resultSet.next()) {
                AFFPartnerView aFFPartnerView = new AFFPartnerView(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4), resultSet.getString(5), resultSet.getString(6),
                        resultSet.getString(7), resultSet.getInt(8), resultSet.getString(9), resultSet.getString(10), resultSet.getString(11), resultSet.getString(12), resultSet.getInt(13), resultSet.getString(14),
                        resultSet.getString(15));
                    aFFPartnerViews.add(aFFPartnerView);
                i++;
                if (i % 1000 == 0) {
                    insertPartnerBatch(aFFPartnerViews);
                    log.info(yyyyMMddhh24miss.format(new Date()) + "  SpMappingMsisdnDAO.getSPMappingMsisdnViewList add list:" + i);
                    aFFPartnerViews.clear();
                }
            }
            if (aFFPartnerViews != null && aFFPartnerViews.size() > 0) {
                insertPartnerBatch(aFFPartnerViews);
            }
            log.info(yyyyMMddhh24miss.format(new Date()) + "  AffPartnerDAO.getTransPackage return list size:" + aFFPartnerViews.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean checkExitPartner(String userName) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseSQLReportUtility.getConnection();
            log.info(yyyyMMddhh24miss.format(new Date()) + "  AffPartnerDAO.checkExitPartner.start query...");
            pstmt = connection.prepareStatement(" select 1 from aff_partner where username = ? ");
            pstmt.setString(1, userName);
            resultSet = pstmt.executeQuery();
            log.info(yyyyMMddhh24miss.format(new Date()) + "  AffPartnerDAO.checkExitPartner.end query...");
            while (resultSet.next()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public Boolean insertPartnerBatch(List<AFFPartnerView> aFFPartnerViews) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        int sizebatch = 500;
        int rowAdd = 0;
        try {
            connection = DatabaseSQLReportUtility.getConnection();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(" INSERT INTO aff_partner(username, partner_name, status, type, gen_date, "
                    + " last_updated, presenter, gender, date_birth, email, "
                    + " shop_region, province, pocket_money, shop_code, share_code) "
                    + "  VALUES (?, ?, ?, ?, STR_TO_DATE(?, '%d%m%Y %H%i%s'),"
                    + " STR_TO_DATE(?, '%d%m%Y %H%i%s'), ?, ?, STR_TO_DATE(?, '%d%m%Y %H%i%s'), ?,"
                    + " ?, ?, ?, ?, ?) ");
            for (AFFPartnerView aFFPartnerView : aFFPartnerViews) {
                pstmt.setString(1, aFFPartnerView.getUsername());
                pstmt.setString(2, aFFPartnerView.getPartnerName());
                pstmt.setInt(3, aFFPartnerView.getStatus());
                pstmt.setInt(4, aFFPartnerView.getType());
                pstmt.setString(5, aFFPartnerView.getGenDate());
                
                pstmt.setString(6, aFFPartnerView.getLastUpdate());
                pstmt.setString(7, aFFPartnerView.getPresenter());
                pstmt.setDouble(8, aFFPartnerView.getGender());
                pstmt.setString(9, aFFPartnerView.getDateBirth());
                pstmt.setString(10, aFFPartnerView.getEmail());
                
                pstmt.setString(11, aFFPartnerView.getShopRegion());
                pstmt.setString(12, aFFPartnerView.getProvince());
                pstmt.setInt(13, aFFPartnerView.getPocketMoney());
                pstmt.setString(14, aFFPartnerView.getShopCode());
                pstmt.setString(15, aFFPartnerView.getShareCode());
                pstmt.addBatch();
                rowAdd++;
                if (rowAdd >= sizebatch) {
                    rowAdd = 0;
                    int[] batch = pstmt.executeBatch();
                    log.info(yyyyMMddhh24miss.format(new Date()) + "  AffTrangsDAO.insertPartnerBatch.executeBatch:" + rowAdd + "-" + batch.length);
                }
            }
            if (rowAdd > 0) {
                int[] batch = pstmt.executeBatch();
                log.info(yyyyMMddhh24miss.format(new Date()) + "  AffTrangsDAO.insertPartnerBatch.executeBatch2:" + rowAdd + "-" + batch.length);
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public Boolean updatePartnerBatch(List<AFFPartnerView> aFFPartnerViews) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        int sizebatch = 500;
        int rowAdd = 0;
        try {
            connection = DatabaseSQLReportUtility.getConnection();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(" Update aff_partner set partner_name = ?, status  = ?, type  = ?, "
                    + " last_updated  = STR_TO_DATE(?, '%d%m%Y %H%i%s'), presenter  = ?, gender  = ?, date_birth  = STR_TO_DATE(?, '%d%m%Y %H%i%s'), email  = ?, "
                    + " shop_region  = ?, province  = ?, pocket_money  = ?, shop_code  = ?, share_code  = ? where username = ?  ");
            for (AFFPartnerView aFFPartnerView : aFFPartnerViews) {
                pstmt.setString(1, aFFPartnerView.getPartnerName());
                pstmt.setInt(2, aFFPartnerView.getStatus());
                pstmt.setInt(3, aFFPartnerView.getType());
                pstmt.setString(4, aFFPartnerView.getLastUpdate());
                pstmt.setString(5, aFFPartnerView.getPresenter());
                
                pstmt.setDouble(6, aFFPartnerView.getGender());
                pstmt.setString(7, aFFPartnerView.getDateBirth());
                pstmt.setString(8, aFFPartnerView.getEmail());
                pstmt.setString(9, aFFPartnerView.getShopRegion());
                pstmt.setString(10, aFFPartnerView.getProvince());
                
                pstmt.setInt(11, aFFPartnerView.getPocketMoney());
                pstmt.setString(12, aFFPartnerView.getShopCode());
                pstmt.setString(13, aFFPartnerView.getShareCode());
                pstmt.setString(14, aFFPartnerView.getUsername());
                pstmt.addBatch();
                rowAdd++;
                if (rowAdd >= sizebatch) {
                    rowAdd = 0;
                    int[] batch = pstmt.executeBatch();
                    log.info(yyyyMMddhh24miss.format(new Date()) + "  AffTrangsDAO.updatePartnerBatch.executeBatch:" + rowAdd + "-" + batch.length);
                }
            }
            if (rowAdd > 0) {
                int[] batch = pstmt.executeBatch();
                log.info(yyyyMMddhh24miss.format(new Date()) + "  AffTrangsDAO.updatePartnerBatch.executeBatch2:" + rowAdd + "-" + batch.length);
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
