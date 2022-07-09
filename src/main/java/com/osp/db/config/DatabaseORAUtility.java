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
public class DatabaseORAUtility {

    static ComboPooledDataSource cpds = new ComboPooledDataSource();

    static {
        try {
            cpds.setDataSourceName("oracle.jdbc.pool.OracleDataSource");
            cpds.setJdbcUrl("jdbc:oracle:thin:@10.10.10.16:9140/PSPACK");
//            cpds.setJdbcUrl("jdbc:oracle:thin:@10.38.21.138:1522/PSPACK");
            cpds.setUser("spack");
            cpds.setPassword("SP##89##nBMF");
            cpds.setInitialPoolSize(10);
            cpds.setMinPoolSize(10);
            cpds.setAcquireIncrement(10);
            cpds.setMaxPoolSize(15);
            cpds.setMaxStatements(15);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        while (connection == null) {
            try {
                connection = cpds.getConnection();
                if (connection == null || connection.isClosed()) {
                    log.error("get connect error ==> retry connect...");
                    Thread.sleep(60 * 1000);
                    connection = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    log.error("get connect error ==> retry connect...");
                    Thread.sleep(60 * 1000);
                    connection = null;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return connection;
    }

    public static void releaseAll(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (resultSet != null && !resultSet.isClosed()) {
                resultSet.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
