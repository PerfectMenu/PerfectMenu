package com.example.perfectmenu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by dkflf on 2016-05-31.
 */
public class ClassifyDatasource {
    private SQLiteDatabase database;
    private ClassifySQLiteHelper dbHelper;
    private String[] allColumns = {ClassifySQLiteHelper.COLUMN_ID, ClassifySQLiteHelper.COLUMN_CLASSIFY};

    public ClassifyDatasource(Context context){
        dbHelper = new ClassifySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void createInfo(int classify){
        Cursor cursor = null;
        try{
            ContentValues values = new ContentValues();
            values.put(ClassifySQLiteHelper.COLUMN_CLASSIFY,classify);
            long insertId = database.insert(ClassifySQLiteHelper.TABLE_CLASSIFY, null, values);
            System.out.println("Classify insertId = " + insertId);
            //지금 막 insert한 것을 다시 조회해 확인
            cursor = database.query(ClassifySQLiteHelper.TABLE_CLASSIFY, allColumns, ClassifySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
            cursor.moveToFirst();
        }finally{
            closeCursor(cursor);
        }
    }

    public void deleteInfo(Info info){
        long id = info.getId();
        database.delete(ClassifySQLiteHelper.TABLE_CLASSIFY, ClassifySQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public void deleteById(long id){
        database.delete(ClassifySQLiteHelper.TABLE_CLASSIFY, ClassifySQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public void deleteAllInfos(){
        database.delete(ClassifySQLiteHelper.TABLE_CLASSIFY, null, null);

        /*database.execSQL("DROP TABLE IF EXISTS " + ClassifySQLiteHelper.TABLE_CLASSIFY);
        dbHelper.onCreate(database);*/
    }


    public int getAllInfos(){
        Cursor cursor = null;
        try{
            cursor = database.query(ClassifySQLiteHelper.TABLE_CLASSIFY, allColumns, null, null, null, null, null);
            if (cursor.moveToFirst()) return cursorToInteger(cursor);
            return 0;
        }finally{
            closeCursor(cursor);
        }
    }

    private void closeCursor(Cursor cursor){
        try {
            if (cursor!=null){
                cursor.close();
            }
        } catch (Exception e) {

        }
    }
    private int cursorToInteger(Cursor cursor){
        return cursor.getInt(cursor.getColumnIndex(ClassifySQLiteHelper.COLUMN_CLASSIFY));
    }
}

