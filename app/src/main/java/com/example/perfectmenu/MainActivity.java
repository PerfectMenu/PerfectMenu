package com.example.perfectmenu;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    PackageManager myPackageManager;
    Activity act = this;
    int temp = 0, t = 0;
    int unitLength = 150;
    GestureDetector gestureDetector;
    List<ResolveInfo> intentList;
    List<AppInfo> infoList = null;
    int N = 17;

    //private AppPrioritySettings priorityDB;

    //List<WeakReference<View>> myRecycleList = new ArrayList<>();
    GridView gridview1, gridview2, gridview3, gridview4, gridview5, gridview6, gridview7,
            gridview8, gridview9, gridview10, gridview11, gridview12, gridview13, gridview14, gridview15, gridview16, gridview17;

    GridView[] gridview = {gridview6,gridview12,gridview11,gridview7,gridview15,gridview9,gridview13,gridview16,gridview17,gridview14,gridview10,gridview8,gridview4,gridview3,gridview5,gridview2,gridview1};
    int[] idx = {6,12,11,7,15,9,13,16,17,14,10,8,4,3,5,2,1};

    public class MyBaseAdapter extends BaseAdapter {
        private Context myContext;
        private List<ResolveInfo> MyAppList;
        private int w, h;
        LayoutInflater inflater;
        private List<WeakReference<View>> myRecycleList;
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
                params.height = (int)(unitLength * h*1.5);
                params.width = (int)(unitLength  * w*1.5 );
                imageView.setLayoutParams(params);

                //imageView.setPadding(params.height/2,params.height/2,params.height/2,params.height/2);
            }
            if (t==N) t=0;

            TextView textView = (TextView) convertView.findViewById(R.id.textView);
            imageView.setImageDrawable(info.loadIcon(myPackageManager));
            textView.setText(info.loadLabel(myPackageManager).toString());
            //Log.v("[Program]", info.activityInfo.packageName+","+info.activityInfo.name);
            //myRecycleList.add(new WeakReference<View>(imageView));
            //myRecycleList.add(new WeakReference<View>(textView));
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
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        String text = null;
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent1 = new Intent(getApplicationContext(), AppPrioritySettings.class);
                startActivity(intent1);
                break;
            case R.id.item2:
                Intent intent2 = new Intent(getApplicationContext(), ClassifyRecommendation.class);
                startActivity(intent2);
                break;
            case R.id.item3:
                text = "C";
                break;
            default:
                return false;
        }
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myPackageManager = getPackageManager();
        gestureDetector = new GestureDetector(this, myOnGestureListener);
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);


        intentList = getPackageManager().queryIntentActivities(intent, 0);
        //List<Info> pl = AppPrioritySettings.getPriorityDB();
        infoList=new ArrayList<AppInfo>();
        for (int i=0;i<intentList.size();i++){
            //infoList.add(new AppInfo(intentList.get(i),intentList.get(i).loadLabel(myPackageManager).toString(),pl.get(i).getPriority()));
            infoList.add(new AppInfo(intentList.get(i),intentList.get(i).loadLabel(myPackageManager).toString(),0));
        }
        Collections.sort(infoList, AppInfo.COMPARATOR);
        intentList = new ArrayList<ResolveInfo>();
        for (int i=0;i<infoList.size();i++){
            intentList.add(infoList.get(i).info);
        }


        gridview1 = (GridView) findViewById(R.id.gridview1);
        assert gridview1 != null;
        gridview1.setAdapter(new MyBaseAdapter(this, intentList.subList(0,1), 1, 1));
        gridview1.setOnItemClickListener(myOnItemClickListener);

        gridview2 = (GridView) findViewById(R.id.gridview2);
        assert gridview2 != null;
        gridview2.setAdapter(new MyBaseAdapter(this, intentList.subList(1,2), 1, 1));
        gridview2.setOnItemClickListener(myOnItemClickListener);

        gridview3 = (GridView) findViewById(R.id.gridview3);
        assert gridview3 != null;
        gridview3.setAdapter(new MyBaseAdapter(this, intentList.subList(2,3), 1, 1));
        gridview3.setOnItemClickListener(myOnItemClickListener);

        gridview4 = (GridView) findViewById(R.id.gridview4);
        assert gridview4 != null;
        gridview4.setAdapter(new MyBaseAdapter(this, intentList.subList(3,4), 1, 1));
        gridview4.setOnItemClickListener(myOnItemClickListener);

        gridview5 = (GridView) findViewById(R.id.gridview5);
        assert gridview5 != null;
        gridview5.setAdapter(new MyBaseAdapter(this, intentList.subList(4,5), 1, 1));
        gridview5.setOnItemClickListener(myOnItemClickListener);

        gridview6 = (GridView) findViewById(R.id.gridview6);
        assert gridview6 != null;
        gridview6.setAdapter(new MyBaseAdapter(this, intentList.subList(5,6), 2, 2));
        gridview6.setOnItemClickListener(myOnItemClickListener);

        gridview7 = (GridView) findViewById(R.id.gridview7);
        assert gridview7 != null;
        gridview7.setAdapter(new MyBaseAdapter(this, intentList.subList(6,7), 1, 1));
        gridview7.setOnItemClickListener(myOnItemClickListener);

        gridview8 = (GridView) findViewById(R.id.gridview8);
        assert gridview8 != null;
        gridview8.setAdapter(new MyBaseAdapter(this, intentList.subList(7,8), 1, 1));
        gridview8.setOnItemClickListener(myOnItemClickListener);

        gridview9 = (GridView) findViewById(R.id.gridview9);
        assert gridview9 != null;
        gridview9.setAdapter(new MyBaseAdapter(this, intentList.subList(8,9), 1, 1));
        gridview9.setOnItemClickListener(myOnItemClickListener);

        gridview10 = (GridView) findViewById(R.id.gridview10);
        assert gridview10 != null;
        gridview10.setAdapter(new MyBaseAdapter(this, intentList.subList(9,10), 1, 1));
        gridview10.setOnItemClickListener(myOnItemClickListener);

        gridview11 = (GridView) findViewById(R.id.gridview11);
        assert gridview11 != null;
        gridview11.setAdapter(new MyBaseAdapter(this, intentList.subList(10,11), 1, 1));
        gridview11.setOnItemClickListener(myOnItemClickListener);

        gridview12 = (GridView) findViewById(R.id.gridview12);
        assert gridview12 != null;
        gridview12.setAdapter(new MyBaseAdapter(this, intentList.subList(11,12), 1, 1));
        gridview12.setOnItemClickListener(myOnItemClickListener);

        gridview13 = (GridView) findViewById(R.id.gridview13);
        assert gridview13 != null;
        gridview13.setAdapter(new MyBaseAdapter(this, intentList.subList(12,13), 1, 1));
        gridview13.setOnItemClickListener(myOnItemClickListener);

        gridview14 = (GridView) findViewById(R.id.gridview14);
        assert gridview14 != null;
        gridview14.setAdapter(new MyBaseAdapter(this, intentList.subList(13,14), 1, 1));
        gridview14.setOnItemClickListener(myOnItemClickListener);

        gridview15 = (GridView) findViewById(R.id.gridview15);
        assert gridview15 != null;
        gridview15.setAdapter(new MyBaseAdapter(this, intentList.subList(14,15), 1, 1));
        gridview15.setOnItemClickListener(myOnItemClickListener);

        gridview16 = (GridView) findViewById(R.id.gridview16);
        assert gridview16 != null;
        gridview16.setAdapter(new MyBaseAdapter(this, intentList.subList(15,16), 1, 1));
        gridview16.setOnItemClickListener(myOnItemClickListener);

        gridview17 = (GridView) findViewById(R.id.gridview17);
        assert gridview17 != null;
        gridview17.setAdapter(new MyBaseAdapter(this, intentList.subList(16,17), 1, 1));
        gridview17.setOnItemClickListener(myOnItemClickListener);
    }
    /*@Override
    protected void onDestroy(){
        RecycleUtils.recursiveRecycle(myRecycleList);
        System.gc();
        super.onDestroy();
    }*/

    GestureDetector.OnGestureListener myOnGestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }
        @Override
        public void onShowPress(MotionEvent e) {
        }
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }
        @Override
        public void onLongPress(MotionEvent e) {
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            List<ResolveInfo> intentListPart = new ArrayList<ResolveInfo>();
            /* same page always show same apps */
            /*------------------------------------------------------------------------------------------------*/
            if (velocityX > 0) {
                temp -= N;
            } else if (velocityX < 0) {
                temp += N;
            }
            if (temp >= intentList.size() - 1) temp -= N; // 만약 환형 page 형식이라면 temp=0
            if (temp <= 0) temp = 0; // 만약 환형 page 형식이라면 temp=(int)((intentList.size() - 1)/N) * N
            intentListPart.addAll(intentList.subList(temp, min(temp + N, intentList.size() - 1)));
            /*------------------------------------------------------------------------------------------------*/
            if (intentListPart.size()>=1) gridview1.setAdapter(new MyBaseAdapter(act, intentListPart.subList(0, 1), 1, 1));
            else gridview1.setAdapter(new MyBaseAdapter(act, new ArrayList<ResolveInfo>(), 1, 1));
            if (intentListPart.size()>=2) gridview2.setAdapter(new MyBaseAdapter(act, intentListPart.subList(1, 2), 1, 1));
            else gridview2.setAdapter(new MyBaseAdapter(act, new ArrayList<ResolveInfo>(), 1, 1));
            if (intentListPart.size()>=3) gridview3.setAdapter(new MyBaseAdapter(act, intentListPart.subList(2, 3), 1, 1));
            else gridview3.setAdapter(new MyBaseAdapter(act, new ArrayList<ResolveInfo>(), 1, 1));
            if (intentListPart.size()>=4) gridview4.setAdapter(new MyBaseAdapter(act, intentListPart.subList(3, 4), 1, 1));
            else gridview4.setAdapter(new MyBaseAdapter(act, new ArrayList<ResolveInfo>(), 1, 1));
            if (intentListPart.size()>=5) gridview5.setAdapter(new MyBaseAdapter(act, intentListPart.subList(4, 5), 1, 1));
            else gridview5.setAdapter(new MyBaseAdapter(act, new ArrayList<ResolveInfo>(), 1, 1));
            if (intentListPart.size()>=6) gridview6.setAdapter(new MyBaseAdapter(act, intentListPart.subList(5, 6), 2, 2));
            else gridview6.setAdapter(new MyBaseAdapter(act, new ArrayList<ResolveInfo>(), 1, 1));
            if (intentListPart.size()>=7) gridview7.setAdapter(new MyBaseAdapter(act, intentListPart.subList(6, 7), 1, 1));
            else gridview7.setAdapter(new MyBaseAdapter(act, new ArrayList<ResolveInfo>(), 1, 1));
            if (intentListPart.size()>=8) gridview8.setAdapter(new MyBaseAdapter(act, intentListPart.subList(7, 8), 1, 1));
            else gridview8.setAdapter(new MyBaseAdapter(act, new ArrayList<ResolveInfo>(), 1, 1));
            if (intentListPart.size()>=9) gridview9.setAdapter(new MyBaseAdapter(act, intentListPart.subList(8, 9), 1, 1));
            else gridview9.setAdapter(new MyBaseAdapter(act, new ArrayList<ResolveInfo>(), 1, 1));
            if (intentListPart.size()>=10) gridview10.setAdapter(new MyBaseAdapter(act, intentListPart.subList(9, 10), 1, 1));
            else gridview10.setAdapter(new MyBaseAdapter(act, new ArrayList<ResolveInfo>(), 1, 1));
            if (intentListPart.size()>=11) gridview11.setAdapter(new MyBaseAdapter(act, intentListPart.subList(10, 11), 1, 1));
            else gridview11.setAdapter(new MyBaseAdapter(act, new ArrayList<ResolveInfo>(), 1, 1));
            if (intentListPart.size()>=12) gridview12.setAdapter(new MyBaseAdapter(act, intentListPart.subList(11, 12), 1, 1));
            else gridview12.setAdapter(new MyBaseAdapter(act, new ArrayList<ResolveInfo>(), 1, 1));
            if (intentListPart.size()>=13) gridview13.setAdapter(new MyBaseAdapter(act, intentListPart.subList(12, 13), 1, 1));
            else gridview13.setAdapter(new MyBaseAdapter(act, new ArrayList<ResolveInfo>(), 1, 1));
            if (intentListPart.size()>=14) gridview14.setAdapter(new MyBaseAdapter(act, intentListPart.subList(13, 14), 1, 1));
            else gridview14.setAdapter(new MyBaseAdapter(act, new ArrayList<ResolveInfo>(), 1, 1));
            if (intentListPart.size()>=15) gridview15.setAdapter(new MyBaseAdapter(act, intentListPart.subList(14, 15), 1, 1));
            else gridview15.setAdapter(new MyBaseAdapter(act, new ArrayList<ResolveInfo>(), 1, 1));
            if (intentListPart.size()>=16) gridview16.setAdapter(new MyBaseAdapter(act, intentListPart.subList(15, 16), 1, 1));
            else gridview16.setAdapter(new MyBaseAdapter(act, new ArrayList<ResolveInfo>(), 1, 1));
            if (intentListPart.size()>=17) gridview17.setAdapter(new MyBaseAdapter(act, intentListPart.subList(16, 17), 1, 1));
            else gridview17.setAdapter(new MyBaseAdapter(act, new ArrayList<ResolveInfo>(), 1, 1));

            return gestureDetector.onTouchEvent(e1);
            //return false;
        }
    };
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
    public boolean onTouchEvent(MotionEvent event){
        return gestureDetector.onTouchEvent(event);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        return false;
    }
    private int max(int i, int i1){
        return i>i1?i:i1;
    }
    private int min(int i, int i1) {
        return i<i1?i:i1;
    }
}
