package ccc.tcl.com.sprotappui.customui;

import android.support.v4.app.Fragment;

import com.alibaba.mobileim.aop.Pointcut;
import com.alibaba.mobileim.aop.custom.IMConversationListUI;

/**
 * Created by user on 17-10-9.
 */

public class CustomConversationListUI extends IMConversationListUI {

    public CustomConversationListUI(Pointcut pointcut) {
        super(pointcut);
    }


    public boolean needHideTitleView(Fragment fragment){
        return true;
    }
}
