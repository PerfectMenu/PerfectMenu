package com.example.perfectmenu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dkflf on 2016-05-31.
 */
public class ClassifySQLiteHelper  extends SQLiteOpenHelper {
    public static final String TABLE_CLASSIFY = "classifyInfo";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CLASSIFY = "classify";

    private static final String DATABASE_NAME = "classify.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "create table " + TABLE_CLASSIFY + " ( " + COLUMN_ID
                    + " integer primary key autoincrement, " + COLUMN_CLASSIFY
                    + " text not null);";

    public ClassifySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASSIFY);
        onCreate(db);
    }
}
