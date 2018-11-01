package houijnbangohelper;

import com.mysql.jdbc.Connection;
import constant.Constant;
import constant.HoujinBangoConstant;
import constant.HoujinBangoConstantUpdate;
import entity.Page;
import houjinbango.HoujinBango;
import houjinbango_update.HoujinBangoUpdate;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    public static Page getConstant(int id, String fileFolder, String databaseTable, String selector, String homePage) {
        Page page = new Page();
        page.id = id;
        page.fileFolder = fileFolder;
        page.databaseTable = databaseTable;
        page.selector = selector;
        page.homePage = homePage;
        page.listPage = getListPageConstant(id);

        return page;
    }

    public static List<String> getListPageConstant(int pageId) {
        List<String> list = new ArrayList<>();
        switch (pageId) {
            case HoujinBangoConstant.ID:
                list.add(HoujinBangoConstant.PAGE);
                break;
            case HoujinBangoConstantUpdate.ID:
                list.add(HoujinBangoConstantUpdate.PAGE);
                break;
        }
        return list;
    }

    public static Page getPageInfo(String param) {
        Page page = new Page();
        int webId = Integer.valueOf(param);
        switch (webId) {
            case HoujinBangoConstant.ID:
                page = getConstant(HoujinBangoConstant.ID, HoujinBangoConstant.FILE_FOLDER,
                        HoujinBangoConstant.DATABASE_TABLE, HoujinBangoConstant.SELECTOR,
                        HoujinBangoConstant.HOME_PAGE);
                break;
            case HoujinBangoConstantUpdate.ID:
                page = getConstant(HoujinBangoConstantUpdate.ID, HoujinBangoConstantUpdate.FILE_FOLDER,
                        HoujinBangoConstantUpdate.DATABASE_TABLE, HoujinBangoConstantUpdate.SELECTOR,
                        HoujinBangoConstantUpdate.HOME_PAGE);
                break;

            default:
                break;
        }
        return page;
    }

    public static String getEncoding(String pageId) {
        String encoding = "";
        switch (Integer.valueOf(pageId)) {
            case HoujinBangoConstant.ID:
                encoding = HoujinBangoConstant.ENCODING;
                break;
            case HoujinBangoConstantUpdate.ID:
                encoding = HoujinBangoConstantUpdate.ENCODING;
                break;
        }
        return encoding;
    }

    public static boolean getContent(String pageId, Connection conn,String line,  String fileName){
            boolean flag = false;
            int webId = Integer.valueOf(pageId);
            switch (webId){
                case HoujinBangoConstant.ID:
                    flag = HoujinBango.getContent(conn, line, pageId);
                    break;
                case HoujinBangoConstantUpdate.ID:
                    flag = HoujinBangoUpdate.getContent(conn, line, pageId);
                    break;
                default:
                    break;
            }

            return flag;
        }

    public static List<String> getColumnsName(String line) {
        List<String> lis_columns = new ArrayList<>();
        String[] columns = line.split(Constant.CSV_SEPARATOR_CHAR);
        int cout = 1;
        for (int i = 0; i < line.length(); i++) {
            Character ch = line.charAt(i);
            if (ch.toString().equals(",")) {
                cout++;
            }
        }

        for (int i = 0; i < columns.length; i++) {
            lis_columns.add(columns[i].replaceAll("\"", "").trim());
        }
        if (columns.length < cout) {
            for (int e = columns.length; e < cout; e++) {
                lis_columns.add("");
            }
        }
        return lis_columns;
    }
}
