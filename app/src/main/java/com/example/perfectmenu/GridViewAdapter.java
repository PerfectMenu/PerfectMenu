package com.example.perfectmenu;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.List;

/**
 * Created by 유정 on 2016-05-25.
 */
public class GridViewAdapter extends PagerAdapter {
    LayoutInflater inflater;
    List<ResolveInfo> info;
    int pageNum = 3;
    PackageManager pm;
    ResolveInfoAdapter r;
    public GridViewAdapter(LayoutInflater inflater, PackageManager myPackageManager, List<ResolveInfo> intentList){
        this.inflater = inflater;
        this.pm = myPackageManager;
        this.info = intentList;
    }
    @Override
    public int getCount() {
        return pageNum;
    }
    public Object instantiateItem(ViewGroup container, int position){
        View view = null;
        view = inflater.inflate(R.layout.viewpager, null);
        GridView grid = (GridView)view.findViewById(R.id.grid);
        ResolveInfoAdapter adapter = new ResolveInfoAdapter(inflater, pm, info);
        grid.setAdapter(adapter);
        //
        container.addView(view);
        return view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((View)object);
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
