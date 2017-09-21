package ccc.tcl.com.sprotappui.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 17-9-20.
 */

public class BaseData {
    private static final String SHARE_MAP = "UserInfo";
    private SharedPreferences.Editor editor;

    public BaseData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARE_MAP,
                Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void updateUserID(String id){
        editor.putString("current_user_id", id);
        editor.apply();
    }
}
