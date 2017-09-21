package ccc.tcl.com.sprotappui.service;

import android.util.Log;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;

import java.io.IOException;

import ccc.tcl.com.sprotappui.App;
import ccc.tcl.com.sprotappui.constant.URLConstant;

/**
 * Created by user on 17-9-18.
 */

public class IMService {
    public static YWIMKit mIMKit;
    private static final String TAG = "IMService";

    public IMService(){
        try {
            mIMKit = YWAPI.getIMKitInstance(App.userInfo.getIm_uid(), URLConstant.BAICHUAN_APP_KEY);
        }catch (Exception e){
            Log.d(TAG, "IMService: " + e.getMessage());
        }
    }
}
