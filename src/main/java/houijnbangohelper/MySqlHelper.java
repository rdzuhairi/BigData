package houijnbangohelper;

import com.mysql.jdbc.Connection;
import constant.HoujinBangoConstant;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySqlHelper {
    public static final String NEW_LINE_SPEARATOR = "\n";
    public static List<String> list = new ArrayList<>();

    public static boolean exportTSVFile(Connection conn, String pageId, String fileName, int cutColumns) throws SQLException {
        conn.setAutoCommit(false);
        Statement st = null;

        list.add("\"URL\"\t\"法人番号\"\t\"名称\"\t\"住所\"\t\"カナ\"\t\"最終更新年月日\"\t\"新規法人番号指定年月日\"\t\"電話番号\"\t\"県域\"");
        st = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        st.setFetchSize(Integer.MIN_VALUE);

        String sql = ("SELECT MAX(created_at) AS MaxDateTime, corporate_number, name, city, street_address, furigana, update_date, corporate_number_designated_date, prefecture FROM houjinbangodata GROUP BY corporate_number");
        ResultSet rs = null;
        FileWriter fw = null;

        try {
            rs = st.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            fw = new FileWriter(fileName);
            if (!rs.next()){
                System.out.println("empty");
            }else
                do {
                    String corporate_number = rs.getString("corporate_number");
                    String url = HoujinBangoConstant.HOME_PAGE + "/henkorireki-johoto.html?selHouzinNo=" + corporate_number;
                    String name = rs.getString("name");
                    String street_address = rs.getString("street_address");
                    String city = rs.getString("city");
                    String furigana = rs.getString("furigana");
                    String update_date = rs.getString("update_date");
                    String corporate_number_designated_date = rs.getString("corporate_number_designated_date");
                    String prefecture = rs.getString("prefecture");
                    String phone_number = "";

                    list.add("\"" + url + "\"" + "\t" +
                            "\"" + corporate_number + "\"" + "\t" +
                            "\"" + name + "\"" + "\t" +
                            "\"" + (prefecture + city + street_address) + "\"" + "\t" +
                            "\"" + furigana + "\"" + "\t" +
                            "\"" + update_date + "\"" + "\t" +
                            "\"" + corporate_number_designated_date + "\"" + "\t" +
                            "\"" + phone_number + "\"" + "\t" +
                            "\"" + prefecture + "\"");
                    if (list.size() >= 100) {
                        for (String line : list) {
                            fw.append(line);
                            fw.append(NEW_LINE_SPEARATOR);
                        }
                        fw.flush();
                        list.clear();
                    }
                }while (rs.next());
                rs.close();
                st.close();
                System.out.println(fileName + " is exported");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
