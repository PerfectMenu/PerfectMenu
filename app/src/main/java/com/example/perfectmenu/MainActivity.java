package com.example.perfectmenu;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    PackageManager myPackageManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myPackageManager = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> intentList = getPackageManager().queryIntentActivities(intent, 0);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, intentList);
        pager.setAdapter(adapter);
    }
    public class ViewPagerAdapter extends PagerAdapter{
        private Context mContext;
        private int pageNum = 0;
        private List<ResolveInfo> MyappList;
        public ViewPagerAdapter(Context context, List<ResolveInfo> I) {
            mContext = context;
            MyappList = I;
        }
        public int getCount(){ return pageNum; };
        public Object instantiateItem(ViewGroup container){
            MenuPage page = new MenuPage(mContext);
            //초기화
            container.addView(page);
            return page;
        }
        public void destroyItem(ViewGroup container, int position, Object view){
            container.removeView((View)view);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }
    }

    public OnItemClickListener myOnItemClickListener = new OnItemClickListener() {
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
        return false;
    }
}