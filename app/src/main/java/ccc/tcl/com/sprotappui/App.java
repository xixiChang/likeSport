package ccc.tcl.com.sprotappui;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

//import com.alibaba.mobileim.YWAPI;
//import com.alibaba.wxlib.util.SysUtil;

import ccc.tcl.com.sprotappui.activity.HomeActivity;
import ccc.tcl.com.sprotappui.activity.LoginActivity;
import ccc.tcl.com.sprotappui.data.UserInfo;

import static ccc.tcl.com.sprotappui.constant.URLConstant.BAICHUAN_APP_KEY;


/**
 * Created by user on 17-9-14.
 */

public class App extends Application {
    public static UserInfo userInfo = new UserInfo();
    private static final String NO_USER = "-1";
    private static final String SHARE_MAP = "UserInfo";

    @Override
    public void onCreate() {
        super.onCreate();
        initApplication();
    }

    private void initApplication() {
        SharedPreferences s = this.getSharedPreferences(SHARE_MAP, Context.MODE_PRIVATE);
        String userID = getBaseData(s);
        if (!userID.equals(NO_USER)){
            userInfo.setId(userID);
        }
        initIMKit();
    }

    private String getBaseData(SharedPreferences s) {
        return s.getString("current_user_id", NO_USER);
    }

    /**
     * 初始化即时通讯组件
     */
    private void initIMKit() {
        SysUtil.setApplication(this);
        if (SysUtil.isTCMSServiceProcess(this)) {
            return;
        }
        if (SysUtil.isMainProcess()) {
            YWAPI.init(this, BAICHUAN_APP_KEY);
        }
    }
}
