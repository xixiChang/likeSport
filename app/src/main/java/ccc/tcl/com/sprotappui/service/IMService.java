package ccc.tcl.com.sprotappui.service;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;

import ccc.tcl.com.sprotappui.App;
import ccc.tcl.com.sprotappui.constant.URLConstant;

/**
 * Created by user on 17-9-18.
 */

public class IMService {
    public static  YWIMKit mIMKit;
    public IMService(){
        mIMKit = YWAPI.getIMKitInstance(App.userInfo.getIm_uid(), URLConstant.BAICHUAN_APP_KEY);
    }
}
