package com.example.perfectmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
/**
 * * Created by 유정 on 2016-05-28.
 * */
public class ShowHowTo extends AppCompatActivity {
	ViewPager myViewPager;
    public class MyPagerAdapter extends PagerAdapter {
        LayoutInflater inflater;

        MyPagerAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = null;
            view = inflater.inflate(R.layout.select_layout, null);
            ImageView img = (ImageView) view.findViewById(R.id.layout_image);
            img.setImageResource(R.drawable.howto1 + position);// + position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_how_to);
		myViewPager = (ViewPager)findViewById(R.id.show_how_to);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
		MyPagerAdapter adapter = new MyPagerAdapter(getLayoutInflater());
		myViewPager.setAdapter(adapter);
	}
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		Intent intent = new Intent(ShowHowTo.this, top_activity.class);
		startActivity(intent);
		finish();
		return false;
	}
}
