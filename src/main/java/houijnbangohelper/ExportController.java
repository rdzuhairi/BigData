package houijnbangohelper;

import com.mysql.jdbc.Connection;
import constant.HoujinBangoConstant;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExportController {
    public static final String TYPE_FILE_EXPORT = ".tsv";

    public static List<String> getFileNameExport(String pageId){
        List<String> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();

        switch (Integer.valueOf(pageId)){
            case HoujinBangoConstant.ID:
                list.add(HoujinBangoConstant.NAME_EXPORT + sdf.format(date) + TYPE_FILE_EXPORT);
                break;
        }
        return list;
    }

    public static List<String> getTitleExport(String pageId){
        List<String> list = new ArrayList<>();
        switch (Integer.valueOf(pageId)) {
            case HoujinBangoConstant.ID:
                list.add(HoujinBangoConstant.CORPORATE_NUMBER);
                list.add(HoujinBangoConstant.NAME_COMPANY);
                list.add(HoujinBangoConstant.STREET_ADDRESS);
                list.add(HoujinBangoConstant.FURIGANA);
                list.add(HoujinBangoConstant.UPDATE_DATE);
                list.add(HoujinBangoConstant.CORPORATE_NUMBER_DESIGNATION_DATE);
                list.add(HoujinBangoConstant.TELEPHONE_NUMBER);
                list.add(HoujinBangoConstant.PREFECTURE);
                break;
        }
        return  list;
    }

    public static void exportTSV(Connection conn, String strParam, String fileName) throws SQLException {
        switch (Integer.valueOf(strParam)){
            case HoujinBangoConstant.ID:
                MySqlHelper.exportTSVFile(conn, strParam, fileName, 1);
                break;

            default:
                break;
        }

    }
}
