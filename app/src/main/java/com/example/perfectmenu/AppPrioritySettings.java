package com.example.perfectmenu;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
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

import java.util.ArrayList;
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
    List<Integer> priorityList;
    NumberPicker numberPicker = null;

    public class SettingBaseAdapter extends BaseAdapter {
        int pos;
        ViewGroup saveParent;
        SettingBaseAdapter(List<ResolveInfo> I, List<Integer> p) {
            myInfo = I;
            priorityList = p;
            inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return myInfo.size();
        }
        @Override
        public Object getItem(int position) {
            //return myInfo.get(position);
            View view = inflater.inflate(R.layout.application_priority, saveParent, false);
            return ((NumberPicker) view.findViewById(R.id.app_priority)).getValue();
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            saveParent = parent;
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
            numberPicker.setTag(position);
            numberPicker.setMinValue(0);
            numberPicker.setMaxValue(5);
            numberPicker.setValue(priorityList.get(position));
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    priorityList.set((Integer) picker.getTag(), newVal);
                    //System.out.println("picker's value is " + picker.getValue());
                }
            });
            return convertView;
        }
    }

    public PriorityDatasource getPriorityDB(){
        return datasource;
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
        //listView.setOnItemClickListener(myOnItemClickListener);

        List<Integer> pL = new ArrayList<Integer>();
        datasource = new PriorityDatasource(this);
        datasource.open();
        List<Info> values = datasource.getAllInfos();
        System.out.println("Create");
        if (values.size()!=intentList.size()){
            System.out.println("Formatting database");
            datasource.deleteAllInfos();
            for (ResolveInfo info : intentList){
                datasource.createInfo(info.loadLabel(myPackageManager).toString(), 0);
            }
        }
    }
    @Override
    protected void onResume(){
        System.out.println("onResume");
        datasource.open();

        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> intentList = getPackageManager().queryIntentActivities(intent, 0);
        List<Integer> pL = new ArrayList<Integer>();
        List<Info> values = datasource.getAllInfos();
        ListView listView = (ListView) findViewById(R.id.listView);
        assert listView != null;

        System.out.println("Load database @ Resume");
        values = datasource.getAllInfos();
        for (Info info : values) pL.add(0);
        for (Info info : values){
            int t=-1;
            for (ResolveInfo resolveInfo : intentList){
                t++;
                if (resolveInfo.loadLabel(myPackageManager).toString().equals(info.getName())){
                    pL.set(t,info.getPriority());
                    break;
                }
            }
        }

        listView.setAdapter(new SettingBaseAdapter(intentList, pL));

        super.onResume();
    }

    @Override
    protected void onPause(){
        System.out.println("onPause");
        datasource.close();
        super.onPause();
    }

    private void save() {
        System.out.println("Save");
        ListView listView = (ListView) findViewById(R.id.listView);
        assert listView != null;
        datasource.deleteAllInfos();
        int i=0;
        for (ResolveInfo info : myInfo){
            datasource.createInfo(info.loadLabel(myPackageManager).toString(), priorityList.get(i));
            i++;
        }
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
        System.out.println("onKeyDown");
        save();
        finish();
        return false;
    }
}