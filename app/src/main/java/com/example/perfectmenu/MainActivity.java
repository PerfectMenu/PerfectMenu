package com.example.perfectmenu;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.WindowManager;
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
    int temp = 0, t = 0;
    int unitLength = 90;
    int N = 20;
    public class MyBaseAdapter extends BaseAdapter {
        private Context myContext;
        private List<ResolveInfo> MyAppList;
        private int w, h;
        LayoutInflater inflater;
        MyBaseAdapter (Context c, List<ResolveInfo> I, int _w, int _h){
            myContext = c;
            MyAppList = I;
            w = _w;
            h = _h;
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

            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) getApplicationContext()
                    .getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(metrics);

            final ResolveInfo info = MyAppList.get(position);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0,0,0,0);
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            params.height = unitLength * h;
            params.width = unitLength  * w;
            imageView.setLayoutParams(params);
            imageView.setMaxHeight(1000);
            imageView.setMaxWidth(1000);

            if (h==2) {
                params.height = unitLength * h*2;
                params.width = unitLength  * w*2;
                imageView.setLayoutParams(params);

                //imageView.setPadding(params.height/2,params.height/2,params.height/2,params.height/2);
            }
            if (t==N) t=0;

            TextView textView = (TextView) convertView.findViewById(R.id.textView);
            imageView.setImageDrawable(info.loadIcon(myPackageManager));
            textView.setText(info.loadLabel(myPackageManager).toString());
            //Log.v("[Program]", info.activityInfo.packageName+","+info.activityInfo.name);

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
        intentListPart.addAll(intentList.subList(0,1));

        GridView gridview1 = (GridView) findViewById(R.id.gridview1);
        assert gridview1 != null;
        gridview1.setAdapter(new MyBaseAdapter(this, intentList.subList(0,1), 1, 1));
        gridview1.setOnItemClickListener(myOnItemClickListener);

        GridView gridview2 = (GridView) findViewById(R.id.gridview2);
        assert gridview2 != null;
        gridview2.setAdapter(new MyBaseAdapter(this, intentList.subList(1,2), 1, 1));
        gridview2.setOnItemClickListener(myOnItemClickListener);

        GridView gridview3 = (GridView) findViewById(R.id.gridview3);
        assert gridview3 != null;
        gridview3.setAdapter(new MyBaseAdapter(this, intentList.subList(2,3), 1, 1));
        gridview3.setOnItemClickListener(myOnItemClickListener);

        GridView gridview4 = (GridView) findViewById(R.id.gridview4);
        assert gridview4 != null;
        gridview4.setAdapter(new MyBaseAdapter(this, intentList.subList(3,4), 1, 1));
        gridview4.setOnItemClickListener(myOnItemClickListener);

        GridView gridview5 = (GridView) findViewById(R.id.gridview5);
        assert gridview5 != null;
        gridview5.setAdapter(new MyBaseAdapter(this, intentList.subList(4,5), 1, 1));
        gridview5.setOnItemClickListener(myOnItemClickListener);

        GridView gridview6 = (GridView) findViewById(R.id.gridview6);
        assert gridview6 != null;
        gridview6.setAdapter(new MyBaseAdapter(this, intentList.subList(5,6), 2, 2));
        gridview6.setOnItemClickListener(myOnItemClickListener);

        GridView gridview7 = (GridView) findViewById(R.id.gridview7);
        assert gridview7 != null;
        gridview7.setAdapter(new MyBaseAdapter(this, intentList.subList(6,7), 1, 1));
        gridview7.setOnItemClickListener(myOnItemClickListener);

        GridView gridview8 = (GridView) findViewById(R.id.gridview8);
        assert gridview8 != null;
        gridview8.setAdapter(new MyBaseAdapter(this, intentList.subList(7,8), 1, 1));
        gridview8.setOnItemClickListener(myOnItemClickListener);

        GridView gridview9 = (GridView) findViewById(R.id.gridview9);
        assert gridview9 != null;
        gridview9.setAdapter(new MyBaseAdapter(this, intentList.subList(8,9), 1, 1));
        gridview9.setOnItemClickListener(myOnItemClickListener);

        GridView gridview10 = (GridView) findViewById(R.id.gridview10);
        assert gridview10 != null;
        gridview10.setAdapter(new MyBaseAdapter(this, intentList.subList(9,10), 1, 1));
        gridview10.setOnItemClickListener(myOnItemClickListener);

        GridView gridview11 = (GridView) findViewById(R.id.gridview11);
        assert gridview11 != null;
        gridview11.setAdapter(new MyBaseAdapter(this, intentList.subList(10,11), 1, 1));
        gridview11.setOnItemClickListener(myOnItemClickListener);

        GridView gridview12 = (GridView) findViewById(R.id.gridview12);
        assert gridview12 != null;
        gridview12.setAdapter(new MyBaseAdapter(this, intentList.subList(11,12), 1, 1));
        gridview12.setOnItemClickListener(myOnItemClickListener);

        GridView gridview13 = (GridView) findViewById(R.id.gridview13);
        assert gridview13 != null;
        gridview13.setAdapter(new MyBaseAdapter(this, intentList.subList(12,13), 1, 1));
        gridview13.setOnItemClickListener(myOnItemClickListener);

        GridView gridview14 = (GridView) findViewById(R.id.gridview14);
        assert gridview14 != null;
        gridview14.setAdapter(new MyBaseAdapter(this, intentList.subList(13,14), 1, 1));
        gridview14.setOnItemClickListener(myOnItemClickListener);

        GridView gridview15 = (GridView) findViewById(R.id.gridview15);
        assert gridview15 != null;
        gridview15.setAdapter(new MyBaseAdapter(this, intentList.subList(14,15), 1, 1));
        gridview15.setOnItemClickListener(myOnItemClickListener);

        GridView gridview16 = (GridView) findViewById(R.id.gridview16);
        assert gridview16 != null;
        gridview16.setAdapter(new MyBaseAdapter(this, intentList.subList(15,16), 1, 1));
        gridview16.setOnItemClickListener(myOnItemClickListener);

        GridView gridview17 = (GridView) findViewById(R.id.gridview17);
        assert gridview17 != null;
        gridview17.setAdapter(new MyBaseAdapter(this, intentList.subList(16,17), 1, 1));
        gridview17.setOnItemClickListener(myOnItemClickListener);
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
        intentListPart.addAll(intentList.subList(temp,min(temp+N,intentList.size())));
        temp+=N;
        if (temp>=intentList.size()) temp=0;

        /*GridView gridview1 = (GridView) findViewById(R.id.gridview1);
        assert gridview1 != null;
        gridview1.setAdapter(new MyBaseAdapter(act, intentListPart));*/
        return false;
    }

    private int min(int i, int i1) {
        return i<i1?i:i1;
    }
}
