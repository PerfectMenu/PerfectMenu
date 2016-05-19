package com.example.perfectmenu;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.content.*;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    PackageManager myPackageManager;

    public class MyBaseAdapter extends BaseAdapter {
        private Context myContext;
        private List<ResolveInfo> MyAppList;
        MyBaseAdapter (Context c, List<ResolveInfo> I){
            myContext = c;
            MyAppList = I;
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
            ImageView imageView;
            if(convertView == null){
                //not recycled -> initialize
                imageView = new ImageView(myContext);
                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8,8,8,8);
            }
            else{
                imageView = (ImageView)convertView;
            }
            ResolveInfo resolveInfo = MyAppList.get(position);
            imageView.setImageDrawable(resolveInfo.loadIcon(myPackageManager));
            return imageView;

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

        GridView gridview = (GridView) findViewById(R.id.gridview);
        assert gridview != null;
        gridview.setAdapter(new MyBaseAdapter(this, intentList));

        gridview.setOnItemClickListener(myOnItemClickListener);
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
        return false;
    }
}
