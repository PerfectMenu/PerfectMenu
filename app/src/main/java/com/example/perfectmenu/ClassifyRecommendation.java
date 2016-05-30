package com.example.perfectmenu;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by 유정 on 2016-05-28.
 */
public class ClassifyRecommendation extends AppCompatActivity {
    ViewPager myViewPager;
    public class MyPagerAdapter extends PagerAdapter {
        LayoutInflater inflater;
        MyPagerAdapter(LayoutInflater inflater){
            this.inflater = inflater;
        }
        @Override
        public int getCount() {
            return 4;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position){
            View view = null;
            view = inflater.inflate(R.layout.select_layout, null);

            ImageView img = (ImageView)view.findViewById(R.id.layout_image);
            img.setImageResource(R.mipmap.screen1);// + position);
            container.addView(view);
            return view;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object){
            container.removeView((View) object);
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view==object);
        }
    }
    public void myOnClick(View v){
        int position;
        switch(v.getId()){
            case R.id.to_left:
                position= myViewPager.getCurrentItem();
                if(position != 0) {
                    myViewPager.setCurrentItem(position -1, true);
                }
                break;
            case R.id.to_right:
                position= myViewPager.getCurrentItem();
                if(position != 3) myViewPager.setCurrentItem(position + 1, true);
                break;
            case R.id.select:
                //save selected layout type
                finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classify_recommendation);
        myViewPager = (ViewPager)findViewById(R.id.recommend_pager);
        //Button button = (Button)findViewById(R.id.select);
        //setContentView(button);
        MyPagerAdapter adapter = new MyPagerAdapter(getLayoutInflater());
        myViewPager.setAdapter(adapter);
    }
    /*@Override
    protected void onDestroy(){
        RecycleUtils.recursiveRecycle(myRecycleList);
        System.gc();
        super.onDestroy();
    }*/

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        finish();
        return false;
    }
}
