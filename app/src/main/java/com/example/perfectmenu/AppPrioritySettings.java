package com.example.perfectmenu;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 유정 on 2016-05-28.
 */
public class AppPrioritySettings extends AppCompatActivity{
    final public static String TAG = "Database";
    private PriorityDatasource datasource;
    //private EditText mInfoET;

    PackageManager myPackageManager;
    LayoutInflater inflater;
    List<ResolveInfo> myInfo;
    Activity act = this;
    NumberPicker numberPicker = null;

    public class SettingBaseAdapter extends BaseAdapter {
        SettingBaseAdapter(List<ResolveInfo> I) {
            myInfo = I;
            inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return myInfo.size();
        }
        @Override
        public Object getItem(int position) {
            return myInfo.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = inflater.inflate(R.layout.application_priority, parent, false);
            }
            ResolveInfo item = myInfo.get(position);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.app_icon);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0,0,0,0);
            TextView textView = (TextView) convertView.findViewById(R.id.app_name);
            imageView.setImageDrawable(item.loadIcon(myPackageManager));
            textView.setText(item.loadLabel(myPackageManager).toString());
            NumberPicker numberPicker = (NumberPicker) convertView.findViewById(R.id.app_priority);
            numberPicker.setMinValue(0);
            numberPicker.setMaxValue(5);
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_list);
        myPackageManager = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> intentList = getPackageManager().queryIntentActivities(intent, 0);
        ListView listView = (ListView) findViewById(R.id.listView);
        assert listView != null;
        listView.setAdapter(new SettingBaseAdapter(intentList));
        //listView.setOnItemClickListener(myOnItemClickListener);

        List<Info> values = datasource.getAllInfos();
        datasource = new PriorityDatasource(this);
        datasource.open();
        //List<Info> infos = datasource.getAllInfos();
        //ArrayAdapter<Info> adapter = new ArrayAdapter<Info>(this, android.R.layout.simple_list_item_1, values);

        //setListAdapter(adapter);

        //mInfoET = (EditText)findViewById(R.id.editText);

    }
    @Override
    protected void onResume(){
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause(){
        datasource.close();
        super.onPause();
    }

    /*@Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
        ArrayAdapter<Info> adapter = (ArrayAdapter<Info>) getListAdapter();
        Info info = adapter.getItem(position);
        datasource.deleteInfo(info);
        adapter.remove(info);
        adapter.notifyDataSetChanged();
    }*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        finish();
        return false;
    }
}