package priv.gao.common.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author gaoqiang
 * @version 1.0
 * @description 日期帮助
 * @date 2021/8/25 11:47
 */
public class DateUtils {
    public static final String DATE_FORMAT_YMDHMS_ = "YYYY-MM-DD HH:mm:ss.S";
    public static final String DATE_FORMAT_YMDHM_ = "YYYY-MM-DD HH:mm";
    public static final String DATE_FORMAT_YMD_ = "YYYY-MM-DD";
    public static final String DATE_FORMAT_YMD = "YYYYMMDD";

    /**
     * 返回当前时间
     * @param format
     * @return
     */
    public static String getCurrDate2Str(String format){
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(new Date());
    }

//    public static Date getCurrentDate(String format){
//        SimpleDateFormat df = new SimpleDateFormat(format);
//        return df.format(new Date());
//    }
}
