package ccc.tcl.com.sprotappui.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import ccc.tcl.com.sprotappui.data.UserInfo;

import static ccc.tcl.com.sprotappui.db.SQLStatement.DBName;

/**
 * Created by user on 17-9-20.
 */

public class SQLParaWrapper {
    private static final String TAG = "SQLParaWrapper";

    private int DBVersion = 1;

    private AppDBHelper appDBHelper;
    public SQLiteDatabase sqLiteDatabase;

    public SQLParaWrapper(Context context) {
        appDBHelper = new AppDBHelper(context, DBName, null, DBVersion);
        try {
            sqLiteDatabase = appDBHelper.getWritableDatabase();
        }catch (SQLiteException e){
            Log.e(TAG, "SQLParaWrapper: " + e.getMessage() );
        }catch (Exception e){
            Log.e(TAG, "SQLParaWrapper: " + e.getMessage() );
        }
    }

    /**
     * id,pwd,phone,name,session,image_url,im_uid,login_method
     * @param userinfo
     * @return
     */
    public Object[] getUserStringArray(UserInfo userinfo){
        return new Object[]{
                userinfo.getId(),
                userinfo.getPassword(),
                userinfo.getPhone(),
                userinfo.getName(),
                userinfo.getSession(),
                userinfo.getImage_url(),
                userinfo.getIm_uid(),
                userinfo.getLogin_method(),
                userinfo.getRetain()
        };
    }

    public Object[] getUpdateUserStringArray(UserInfo userInfo){
        return new Object[]{
                userInfo.getImage_url(),
                userInfo.getName(),
                userInfo.getRetain(),
                userInfo.getId()
        };
    }
}
