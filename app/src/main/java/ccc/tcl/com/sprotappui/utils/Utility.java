package ccc.tcl.com.sprotappui.utils;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 *JSON解析返还数据
 * 获取空气质量
 */
public class Utility {
    public static String handleAqiResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONObject heWeather5Object = new JSONObject(response);
                JSONArray allHeWeather5S = heWeather5Object.getJSONArray("HeWeather5");
                JSONObject city=allHeWeather5S.getJSONObject(0).getJSONObject("aqi")
                        .getJSONObject("city");
                Object qlty=city.get("qlty");
//
                String result=qlty.toString();
                return  result;
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
        return null;
    }
    public static List<String> handleNowResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONObject heWeather5Object = new JSONObject(response);
                JSONArray allHeWeather5S = heWeather5Object.getJSONArray("HeWeather5");
                JSONObject now=allHeWeather5S.getJSONObject(0).getJSONObject("now");
                JSONObject  cond=now.getJSONObject("cond");

                String txt=cond.get("txt").toString();
                String tmp=now.get("tmp").toString();
                List<String>result=new ArrayList<>();
                result.add( txt );
                result.add( tmp );
                return result;
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
        return null;
    }
}



