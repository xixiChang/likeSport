package ccc.tcl.com.sprotappui.utils;

import com.alibaba.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeTranslator {

    public static Date stringToDateTime(String para) {
        if (!StringUtils.isBlank(para) && para.length() > 20)
            para = para.substring(0, 20);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(para);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date stringToDate(String para) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(para);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getOldDate(int pastDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, (0 - pastDays));
        return calendar.getTime();
    }

    public static String dateToString(Date date) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        return new SimpleDateFormat(pattern).format(date);
    }


    public static String secToTime(int cnt) {
        int hour = cnt / 3600;
        int min = cnt % 3600 / 60;
        int second = cnt % 60;
        return String.format(Locale.CHINA, "%02d:%02d:%02d", hour, min, second);
    }

    @Deprecated
    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }
}
