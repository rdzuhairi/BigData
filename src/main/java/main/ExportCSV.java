package main;

import com.mysql.jdbc.Connection;
import constant.Constant;
import entity.DBConnection;
import entity.Page;
import helper.HelperController;
import helper.SqlHelper;
import houijnbangohelper.Controller;
import houijnbangohelper.ExportController;

import java.sql.SQLException;
import java.util.List;

public class ExportCSV {
    public static List<String> columnsDatabase;
    public static List<String> columnsTsv;
    public static Page page;

    public static void main(String[] args){
        String param = "";
        if(args.length == 1){
            param = args[0];
        }

        DBConnection connEntity = HelperController.getConnectionInfor(Constant.CONNECTION_LOCAL_ID);
        Connection conn = SqlHelper.getConnection(connEntity);
        if (conn == null) {
            System.exit(0);
        }

        if(param == "0") {
        } else {
            String[] arr_param = param.split("[,]");
            for (String id_param : arr_param) {
                try {
                    exportToTSV(id_param, conn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        // close connection
        try {
            conn.close();
            System.out.println("connection closed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // end
        System.out.println("----END----");
        System.exit(0);
    }

    public static void exportToTSV(String param, Connection conn) throws SQLException {
        page = Controller.getPageInfo(param);

        List<String> fileNames = null;
        String fileName = "";

        try {
            columnsDatabase = SqlHelper.getTitleColumnsDatabase(conn, page.databaseTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        columnsTsv = ExportController.getTitleExport(param);

        String[] params = param.split(",");
        for (String strParam : params){
            fileNames = ExportController.getFileNameExport(strParam);
            for (int i = 0; i < fileNames.size(); i++) {
                fileName = fileNames.get(i);
                ExportController.exportTSV(conn, strParam, fileName);
            }
        }
    }
}
