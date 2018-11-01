package helper;

import entity.DBConnection;
import com.mysql.jdbc.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlHelper {
    public static Connection getConnection(DBConnection connection) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = null;
            conn = (Connection) DriverManager.getConnection(
                    "jdbc:mysql://" + connection.getHost() + ":" + connection.getPort() + "/" + connection.getDbName() + "?autoReconnect=true&characterEncoding=SHIFT_JIS&allowMultiQueries=true",
                    connection.getDbUser(), connection.getDbPwd() );
            // conn.close();
            if (conn != null) {
                System.out.println("Connect success!");
                System.out.println("host: " + connection.getHost());
                System.out.println("port: " + connection.getPort());
                System.out.println("database: " + connection.getDbName());
                System.out.println("user: " + connection.getDbUser());
                System.out.println("pwd: " + connection.getDbPwd());
            } else {
                System.out.println("Connect failed!");
            }
            return conn;
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getTitleColumnsDatabase(Connection conn, String table) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        // prepare query
        String query = "SELECT * FROM " + table;
        // create a statement
        stmt = conn.createStatement();
        // execute query and return result as a ResultSet
        rs = stmt.executeQuery(query);
        if (rs == null) {
            return null;
        }
        List<String> result = new ArrayList<String>();
        ResultSetMetaData rsMetaData = rs.getMetaData();
        int numberOfColumns = rsMetaData.getColumnCount();

        // get the column names; column index start from 3
        for (int i = 3; i < numberOfColumns + 1; i++) {
            String columnName = rsMetaData.getColumnName(i);
            result.add(columnName);
        }
        return result;
    }
}
