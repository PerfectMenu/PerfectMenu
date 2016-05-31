package com.example.perfectmenu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dkflf on 2016-05-30.
 */
public class PriorityDatasource {
    private SQLiteDatabase database;
    private PrioritySQLiteHelper dbHelper;
    private String[] allColumns = {PrioritySQLiteHelper.COLUMN_ID, PrioritySQLiteHelper.COLUMN_NAME, PrioritySQLiteHelper.COLUMN_PRIORITY};

    public PriorityDatasource(Context context){
        dbHelper = new PrioritySQLiteHelper(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public Info createInfo(String name, int priority){
        Cursor cursor = null;
        try{
            ContentValues values = new ContentValues();
            values.put(PrioritySQLiteHelper.COLUMN_NAME,name);
            values.put(PrioritySQLiteHelper.COLUMN_PRIORITY,priority);
            long insertId = database.insert(PrioritySQLiteHelper.TABLE_INFOS, null, values);
            //지금 막 insert한 것을 다시 조회해 확인
            cursor = database.query(PrioritySQLiteHelper.TABLE_INFOS, allColumns, PrioritySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
            cursor.moveToFirst();
            return cursorToInfo(cursor);
        }finally{
            closeCursor(cursor);
        }
    }

    public void deleteInfo(Info info){
        long id = info.getId();
        database.delete(PrioritySQLiteHelper.TABLE_INFOS, PrioritySQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public void deleteById(long id){
        database.delete(PrioritySQLiteHelper.TABLE_INFOS, PrioritySQLiteHelper.COLUMN_ID + " = " + id, null);
    }

    public int deleteAllInfos(){
        return database.delete(PrioritySQLiteHelper.TABLE_INFOS, null, null);
    }


    public List<Info> getAllInfos(){
        List<Info> infos = new ArrayList<Info>();
        Cursor cursor = null;
        try{
            cursor = database.query(PrioritySQLiteHelper.TABLE_INFOS, allColumns, null, null, null, null, null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Info info = cursorToInfo(cursor);
                infos.add(info);
                cursor.moveToNext();
            }
            return infos;
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
    private Info cursorToInfo(Cursor cursor){
        Info info = new Info();
        int idIndex = cursor.getColumnIndex(PrioritySQLiteHelper.COLUMN_ID);
        int nameIndex = cursor.getColumnIndex(PrioritySQLiteHelper.COLUMN_NAME);
        int priorityIndex = cursor.getColumnIndex(PrioritySQLiteHelper.COLUMN_PRIORITY);
        info.setId(cursor.getLong(idIndex));
        info.setName(cursor.getString(nameIndex));
        info.setPriority(cursor.getInt(priorityIndex));
        return info;
    }
}
