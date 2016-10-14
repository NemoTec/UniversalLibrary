package com.nemo.ul.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.util.Log;


public class ULPackageUtil {
    private static final String TAG = "ULPackageUtil";
    
    protected Drawable getPackageIcon(Context context, String pkgName) {
        try {
            PackageManager pkgManager = context.getPackageManager();
            return pkgManager.getApplicationIcon(pkgName);
        } catch (NameNotFoundException e) {
            Log.e(TAG, "getPackageIcon() NameNotFoundException: " + e.getMessage());
            return null;
        }
    }
    
    public static Drawable getUninstallApkIcon(Context context, String archiveFilePath) {
        Drawable icon = null;

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageArchiveInfo(archiveFilePath,
                    PackageManager.GET_ACTIVITIES);
            if (packageInfo != null) {
                ApplicationInfo appInfo = packageInfo.applicationInfo;
                appInfo.sourceDir = archiveFilePath;
                appInfo.publicSourceDir = archiveFilePath;
                icon = appInfo.loadIcon(packageManager);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        if (icon == null) {
            icon = context.getResources().getDrawable(android.R.drawable.sym_def_app_icon);
        }

        return icon;
    }

}