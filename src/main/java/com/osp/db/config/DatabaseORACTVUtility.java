/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.db.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;

/**
 * @author OSP PC
 */
@Slf4j
public class DatabaseORACTVUtility {

    static ComboPooledDataSource cpds = new ComboPooledDataSource();
    static Connection connection = null;

    static {
        try {
            cpds.setDataSourceName("oracle.jdbc.pool.OracleDataSource");
            cpds.setJdbcUrl("jdbc:oracle:thin:@10.10.10.16:1127/MBAFF");
            cpds.setUser("affiliate");
            cpds.setPassword("aff##$8921");
            cpds.setInitialPoolSize(5);
//            cpds.setStatementCacheNumDeferredCloseThreads(1);
            cpds.setMinPoolSize(2);
            cpds.setAcquireIncrement(3);
            cpds.setMaxPoolSize(20);
            cpds.setMaxStatements(200);
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
        try {
            connection = cpds.getConnection();
        } catch (Exception ex) {
            log.error("Error connect DbOraCTv: "+ex);
        }


//        while (connection == null) {
//            try {
//                connection = cpds.getConnection();
//                if (connection == null || connection.isClosed()) {
//                    log.error("get connect error ==> retry connect...");
//                    Thread.sleep(60 * 1000);
//                    connection = null;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//                try {
//                    log.error("get connect error ==> retry connect...");
//                    Thread.sleep(60 * 1000);
//                    connection = null;
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//        }
        return connection;
    }
}
