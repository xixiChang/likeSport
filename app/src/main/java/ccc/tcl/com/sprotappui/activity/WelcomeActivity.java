package ccc.tcl.com.sprotappui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ccc.tcl.com.sprotappui.App;
import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.db.SQLParaWrapper;
import ccc.tcl.com.sprotappui.db.SQLStatement;
import ccc.tcl.com.sprotappui.service.IMService;

import static ccc.tcl.com.sprotappui.App.userInfo;

public class WelcomeActivity extends AppCompatActivity {
    public static final int JUMP_TO_HOME = 2603;
    public static final int JUMP_TO_Login = 2604;

    private static final int WELCOME_DELAY_TIME = 3000;

    private SQLParaWrapper sqlParaWrapper;
    private static final String TAG = "WelcomeActivity";


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == JUMP_TO_HOME){
                Intent intent = new Intent(WelcomeActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Thread(dbRunnable).start();
    }


    private Runnable dbRunnable = new Runnable() {
        @Override
        public void run() {
            int msgWhat = JUMP_TO_Login;
            //*****************************************
            long startTime = System.currentTimeMillis();

            if (App.userInfo.getId() != null){
                sqlParaWrapper = new SQLParaWrapper(WelcomeActivity.this);
                setUserInfoFromDB();
                if (App.userInfo.getSession() != null){
                    msgWhat = JUMP_TO_HOME;
                }
                new IMService();
            }

            //*****************************************
            long endTime = System.currentTimeMillis();

            long diff = endTime-startTime;
            Log.d(TAG, "run: >>>>>>>>> diff:" + diff);
            if (diff < WELCOME_DELAY_TIME){
                try {
                    Thread.sleep(WELCOME_DELAY_TIME - diff);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            handler.sendEmptyMessage(msgWhat);
        }
    };

    private void setUserInfoFromDB() {
        Cursor cursor = sqlParaWrapper.sqLiteDatabase.rawQuery(SQLStatement.GetUserByID,
                new String[]{userInfo.getId()});
        if (cursor.moveToFirst()) {
            do {
                userInfo.setPassword(cursor.getString(cursor.getColumnIndex("pwd")));
                userInfo.setName(cursor.getString(cursor.getColumnIndex("name")));
                userInfo.setSession(cursor.getString(cursor.getColumnIndex("session")));
                userInfo.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                userInfo.setIm_uid(cursor.getString(cursor.getColumnIndex("im_uid")));
                userInfo.setImage_url(cursor.getString(cursor.getColumnIndex("image_url")));
                userInfo.setLogin_method(cursor.getInt(cursor.getColumnIndex("login_method")));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }


}
