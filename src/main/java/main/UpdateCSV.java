package main;

import com.mysql.jdbc.Connection;
import constant.Constant;
import entity.DBConnection;
import entity.Page;
import helper.HelperController;
import helper.SqlHelper;
import houijnbangohelper.Controller;


import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UpdateCSV {
    public static List<String> columnDatabase;
    public static List<String> columnTSV;
    public static Page page;
    public static  String filename;

    public static void main(String[] args){
        String param = "";
        if (args.length == 1){
            param = args[0];
        }

        DBConnection connEntity = HelperController.getConnectionInfor(Constant.CONNECTION_LOCAL_ID);
        Connection conn = SqlHelper.getConnection(connEntity);
        if (conn == null){
            System.exit(0);
        }

        if (param.equals("0")) {
            int n_web = Constant.N_ALL_WEB;
            for (int i = 1; i <= n_web; i++) {
                run(String.valueOf(i), conn);
            }
        } else {
            String[] params = param.split(",");
            for (String str_param : params) {
                run(str_param, conn);
            }
        }

        try {
            conn.close();
            System.out.println("connection closed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("----END----");
        System.exit(0);
    }


    public static void run(String param, Connection conn){
        page = Controller.getPageInfo(param);

        if (!page.listPage.get(0).equals("")){
            try {
                if (!HelperController.downloadFromWeb(page, param, Constant.DIR_NAME)){
                    System.out.println("error: download file from: " + page.homePage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            columnDatabase = SqlHelper.getTitleColumnsDatabase(conn, page.databaseTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            process(String.valueOf(page.id), conn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void process(String pageId, Connection conn) throws IOException {
        File zipFolder = new File(page.fileFolder);
        String nameFileCSV = null;
        File[] listOfFile = zipFolder.listFiles();
        for (int i = 0; i < listOfFile.length; i++){
            if (listOfFile[i].isFile()){
                nameFileCSV = listOfFile[i].getName();
            }

            ZipInputStream zis = new ZipInputStream(new FileInputStream(nameFileCSV));
            ZipEntry ze = null;
            int unzipCounter = 0;
            while ((ze = zis.getNextEntry()) != null){
                unzipCounter++;
                byte[] buffer = new byte[1024];
                FileOutputStream fos = new FileOutputStream(new File(zipFolder, ze.getName()));
                int unzipLength = 0;
                while ((unzipLength = zis.read(buffer)) > 0){
                    fos.write(buffer, 0, unzipLength);
                }
                zis.closeEntry();
                fos.close();
            }
        }

        for (File file : listOfFile){
            String encoding;
            String nameFile = file.getName();
            if (nameFile.contains(Constant.TYPE_FILE_ZIP)||nameFile.contains(Constant.TYPE_FILE_OTHER)){
                continue;
            }

            String line = "";
            int rowNo = 1;
            int count = 0;
            List<String> listTemp = new ArrayList<>();
            filename = nameFile;
            encoding = Controller.getEncoding(pageId);

            checkValidFile(file);

            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
            listTemp = Controller.getColumnsName(line);
            columnTSV = listTemp;
            while ((line = reader.readLine()) != null){
                if (!Controller.getContent(pageId, conn, line, filename)){
                    System.out.println(nameFile + "data line = " + rowNo + " not inserted");
                } else {
                    count++;
                }
                rowNo++;
            }
            System.out.println("Web Id " + pageId + " - " + nameFile + " data inserted into database " + page.databaseTable );
        }
    }

    private static void checkValidFile(File f) {
        boolean valid = true;
        try {
            if (!f.exists() || f.isDirectory()) {
                valid = false;
            }
        } catch (Exception e) {
            valid = false;
        }
        if (!valid) {
            System.out.println("File doesn't exist: " + f.getName());
            System.exit(0);
        }
    }
}
