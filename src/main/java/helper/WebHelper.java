package helper;

import constant.Constant;
import constant.HoujinBangoConstant;
import constant.HoujinBangoConstantUpdate;
import houjinbango.HoujinBango;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WebHelper {
    public static List<String> getUrls(List<String> listPage, String selector, String param) {
        List<String> list = new ArrayList<>();
        Document doc = null;
        String page = "";
        for (int i = 0; i < listPage.size(); i++) {
            page = listPage.get(i);
            try {
                doc = Jsoup.connect(page).userAgent(Constant.USER_AGENT).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (doc == null) {
                try {
                    doc = Jsoup.parse(readFromWeb(page));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (doc == null) {
                return null;
            }
            Elements urls = doc.body().select(selector);
            for (Element element : urls) {
                String fileEle = element.attr("onclick");
                String fileNo = fileEle.substring(18, 22);
                list.add(addUrl(param) + "?selDlFileNo=" + fileNo + "&event=download" );
            }
        }
        return list;
    }


    public static String readFromWeb(String webURL) throws IOException {
        String html = "";
        URL url = new URL(webURL);
        InputStream is = url.openStream();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                html += line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new MalformedURLException("URL is malformed!!");
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
        return html;
    }

    public static String addUrl(String param){
        String url = "";
        int webId = Integer.valueOf(param);
        switch (webId){
            case HoujinBangoConstant.ID:
                url = HoujinBangoConstant.PAGE;
                break;
            case HoujinBangoConstantUpdate.ID:
                url = HoujinBangoConstantUpdate.PAGE;
                break;
        }
        return url;
    }
}
