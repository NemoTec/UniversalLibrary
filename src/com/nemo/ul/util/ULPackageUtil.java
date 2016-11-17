package com.nemo.ul.util;

import java.io.File;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.content.pm.PackageParser;
import android.content.pm.PackageParser.PackageParserException;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.nemo.ul.java.ULMD5Util;
import com.nemo.ul.util.ULLog;


public class ULPackageUtil {
    private static final String TAG = "ULPackageUtil";
    
    protected Drawable getPackageIcon(Context context, String pkgName) {
        try {
            PackageManager pkgManager = context.getPackageManager();
            return pkgManager.getApplicationIcon(pkgName);
        } catch (NameNotFoundException e) {
            ULLog.e(TAG, "getPackageIcon() NameNotFoundException: " + e.getMessage());
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
            ULLog.e(TAG, e.getMessage());
        }

        if (icon == null) {
            icon = context.getResources().getDrawable(android.R.drawable.sym_def_app_icon);
        }

        return icon;
    }

    public static String getUninstallApkPackageName(Context context, String archiveFilePath) {
        String packageName = "";

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageArchiveInfo(archiveFilePath,
                    PackageManager.GET_ACTIVITIES);
            if (packageInfo != null) {
                ApplicationInfo appInfo = packageInfo.applicationInfo;
                appInfo.sourceDir = archiveFilePath;
                appInfo.publicSourceDir = archiveFilePath;
                packageName = appInfo.packageName;
            }
        } catch (Exception e) {
            ULLog.e(TAG, e.getMessage());
        }

        return packageName;
    }

    public static String getInstalledAppCertMd5(Context context, String packageName) {
        PackageInfo installedPackageInfo = null;
        try {
            installedPackageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            ULLog.e(TAG, packageName + " NameNotFoundException.");
        }

        if (installedPackageInfo == null) {
            ULLog.d(TAG, packageName + " not installed, return null.");
            return "";
        }

        int byteArrayLen = 0;
        for (Signature sign : installedPackageInfo.signatures) {
            //ULLog.d(TAG, packageName + " --- sign = " + sign);
            byteArrayLen += sign.toByteArray().length;
        }

        byte[] resultByteArray = new byte[byteArrayLen];
        int startLen = 0;
        for (Signature sign : installedPackageInfo.signatures) {
            byte[] signArray = sign.toByteArray();
            int signLen = signArray.length;
            System.arraycopy(signArray, 0, resultByteArray, startLen, signArray.length);
            startLen += signLen;
        }

        String strMd5 = ULMD5Util.getByteArrayMD5(resultByteArray);
        ULLog.d(TAG, packageName + " --- strMd5 = " + strMd5);
        return strMd5;
    }

    public static String getUninstalledCertMd5(Context context, String apkPath) {
        ULLog.d(TAG, "SD --- getUninstalledCertMd5() apkPath: " + apkPath);
        if (TextUtils.isEmpty(apkPath)) {
            ULLog.e(TAG, "getUninstalledCertMd5() apkPath empty.");
            return null;
        }

        final File sourceFile = new File(apkPath);
        if (!sourceFile.exists()) {
            ULLog.e(TAG, "getUninstalledCertMd5() apkPath file not exist.");
            return null;
        }

        final PackageParser parser = new PackageParser();
        PackageParser.Package pkg = null;
        try {
            pkg = parser.parsePackage(sourceFile, 0);

            //Add for 'PackageManager.GET_SIGNATURES', see PackageManager.getPackageArchiveInfo(),
            //but this collect will cause the launch time be to long, careful!!!!
            parser.collectCertificates(pkg, 0);

        } catch (PackageParserException e) {
            return null;
        } catch (Exception e) {
            return null;
        }

        if (pkg != null && pkg.mSignatures.length > 0 ) {
            int byteArrayLen = 0;
            for (Signature sign : pkg.mSignatures) {
                //ULLog.d(TAG, apkPath + " --- SD sign = " + sign);
                byteArrayLen += sign.toByteArray().length;
            }

            byte[] resultByteArray = new byte[byteArrayLen];
            int startLen = 0;
            for (Signature sign : pkg.mSignatures) {
                byte[] signArray = sign.toByteArray();
                int signLen = signArray.length;
                System.arraycopy(signArray, 0, resultByteArray, startLen, signArray.length);
                startLen += signLen;
            }

            String strMd5 = ULMD5Util.getByteArrayMD5(resultByteArray);
            ULLog.d(TAG, apkPath + ", packageName - " + pkg.packageName + " --- SD strMd5 = " + strMd5);
            return strMd5;
        }

        return null;
    }

}