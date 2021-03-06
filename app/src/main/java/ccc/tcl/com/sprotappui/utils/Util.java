package ccc.tcl.com.sprotappui.utils;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 17-8-8.
 */

public class Util {
    /**
     * @param context
     * @param v
     * @return
     */
    public static Boolean hideInputMethod(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            return imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        return false;
    }

    /**
     * @param v
     * @param event
     * @return
     */
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0], top = leftTop[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(String para) {
        return para == null || "".equals(para);
    }


    public static List<String> stringToList(String para){
        if (isEmpty(para))
            return null;
        para = para.replace("[", "");
        para = para.replace("]", "");
        List<String> result = new ArrayList<>();
        for (String s : para.split(",")){
            result.add(s);
        }
        return result;
    }

    public static String getSpeed(String dis, String time){
        int d = Integer.parseInt(dis);
        if (d == 0)
            return "0'00\"";
        double s = Integer.parseInt(time)/Integer.parseInt(dis);
        int m = (int) Math.floor(s);
        int ss = (int) (60 * (s - m));
        if (ss == 60) {
            m++;
            ss = 0;
        }
        return m + "'" + ss + "\"";
    }

}
