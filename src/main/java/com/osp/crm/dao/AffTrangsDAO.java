/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.crm.dao;

import com.osp.crm.model.AFFTransPackageView;
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
public class AffTrangsDAO {

    private SimpleDateFormat yyyyMMddhh24miss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     *
     * @param partitionName ==> partition(SYS_P7012)
     * @return
     */
    public List<AFFTransPackageView> getTransPackage(String yyyyMMdd, boolean checkSyncTime) {
        List<AFFTransPackageView> aFFTransPackageViews = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseORACTVUtility.getConnection();
            log.info("AffTrangsDAO.getTransPackage.start query...");
            String strWhere = "";
            if (checkSyncTime) {
                strWhere = " AND TP.REG_TIME > to_date('" + yyyyMMdd + "','yyyyMMdd') ";
            }else{
                strWhere = " AND TP.REG_TIME > trunc(sysDate - 1) ";
            }
            pstmt = connection.prepareStatement(" SELECT "
                    + "          INITCAP( P.PARTNER_NAME)PARTNER_NAME,P.USERNAME, TP.msisdn, "
                    + "           case P.type  WHEN 0 then 'OSP' "
                    + "        WHEN 1 then 'Đại Lý'  "
                    + "        WHEN 2 then 'Điểm bán'  "
                    + "        WHEN 3 then 'CTV Tự do' "
                    + "        WHEN 4 then 'Admin công ty khu vực'  "
                    + "        WHEN 5 then 'Admin tổng công ty' "
                    + "        WHEN 6 then 'Admin mobifone tỉnh'  "
                    + "        WHEN 7 then 'Nhân viên bán hàng'  "
                    + "        WHEN 8 then 'Nhân viên mobifone'  "
                    + "        WHEN 9 then 'Chi nhánh'  "
                    + "          WHEN 10 then 'Giao dịch viên'  "
                    + "            WHEN 11 then 'AM/KAMS'  "
                    + "        end typ_ctv, tp.source, "
                    + "case   pk.type when 2  then 'trả sau' "
                    + "when 3 then 'Trả trước' "
                    + "when 6 then 'Trả trước  trả sau' end type_pack,pk.pck_code,pk.amount, to_char(tp.reg_time, 'ddMMyyyy hh24miss'), "
                    + " case when tp.status=3 then'SUC' ELSE   "
                    + "'UNSUC' END STATUS ,p.shop_region,pk.ratio,nvl(tp.SHARE_VALUE,0)SHARE_VALUE, nvl(tp.REFER_VALUE,0)REFER_VALUE "
                    + "        FROM "
                    + "            AFFILIATE.AFF_TRANS_PACKAGE TP, "
                    + "            AFFILIATE.AFF_PARTNER P, "
                    + "            AFFILIATE.AFF_PACKAGE PK "
                    + "        WHERE "
                    + "            TP.USERNAME = P.USERNAME "
                    + "            AND PK.PCK_CODE = TP.PACKAGE_NAME "
                    + "            AND P.STATUS = 1 "
                    + "            AND PK.STATUS = 1 "
                    + "            AND TP.REG_TIME < trunc(sysDate)" + strWhere );
            resultSet = pstmt.executeQuery();
            log.info(yyyyMMddhh24miss.format(new Date()) + "  AffTrangsDAO.getTransPackage.end query...");
            while (resultSet.next()) {
                AFFTransPackageView aFFTransPackageView = new AFFTransPackageView(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
                        resultSet.getString(7), resultSet.getLong(8), resultSet.getString(9), resultSet.getString(10), resultSet.getString(11), resultSet.getDouble(12), resultSet.getLong(13), resultSet.getLong(14));
                aFFTransPackageViews.add(aFFTransPackageView);
            }
            log.info(yyyyMMddhh24miss.format(new Date()) + "  AffTrangsDAO.getTransPackage return list size:" + aFFTransPackageViews.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aFFTransPackageViews;
    }

    public Boolean insertTransBatch(List<AFFTransPackageView> aFFTransPackageViews) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        int sizebatch = 500;
        int rowAdd = 0;
        try {
            connection = DatabaseSQLReportUtility.getConnection();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(" INSERT INTO aff_trans (partner_name,username,msisdn,typ_ctv,source,type_pack,pck_code,amount,reg_time,status,shop_region,ratio,share_value,refer_value)"
                    + "  VALUES (?, ?, ?, ?, ?,"
                    + " ?, ?, ?, STR_TO_DATE(?, '%d%m%Y %H%i%s'), ?,"
                    + " ?, ?, ?, ?) ");
            for (AFFTransPackageView aFFTransPackageView : aFFTransPackageViews) {
                pstmt.setString(1, aFFTransPackageView.getPartnerName());
                pstmt.setString(2, aFFTransPackageView.getUsername());
                pstmt.setString(3, aFFTransPackageView.getMsisdn());
                pstmt.setString(4, aFFTransPackageView.getTypeCtv());
                pstmt.setString(5, aFFTransPackageView.getSource());
                pstmt.setString(6, aFFTransPackageView.getTypePackage());
                pstmt.setString(7, aFFTransPackageView.getPckCode());
                pstmt.setDouble(8, aFFTransPackageView.getAmount());
                pstmt.setString(9, aFFTransPackageView.getRegTime());
                pstmt.setString(10, aFFTransPackageView.getStatus());
                pstmt.setString(11, aFFTransPackageView.getShopRegion());
                pstmt.setDouble(12, aFFTransPackageView.getRatio());
                pstmt.setLong(13, aFFTransPackageView.getShareValue());
                pstmt.setLong(14, aFFTransPackageView.getReferValue());
                pstmt.addBatch();
                rowAdd++;
                if (rowAdd >= sizebatch) {
                    rowAdd = 0;
                    int[] batch = pstmt.executeBatch();
                    log.info(yyyyMMddhh24miss.format(new Date()) + "  AffTrangsDAO.insertTransBatch.executeBatch:" + rowAdd + "-" + batch.length);
                }
            }
            if (rowAdd > 0) {
                int[] batch = pstmt.executeBatch();
                log.info(yyyyMMddhh24miss.format(new Date()) + "  AffTrangsDAO.insertTransBatch.executeBatch2:" + rowAdd + "-" + batch.length);
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
