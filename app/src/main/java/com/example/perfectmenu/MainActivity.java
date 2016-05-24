package com.example.perfectmenu;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import java.util.List;

public class MainActivity extends Activity {

    private ViewPager viewPager;
    PackageManager myPackageManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myPackageManager = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> intentList = getPackageManager().queryIntentActivities(intent, 0);
        viewPager = (ViewPager) findViewById(R.id.pager);
        GridViewAdapter adapter = new GridViewAdapter(getLayoutInflater(), myPackageManager, intentList);
        viewPager.setAdapter(adapter);
    }
}