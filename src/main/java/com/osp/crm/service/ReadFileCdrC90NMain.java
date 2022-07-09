/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osp.crm.service;

import com.osp.crm.dao.CdrWhitelistDAO;
import com.osp.crm.model.CdrWhiteListView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author OSP PC
 */
@Service
@Slf4j
public class ReadFileCdrC90NMain {

    private SimpleDateFormat ddMMyyyy = new SimpleDateFormat("ddMMyyyy");

    //@Scheduled(cron = "0 0 1 * * ?",zone ="Asia/Bangkok")
    public static void readFileCdrC90NMain() {
        try {
            ReadFileCdrC90NMain readFileCdrMapping = new ReadFileCdrC90NMain();
            readFileCdrMapping.readFolderLocal("/home/app/genFile/C90N/file/", "/home/app/genFile/C90N/log/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    CdrWhitelistDAO cdrWhitelistDAO = new CdrWhitelistDAO();

    public void readFolderLocal(String localFolderPath, String logsFolderPath) {
        try {
            File file = new File(localFolderPath);
            if (file.isDirectory()) {
                if ((file.listFiles()).length > 0) {
                    for (File fileCdr : file.listFiles()) {
                        if (fileCdr != null) {
                            log.info(" read file fileCdr.getFileName():" + fileCdr.getName());
                            readFile(localFolderPath, logsFolderPath, fileCdr.getName());
                        }
                    }
                }
            } else {
                log.info("file is not directory >>>>>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean readFile(String filePath, String movePath, String fileName) {
        boolean result = false;
        int countLineAddSuccess = 0;
        int countLineErr = 0;
        int numberOfLine = 0;
        try {
            String strGenDate = ddMMyyyy.format(new Date());
            File fileErr = new File(filePath + fileName);
            FileReader fr = new FileReader(fileErr);
            BufferedReader br = new BufferedReader(fr);
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>> Read " + fileName + " >>>>>>>>>>>>>>>>>>>>>>>");
            String line;
            List<CdrWhiteListView> cdrWhiteListViews = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                numberOfLine++;
                if (!"".equals(line) && !line.contains(";;") && !line.startsWith(";") && !line.endsWith(";") && line.contains(";")) {
                    String[] strLine = line.split(";");
                    if (strLine.length >= 2) {
                        try {
                            CdrWhiteListView cdrWhiteListView = new CdrWhiteListView(strLine[0].trim(), strGenDate, strLine[1].trim(), strLine[1].trim().equals("C90N") ? 1 : 0, strLine[2].trim());
                            cdrWhiteListViews.add(cdrWhiteListView);
                            countLineAddSuccess++;
                        } catch (NumberFormatException e) {
                            log.info("NumberFormatException line " + numberOfLine + " of file " + fileName + ">>> " + e.toString());
                            countLineErr++;
                        }
                        continue;
                    }
                    log.info("Error line " + numberOfLine + " of file " + fileName);
                    countLineErr++;
                    continue;

                } else {
                    log.info(line);
                }
            }
            log.info("Read file:" + fileName + "|" + numberOfLine + "-" + cdrWhiteListViews.size());
            cdrWhitelistDAO.insertCdrWhiteListBatch(cdrWhiteListViews);
            try {
                if (fr != null) {
                    fr.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                File fileMove = new File(movePath);
                if (!fileMove.exists()) {
                    fileMove.mkdirs();
                }
                movePath = movePath + fileName;
                Files.move(Paths.get(filePath + fileName, new String[0]), Paths.get(movePath, new String[0]), new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
                result = true;
                log.info("add success blackList:" + countLineAddSuccess + "/" + numberOfLine + " number er:" + countLineErr);
            } catch (IOException e) {
                log.info("move file " + fileName + " >>> error: " + e.toString());
                e.printStackTrace();
            }
            log.info(">>>>>>>>>>>>>>>>>>>>>>>>> End Read " + fileName + " >>>>>>>>>>>>>>>>>>>>>>>");
        } catch (Exception e) {
            log.info("readFileError error exception >> " + e.toString());
            return false;
        }
        return result;
    }

}
