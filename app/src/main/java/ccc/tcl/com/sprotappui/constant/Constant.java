package ccc.tcl.com.sprotappui.constant;

import java.util.HashMap;
import java.util.Map;

import ccc.tcl.com.sprotappui.R;

/**
 * Created by user on 17-9-20.
 */

public class Constant {
    public static final int Activity_Status_Normal = 0;
    public static final int Activity_Status_Delay = 1;
    public static final int Activity_Status_Done = 2;
    public static final int Activity_Status_Cancel = 3;
    public static Map<String,Integer> weatherMap=new HashMap<>();
    static {
        weatherMap.put("多云", R.mipmap.w1);
        weatherMap.put("小雨", R.mipmap.w2);
        weatherMap.put("大风", R.mipmap.w3);
        weatherMap.put("大雨", R.mipmap.w4);
        weatherMap.put("中雨", R.mipmap.w5);
        weatherMap.put("无", R.mipmap.w6);
        weatherMap.put("晴", R.mipmap.w7);
        weatherMap.put("冰雹", R.mipmap.w8);
        weatherMap.put("沙尘暴", R.mipmap.w9);
        weatherMap.put("小雪", R.mipmap.w10);
        weatherMap.put("中雪", R.mipmap.w11);
        weatherMap.put("大雪", R.mipmap.w12);
        weatherMap.put("暴雨", R.mipmap.w13);
        weatherMap.put("晴转多云", R.mipmap.w14);
        weatherMap.put("暴雪", R.mipmap.w15);
        weatherMap.put("阴", R.mipmap.w16);
        weatherMap.put("雾", R.mipmap.w17);
        weatherMap.put("雾霾", R.mipmap.w18);
        weatherMap.put("雨夹雪", R.mipmap.w19);
        weatherMap.put("阵雨", R.mipmap.w20);
    }
}
