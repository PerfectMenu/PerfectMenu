package com.example.perfectmenu;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 유정 on 2016-05-25.
 */
public class AppInfo {
    ImageView img;
    TextView txt;
    public AppInfo(ImageView img, TextView txt){
        this.img = img;
        this.txt = txt;
    }
    public ImageView getImg(){
        return img;
    }
    public TextView getTxt(){
        return txt;
    }
    public void setImg(ImageView img){
        this.img = img;
    }
    public void setTxt(TextView txt){
        this.txt = txt;
    }
}
