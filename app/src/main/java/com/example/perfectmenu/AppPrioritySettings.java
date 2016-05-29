package com.example.perfectmenu;

import android.app.Activity;
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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 유정 on 2016-05-28.
 */
public class AppPrioritySettings extends AppCompatActivity{
    PackageManager myPackageManager;
    LayoutInflater inflater;
    List<ResolveInfo> myInfo;
    Activity act = this;
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
            System.out.println (position + " " + (convertView));
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
            numberPicker.setOnScrollListener(new NumberPicker.OnScrollListener() {
                @Override
                public void onScrollStateChange(NumberPicker view, int scrollState) {
                    view.setValue(scrollState);
                }
            }
            );
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
        listView.setOnItemClickListener(myOnItemClickListener);
    }
    /*@Override
    protected void onDestroy(){
        RecycleUtils.recursiveRecycle(myRecycleList);
        System.gc();
        super.onDestroy();
    }*/
    AdapterView.OnItemClickListener myOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        }
    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        finish();
        return false;
    }
}
