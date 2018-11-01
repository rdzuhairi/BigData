package helper;

import constant.Constant;
import constant.HoujinBangoConstant;
import entity.DBConnection;
import entity.Page;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.List;

public class HelperController {
    public static DBConnection getConnectionInfor(int conn_type) {
        DBConnection conn = new DBConnection();
        switch (conn_type) {
            case Constant.CONNECTION_LOCAL_ID:
                conn.setHost(Constant.HOST_LOCAL);
                conn.setPort(Constant.PORT_LOCAL);
                conn.setDbName(Constant.DBNAME_LOCAL);
                conn.setDbUser(Constant.DBUSER_LOCAL);
                conn.setDbPwd(Constant.DBPWD_LOCAL);
                break;
            case Constant.CONNECTION_REAL_ID:
                conn.setHost(Constant.HOST);
                conn.setPort(Constant.PORT);
                conn.setDbName(Constant.DBNAME);
                conn.setDbUser(Constant.DBUSER);
                conn.setDbPwd(Constant.DBPWD);
                break;
            default:
                break;
        }
        return conn;
    }

    public static boolean downloadFromWeb(Page page, String param, String dirName) throws Exception {
        boolean flag = false;
        String saveFolder = dirName + page.fileFolder;
        List<String> urls = WebHelper.getUrls(page.listPage, page.selector, param);
        for (String url : urls) {

            HttpUriRequest urlNext = new HttpGet(url);
            HttpClients.custom().setUserAgent(Constant.USER_AGENT);
            CloseableHttpClient client = HttpClientBuilder.create().build();
            client.execute(new HttpGet(url));

            HttpResponse response = client.execute(urlNext);
            HttpEntity entity = response.getEntity();

            String fileName = response.getFirstHeader("Content-Disposition").getValue();
            String name = java.net.URLDecoder.decode(fileName.substring(fileName.indexOf("''")+32), "UTF-8");

            FileOutputStream fos = new FileOutputStream(saveFolder + name);
            entity.writeTo(fos);

            InputStream is = entity.getContent();

            is.close();
            fos.close();

            client.close();
        }
        return flag;
    }
}
