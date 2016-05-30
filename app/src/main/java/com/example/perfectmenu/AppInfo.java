package com.example.perfectmenu;

import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager;

import java.text.Collator;
import java.util.Comparator;

import javax.security.auth.callback.PasswordCallback;

/**
 * Created by dkflf on 2016-05-29.
 */
public class AppInfo {
    // App Informantion
    public ResolveInfo info;
    // App Name
    String appName;
    // Priority
    public int value;

    public AppInfo() {}


    public AppInfo(ResolveInfo _info, String _appName, int _value){
        info = _info;
        appName = _appName;
        value = _value;
    }

    public static final Comparator<AppInfo> COMPARATOR = new Comparator<AppInfo>() {
        private final Collator sCollator = Collator.getInstance();
        @Override
        public int compare(AppInfo lhs, AppInfo rhs) {
            if (lhs.value != rhs.value) return rhs.value-lhs.value;
            return lhs.appName.compareTo(rhs.appName);
        }
    };
}
