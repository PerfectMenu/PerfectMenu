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
    private ClassifyDatasource classifyDS;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        priorityDS = new PriorityDatasource(this);
        priorityDS.open();

        classifyDS = new ClassifyDatasource(this);
        classifyDS.open();
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
        classifyDS.open();
        setting();
        Intent getIntent = getIntent();

        /* Get Priority */
        List<Info> renewPriority = (List<Info>) getIntent.getSerializableExtra("infoList");
        if (renewPriority!=null) {
            priorityDS.deleteAllInfos();
            int i = 0;
            for (Info info : renewPriority) {
                priorityDS.createInfo(info.getName(), info.getPriority());
            }
        }

        /* Get Classify Recommendation */
        int classify = (int) getIntent.getIntExtra("classifyRecommendation", -1);
        System.out.println("Classify is " + classify);
        if (classify!=-1){
            classifyDS.deleteAllInfos();
            classifyDS.createInfo(classify);
        }

        int currentClassify = classifyDS.getAllInfos();
        Intent putIntent = null;
        switch (currentClassify){
            case 0:
                //putIntent = new Intent(top_activity.this, DefaultMenuActivity.class);
                putIntent = new Intent(top_activity.this, DefaultMenuActivity.class);
                break;
            case 1:
                putIntent = new Intent(top_activity.this, MenuActivity1.class);
                break;
            case 2:
                putIntent = new Intent(top_activity.this, MenuActivity2.class);
                break;
            case 3:
                putIntent = new Intent(top_activity.this, MenuActivity3.class);
                break;
            case 4:
                putIntent = new Intent(top_activity.this, MenuActivity4.class);
                break;
            default:
                putIntent = new Intent(top_activity.this, DefaultMenuActivity.class);
                break;
        }
        putIntent.putExtra("infoList", (Serializable) priorityDS.getAllInfos());
        classifyDS.close();
        priorityDS.close();
        startActivity(putIntent);
        super.onResume();
    }
}