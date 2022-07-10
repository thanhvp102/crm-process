/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.db.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author OSP PC
 */
@Slf4j
public class DatabaseSQLReportUtility {

    static ComboPooledDataSource cpds = new ComboPooledDataSource();
    static Connection connection = null;

    static {
        try {
            cpds.setJdbcUrl("jdbc:mysql://192.168.1.85:3306/report?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Ho_Chi_Minh");
            cpds.setUser("report");
            cpds.setPassword("dataReport@2022");
            cpds.setInitialPoolSize(10);
            cpds.setMinPoolSize(10);
            cpds.setAcquireIncrement(10);
            cpds.setMaxPoolSize(15);
            cpds.setMaxStatements(0);
            //call to time-out and break with an SQLException after the specified number of milliseconds
            cpds.setCheckoutTimeout(0);
            //Defines how many times c3p0 will try to acquire a new Connection from the database before giving up
            cpds.setAcquireRetryAttempts(30);
            //Milliseconds, time c3p0 will wait between acquire attempts.
            cpds.setAcquireRetryDelay(60000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() {
        Connection connection=null;
        try {
            connection = cpds.getConnection();
        } catch (Exception ex) {
            log.error("Error connect report: "+ex);
        }
        return connection;
    }
//    public static Connection getConnection() {
//        try {
//            while (connection == null || connection.isClosed()) {
//                try {
//                    connection = cpds.getConnection();
//                    if (connection == null || connection.isClosed()) {
//                        log.error("get connect error ==> retry connect...");
//                        Thread.sleep(60 * 1000);
//                        connection = null;
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    try {
//                        log.error("get connect error ==> retry connect...");
//                        Thread.sleep(60 * 1000);
//                        connection = null;
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return connection;
//    }
//
//    public static void releaseAll(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
//        try {
//            if (resultSet != null && !resultSet.isClosed()) {
//                resultSet.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            if (preparedStatement != null) {
//                preparedStatement.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            if (connection != null) {
//                connection.close();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
