package ccc.tcl.com.sprotappui.activity;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by user on 17-9-18.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("",null);
        db.execSQL("CREATE TABLE IF NOT EXISTS sport(sid integer primary key autoincrement," +
                "name varchar(20)," +
                "start_time varchar(20)," +
                "end_time varchar(20)," +
                "image_url varchar(50)," +
                ")");
    }
}
