package com.example.perfectmenu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dkflf on 2016-05-30.
 */
public class PrioritySQLiteHelper extends SQLiteOpenHelper{
    public static final String TABLE_INFOS = "infos";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRIORITY = "priority";

    private static final String DATABASE_NAME = "infos.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "create table " + TABLE_INFOS + " ( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NAME
            + " text not null, " + COLUMN_PRIORITY + " text not null);";

    public PrioritySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFOS);
        onCreate(db);
    }
}
