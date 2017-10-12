package ccc.tcl.com.sprotappui;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.aop.AdviceBinder;
import com.alibaba.mobileim.aop.PointCutEnum;
import com.alibaba.wxlib.util.SysUtil;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import ccc.tcl.com.sprotappui.customui.CustomConversationListUI;
import ccc.tcl.com.sprotappui.data.UserInfo;

import static ccc.tcl.com.sprotappui.constant.URLConstant.BAICHUAN_APP_KEY;


/**
 * Created by user on 17-9-14.
 */

public class App extends MultiDexApplication {
    public static UserInfo userInfo = new UserInfo();
    private static final String NO_USER = "-1";
    private static final String SHARE_MAP = "UserInfo";
    public static boolean cameraPermission = false;
    public static boolean vibratePermission = false;
    public static boolean storagePermission = false;
    public static boolean notificationPermission = false;
    public static boolean voicePermission = false;
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
        initUmeng();

        TwinklingRefreshLayout.setDefaultHeader(SinaRefreshView.class.getName());
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
            AdviceBinder.bindAdvice(PointCutEnum.CONVERSATION_FRAGMENT_UI_POINTCUT,
                    CustomConversationListUI.class);
        }
    }
    /**
     * 初始化umeng
     */
    private void initUmeng(){
        PlatformConfig.setWeixin("wx615464c5b6f30c45", "2d11135893139930c161b102ae7a1f0f");
        PlatformConfig.setQQZone("wx615464c5b6f30c45", "2d11135893139930c161b102ae7a1f0f");
        UMShareAPI.get(this);
    }
}
