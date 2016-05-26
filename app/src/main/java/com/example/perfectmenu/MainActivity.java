package com.example.perfectmenu;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.content.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    PackageManager myPackageManager;
    Activity act = this;
    int temp = 0;
    public class MyBaseAdapter extends BaseAdapter {
        private Context myContext;
        private List<ResolveInfo> MyAppList;
        LayoutInflater inflater;
        MyBaseAdapter (Context c, List<ResolveInfo> I){
            myContext = c;
            MyAppList = I;
            inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount(){
            return MyAppList.size();
        }
        @Override
        public Object getItem(int position){
            return MyAppList.get(position);
        }
        @Override
        public long getItemId(int position){
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if (convertView == null){
                convertView = inflater.inflate(R.layout.item, parent, false);
            }
            final ResolveInfo info = MyAppList.get(position);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8,8,8,8);
            TextView textView = (TextView) convertView.findViewById(R.id.textView);
            imageView.setImageDrawable(info.loadIcon(myPackageManager));
            textView.setText(info.loadLabel(myPackageManager).toString());
            Log.v("[Program]", info.activityInfo.packageName+","+info.activityInfo.name);

            imageView.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_RUN);
                    intent.setComponent(new ComponentName(
                            info.activityInfo.packageName,
                            info.activityInfo.name));
                    act.startActivity(intent);
                    String msg = info.activityInfo.packageName+", "+info.activityInfo.name;
                    Toast.makeText(act,msg,Toast.LENGTH_LONG).show();
                }
            });

            return convertView;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myPackageManager = getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> intentList = getPackageManager().queryIntentActivities(intent, 0);
        List<ResolveInfo> intentListPart = new ArrayList<ResolveInfo>();
        intentListPart.addAll(intentList.subList(1,10));

        GridView gridview = (GridView) findViewById(R.id.gridview);
        assert gridview != null;
        gridview.setAdapter(new MyBaseAdapter(this, intentListPart));

        gridview.setOnItemClickListener(myOnItemClickListener);

        /*Button button = (Button) findViewById(R.id.button);
        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        })*/;
    }

    OnItemClickListener myOnItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ResolveInfo clickedResolveInfo = (ResolveInfo)parent.getItemAtPosition(position);
            ActivityInfo clickedActivityInfo = clickedResolveInfo.activityInfo;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setClassName(
                    clickedActivityInfo.applicationInfo.packageName,
                    clickedActivityInfo.name);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            startActivity(intent);
        }
    };
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> intentList = getPackageManager().queryIntentActivities(intent, 0);
        List<ResolveInfo> intentListPart = new ArrayList<ResolveInfo>();
        intentListPart.addAll(intentList.subList(temp,min(temp+10,intentList.size())));
        temp+=10;
        if (temp>=intentList.size()) temp=0;

        GridView gridview = (GridView) findViewById(R.id.gridview);
        assert gridview != null;
        gridview.setAdapter(new MyBaseAdapter(act, intentListPart));
        return false;
    }

    private int min(int i, int i1) {
        return i<i1?i:i1;
    }
}
