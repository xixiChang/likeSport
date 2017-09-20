package ccc.tcl.com.sprotappui;

import android.app.Application;

//import com.alibaba.mobileim.YWAPI;
//import com.alibaba.wxlib.util.SysUtil;

import ccc.tcl.com.sprotappui.data.UserInfo;

import static ccc.tcl.com.sprotappui.constant.URLConstant.BAICHUAN_APP_KEY;


/**
 * Created by user on 17-9-14.
 */

public class App extends Application {
    public static UserInfo userInfo;

    @Override
    public void onCreate() {
        super.onCreate();
//        SysUtil.setApplication(this);
//
//        if(SysUtil.isTCMSServiceProcess(this)) {
//            return;
//        }
//        if(SysUtil.isMainProcess()){
//            YWAPI.init(this, BAICHUAN_APP_KEY);
//        }
    }
}
