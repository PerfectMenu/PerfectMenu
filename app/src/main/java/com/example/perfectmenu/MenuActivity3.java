package com.example.perfectmenu;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mooth on 2016-06-02.
 */
public class MenuActivity3 extends AppCompatActivity {
    PackageManager myPackageManager;
    Activity act = this;
    int temp = 0, t = 0;
    int unitLength = 150;
    GestureDetector gestureDetector;
    List<ResolveInfo> intentList;
    List<AppInfo> infoList = null;
    List<Info> priorityInfo;
    int N = 12;

    //private AppPrioritySettings priorityDB;

    //List<WeakReference<View>> myRecycleList = new ArrayList<>();
    GridView gridview1, gridview2, gridview3, gridview4, gridview5, gridview6, gridview7,
            gridview8, gridview9, gridview10, gridview11, gridview12;

    GridView[] gridview = new GridView[]{gridview5,gridview8,gridview3,gridview4,gridview7,gridview2,gridview1,gridview6,gridview9,gridview10,gridview11,gridview12};
    int[] size = {1,1,1,1,1,1,1,1,1,1,1,1};

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
                Intent intent = new Intent(getApplicationContext(), AppPrioritySettings.class);
                intent.putExtra("infoList", (Serializable) priorityInfo);
                startActivity(intent);
                break;
            case R.id.item2:
                Intent intent2 = new Intent(getApplicationContext(), ClassifyRecommendation.class);
                startActivity(intent2);
                break;
            case R.id.item3:
                Intent intent3 = new Intent(getApplicationContext(), ShowHowTo.class);
                startActivity(intent3);
                break;
            default:
                return false;
        }
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu3);
        myPackageManager = getPackageManager();
        gestureDetector = new GestureDetector(this, myOnGestureListener);
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        Intent getIntent = getIntent();
        priorityInfo = (List<Info>) getIntent.getSerializableExtra("infoList");

        intentList = getPackageManager().queryIntentActivities(intent, 0);
        //List<Info> pl = AppPrioritySettings.getPriorityDB();
        infoList=new ArrayList<AppInfo>();
        for (ResolveInfo resolveInfo : intentList){
            int pri = 0;
            for (Info info : priorityInfo){
                if (info.getName().equals(resolveInfo.loadLabel(myPackageManager))) pri=info.getPriority();
            }
            infoList.add(new AppInfo(resolveInfo,resolveInfo.loadLabel(myPackageManager).toString(),pri));
        }
        Collections.sort(infoList, AppInfo.COMPARATOR);
        intentList = new ArrayList<ResolveInfo>();
        for (int i=0;i<infoList.size();i++){
            intentList.add(infoList.get(i).info);
        }


        gridview1 = (GridView) findViewById(R.id.gridview1); assert gridview1 != null;
        gridview2 = (GridView) findViewById(R.id.gridview2); assert gridview2 != null;
        gridview3 = (GridView) findViewById(R.id.gridview3); assert gridview3 != null;
        gridview4 = (GridView) findViewById(R.id.gridview4); assert gridview4 != null;
        gridview5 = (GridView) findViewById(R.id.gridview5); assert gridview5 != null;
        gridview6 = (GridView) findViewById(R.id.gridview6); assert gridview6 != null;
        gridview7 = (GridView) findViewById(R.id.gridview7); assert gridview7 != null;
        gridview8 = (GridView) findViewById(R.id.gridview8); assert gridview8 != null;
        gridview9 = (GridView) findViewById(R.id.gridview9); assert gridview9 != null;
        gridview10 = (GridView) findViewById(R.id.gridview10); assert gridview10 != null;
        gridview11 = (GridView) findViewById(R.id.gridview11); assert gridview11 != null;
        gridview12 = (GridView) findViewById(R.id.gridview12); assert gridview12 != null;

        GridView[] gridview = new GridView[]{gridview5,gridview8,gridview3,gridview4,gridview7,gridview2,gridview1,gridview6,gridview9,gridview10,gridview11,gridview12};
        for (int i=0;i<N;i++){
            if (i<=intentList.size()) gridview[i].setAdapter(new MyBaseAdapter(act, intentList.subList(i, i+1), size[i], size[i]));
            else gridview[i].setAdapter(new MyBaseAdapter(act, new ArrayList<ResolveInfo>(), size[i], size[i]));
            gridview[i].setOnItemClickListener(myOnItemClickListener);
            gridview[i].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return gestureDetector.onTouchEvent(event);
                }
            });
        }
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
            if (temp >= intentList.size()) temp -= N; // 만약 환형 page 형식이라면 temp=0
            if (temp <= 0) temp = 0; // 만약 환형 page 형식이라면 temp=(int)((intentList.size() - 1)/N) * N
            intentListPart.addAll(intentList.subList(temp, min(temp + N, intentList.size())));
            /*------------------------------------------------------------------------------------------------*/
            GridView[] gridview = new GridView[]{gridview5,gridview8,gridview3,gridview4,gridview7,gridview2,gridview1,gridview6,gridview9,gridview10,gridview11,gridview12};
            for (int i=0;i<N;i++){
                if (i<intentListPart.size()) gridview[i].setAdapter(new MyBaseAdapter(act, intentListPart.subList(i, i+1), size[i], size[i]));
                else gridview[i].setAdapter(new MyBaseAdapter(act, new ArrayList<ResolveInfo>(), size[i], size[i]));
                gridview[i].setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return gestureDetector.onTouchEvent(event);
                    }
                });
            }

            return gestureDetector.onTouchEvent(e1);
            //return false;
        }
    };
    AdapterView.OnItemClickListener myOnItemClickListener = new AdapterView.OnItemClickListener() {
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