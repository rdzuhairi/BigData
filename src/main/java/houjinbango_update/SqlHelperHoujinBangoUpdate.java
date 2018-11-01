package houjinbango_update;

import com.mysql.jdbc.Connection;
import houjinbango.ContentHoujinBango;
import main.CSVEat;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlHelperHoujinBangoUpdate {
    public static boolean createContent(Connection conn, String table, ContentHoujinBangoUpdate content) {
        try {
            List<String> contentList = new ArrayList<>();

            contentList.add(content.sequence_number);
            contentList.add(content.corporate_number);
            contentList.add(content.processing_division);
            contentList.add(content.correction_classification);
            contentList.add(content.update_date);
            contentList.add(content.change_date);
            contentList.add(content.name);
            contentList.add(content.company_image_name_ID);
            contentList.add(content.corporate_type);
            contentList.add(content.prefecture);
            contentList.add(content.city);
            contentList.add(content.street_address);
            contentList.add(content.location_image_ID);
            contentList.add(content.prefecture_code);
            contentList.add(content.city_code);
            contentList.add(content.postal_code);
            contentList.add(content.foreign_address);
            contentList.add(content.foreign_address_image_ID);
            contentList.add(content.closure_date);
            contentList.add(content.closing_reason);
            contentList.add(content.succeded_corporation_number);
            contentList.add(content.reason_change_details);
            contentList.add(content.corporate_number_designated_date);
            contentList.add(content.latest_history);
            contentList.add(content.foreign_name);
            contentList.add(content.prefecture_in_english);
            contentList.add(content.address_in_english);
            contentList.add(content.overseas_location_in_english);
            contentList.add(content.furigana);

            String strInto = "";
            //set params for query
            try {
                for (String column : CSVEat.columnDatabase) {
                    strInto += column + ",";
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            strInto = strInto.substring(0, strInto.length() - 1);

            String strValues = "";
            for (int i = 0; i < CSVEat.columnDatabase.size(); i++) {
                strValues += "?,";
            }
            //values of query
            strValues = strValues.substring(0, strValues.length() - 1);

            // the mysql insert statement
            String query = " insert into " + table + " ( " + strInto + ")" + " values (" + strValues + ")";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setFetchSize(Integer.MIN_VALUE);

            if (CSVEat.columnDatabase.size() == contentList.size()){
                for (int i = 0; i < contentList.size(); i++){
                    preparedStmt.setString(i + 1, contentList.get(i));
                }
            }
            try{
                preparedStmt.execute();
            }catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
        return false;
    }
}
