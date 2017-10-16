package ccc.tcl.com.sprotappui.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 引入OKhttp网络请求框架
 */
public class HttpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
            OkHttpClient client=new OkHttpClient();
            Request request=new Request.Builder().url(address).build();
            client.newCall(request).enqueue(callback);
    }
}
