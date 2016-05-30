package com.example.perfectmenu;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dkflf on 2016-05-31.
 */
public class top_activity extends AppCompatActivity{
    final public static String TAG = "Database";
    private PriorityDatasource priorityDS;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        priorityDS = new PriorityDatasource(this);
        priorityDS.open();
    }

    void setting(){
        PackageManager myPackageManager = getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> intentList = getPackageManager().queryIntentActivities(intent, 0);
        List<Info> values = priorityDS.getAllInfos();
        //System.out.println("values.size() = " + values.size() + ", intentList.size() = " + intentList.size());
        if (values.size()!=intentList.size()){
            priorityDS.deleteAllInfos();
            for (ResolveInfo info : intentList){
                priorityDS.createInfo(info.loadLabel(myPackageManager).toString(), 0);
            }
        }
    }

    @Override
    protected void onResume(){
        priorityDS.open();
        setting();
        Intent getIntent = getIntent();
        List<Info> renewPriority = (List<Info>) getIntent.getSerializableExtra("infoList");
        if (renewPriority!=null) {
            System.out.println("Save");
            priorityDS.deleteAllInfos();
            int i = 0;
            for (Info info : renewPriority) {
                priorityDS.createInfo(info.getName(), info.getPriority());
            }
        }
        Intent putIntent = new Intent(top_activity.this, MainActivity.class);
        putIntent.putExtra("infoList", (Serializable) priorityDS.getAllInfos());
        priorityDS.close();
        startActivity(putIntent);
        super.onResume();
    }
}
