package houjinbango_update;

import com.mysql.jdbc.Connection;
import com.opencsv.CSVReaderBuilder;
import houjinbango.SqlHelperHoujinBango;
import main.CSVEat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HoujinBangoUpdate {
    public static boolean getContent(Connection conn, String line, String filename){
        ContentHoujinBangoUpdate content = new ContentHoujinBangoUpdate();
        content.filename = CSVEat.fileName;

        ArrayList<String> columnLines =  new ArrayList<>(Arrays.asList(line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)")));
        List<String> contentColumn = new ArrayList<>();
        for(int i = 0; i <= CSVEat.columnDatabase.size() - 1; i++){

            if (columnLines.size() >= CSVEat.columnDatabase.size()) {
                if (columnLines.equals("")){
                    columnLines.add("");
                }
                content.sequence_number = columnLines.get(0).replaceAll("\"", "");
                content.corporate_number = columnLines.get(1).replaceAll("\"", "");
                content.processing_division = columnLines.get(2).replaceAll("\"", "");
                content.correction_classification = columnLines.get(3).replaceAll("\"", "");
                content.update_date = columnLines.get(4).replaceAll("\"", "");
                content.change_date = columnLines.get(5).replaceAll("\"", "");
                content.name = columnLines.get(6).replaceAll("\"", "");
                content.company_image_name_ID = columnLines.get(7).replaceAll("\"", "");
                content.corporate_type = columnLines.get(8).replaceAll("\"", "");
                content.prefecture = columnLines.get(9).replaceAll("\"", "");
                content.city = columnLines.get(10).replaceAll("\"", "");
                content.street_address = columnLines.get(11).replaceAll("\"", "");
                content.location_image_ID = columnLines.get(12).replaceAll("\"", "");
                content.prefecture_code = columnLines.get(13).replaceAll("\"", "");
                content.city_code = columnLines.get(14).replaceAll("\"", "");
                content.postal_code = columnLines.get(15).replaceAll("\"", "");
                content.foreign_address = columnLines.get(16).replaceAll("\"", "");
                content.foreign_address_image_ID = columnLines.get(17).replaceAll("\"", "");
                content.closure_date = columnLines.get(18).replaceAll("\"", "");
                content.closing_reason = columnLines.get(19).replaceAll("\"", "");
                content.succeded_corporation_number = columnLines.get(20).replaceAll("\"", "");
                content.reason_change_details = columnLines.get(21).replaceAll("\"", "");
                content.corporate_number_designated_date = columnLines.get(22).replaceAll("\"", "");
                content.latest_history = columnLines.get(23).replaceAll("\"", "");
                content.foreign_name = columnLines.get(24).replaceAll("\"", "");
                content.prefecture_in_english = columnLines.get(25).replaceAll("\"", "");
                content.address_in_english = columnLines.get(26).replaceAll("\"", "");
                content.overseas_location_in_english = columnLines.get(27).replaceAll("\"", "");
                content.furigana = columnLines.get(28).replaceAll("\"", "");
            }
        }
        if (SqlHelperHoujinBangoUpdate.createContent(conn, CSVEat.page.databaseTable, content)) {
            return true;
        } else
            return false;
    }

    public static List<String> getKeyColumnValues(ContentHoujinBangoUpdate content){
        List<String> list = new ArrayList<>();
        list.add(content.corporate_number);
        return list;
    }
}
