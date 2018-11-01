package constant;

import org.omg.CORBA.PUBLIC_MEMBER;

import javax.print.DocFlavor;

public class HoujinBangoConstant {
    public static final int ID = 1;
    public static final String NAME_EXPORT = "crawling_houjinBangou_";
    public static final String FILE_FOLDER = "src\\";
    public static final String DATABASE_TABLE = "houjinbangodata";
    public static final String SELECTOR = "#appForm > div.inBox21 > div:nth-child(3) > table > tbody > tr> td > dl > dd > ol > li > a";
    public static final String PAGE = "https://www.houjin-bangou.nta.go.jp/download/zenken/";
    public static final String HOME_PAGE = "www.houjin-bangou.nta.go.jp";
    public static final String ENCODING = "windows-932";

    public static final String SEQUENCE_NUMBER = "一連番号";
    public static final String CORPORATE_NUMBER = "法人番号";
    public static final String PROCESSING_DIVISION = "処理区分";
    public static final String CORRECTION_CLASSIFICATION = "訂正区分";
    public static final String UPDATE_DATE = "更新年月日";
    public static final String CHANGE_DATE = "変更年月日 ";
    public static final String NAME_COMPANY = "商号又は名称";
    public static final String COMPANY_IMAGE_ID = "商号又は名称イメージ ID";
    public static final String CORPORATE_TYPE = "法人種別";
    public static final String PREFECTURE   = "国内所在地（都道府県）";
    public static final String CITY = "国内所在地（市区町村）";
    public static final String STREET_ADDRESS = "国内所在地（丁目番地等）";
    public static final String LOCATION_IMAGE_ID = "国内所在地イメージ ID";
    public static final String PREFECTURE_CODE = "都道府県コード";
    public static final String CITY_CODE = "市区町村コード";
    public static final String POSTAL_CODE = "郵便番号";
    public static final String FOREIGN_ADDRESS = "国外所在地";
    public static final String FOREIGN_ADDRESS_IMAGE_ID = "国外所在地イメージ ID";
    public static final String CLOSURE_DATE = "登記記録の閉鎖等年月日";
    public static final String CLOSING_REASON = "登記記録の閉鎖等の事由";
    public static final String SUCCEEDED_CORPORATION_NUMBER = "承継先法人番号";
    public static final String REASON_CHANGE_DETAILS = "変更事由の詳細";
    public static final String CORPORATE_NUMBER_DESIGNATION_DATE = "法人番号指定年月日";
    public static final String LATEST_HISTORY = "最新履歴";
    public static final String FOREIGN_NAME = "商号又は名称（英語表記）";
    public static final String PREFECTURE_IN_ENGLISIH = "国内所在地（都道府県）（英語表 記）";
    public static final String ADDRESS_IN_ENGLISH = "国内所在地（市区町村丁目番地等） （英語表記）";
    public static final String OVERSEAS_LOCATION_IN_ENGLISH = "国外所在地（英語表記）";
    public static final String FURIGANA = "フリガナ";
    public static final String TELEPHONE_NUMBER = "電話番号";
}
