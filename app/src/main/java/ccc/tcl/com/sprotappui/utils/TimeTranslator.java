package ccc.tcl.com.sprotappui.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeTranslator {

    public static Date stringToDateTime(String para){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
             date = simpleDateFormat.parse(para);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date stringToDate(String para){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(para);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getOldDate(int pastDays){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, (0-pastDays));
        return calendar.getTime();
    }

    public static String dateToString(Date date){
        String pattern = "yyyy-MM-dd HH:mm:ss";
        return new SimpleDateFormat(pattern).format(date);
    }
}
