package ccc.tcl.com.sprotappui.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by user on 17-9-19.
 */

public class AppDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "AppDBHelper";

    public AppDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: ");
        try {
            db.execSQL(SQLStatement.CREATE_TABLE_USER);
            db.execSQL(SQLStatement.CREATE_TABLE_SPORT_RECORD);
            db.execSQL(SQLStatement.CREATE_TABLE_ACTIVITY);
        }catch (SQLException e){
            Log.e(TAG, "onCreate: " + e.getMessage() );
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
