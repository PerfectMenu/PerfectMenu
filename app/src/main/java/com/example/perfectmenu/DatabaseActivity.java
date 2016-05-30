package com.example.perfectmenu;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Created by dkflf on 2016-05-30.
 */
public class DatabaseActivity extends ListActivity {
    final public static String TAG = "Database";
    private PriorityDatasource datasource;
    private EditText mInfoET;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_activity);
        datasource = new PriorityDatasource(this);
        datasource.open();
    }

}
