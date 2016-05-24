package com.example.perfectmenu;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 유정 on 2016-05-25.
 */
public class ResolveInfoAdapter extends BaseAdapter{
    List<ResolveInfo> infoList;
    LayoutInflater inflater;
    PackageManager myPackageManager;
    public ResolveInfoAdapter(LayoutInflater inflater, PackageManager myPackageManager, List<ResolveInfo> intentList){
        this.infoList = intentList;
        this.myPackageManager = myPackageManager;
        this.inflater = inflater;
    }
    @Override
    public int getCount(){
        return infoList.size();
    }
    @Override
    public Object getItem(int position){
        return infoList.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.viewpager, null);
        }
        ImageView iconImg = (ImageView) convertView.findViewById(R.id.grid_item_image);
        TextView textLabel = (TextView) convertView.findViewById(R.id.grid_item_label);
        iconImg.setImageDrawable(infoList.get(position).loadIcon(myPackageManager));
        textLabel.setText(infoList.get(position).loadLabel(myPackageManager));
        return convertView;
    }
}