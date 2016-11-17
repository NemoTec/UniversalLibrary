package com.nemo.ul.comparator;

import android.content.Context;
import android.content.pm.ApplicationInfo;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import com.nemo.ul.java.ULStringUtil;


public class AppWrapperComparator implements Comparator<AppWrapperComparator.AppInfoWrapper> {
    private static final String TAG = "AppWrapperComparator";

    private static final int TYPE_NUMBER = 1;
    private static final int TYPE_LETTER = 2;
    private static final int TYPE_CHINESE = 3;
    private static final int TYPE_OTHER = 4;

    Context mContext;
    List<String> mNotRestrictL = new ArrayList<String>();
    List<String> mNewStartL = new ArrayList<String>();


    public AppWrapperComparator(Context context, List<String> listRL, List<String> listSL) {
        mContext = context;
        mNotRestrictL = listRL;
        mNewStartL = listSL;
    }

     @Override
    public int compare(AppInfoWrapper appInfo1, AppInfoWrapper appInfo2) {
        boolean isInRestrictApp1 = isInRestrictList(appInfo1.pkgName);
        boolean isInRestrictApp2 = isInRestrictList(appInfo2.pkgName);
        if (isInRestrictApp1 && !isInRestrictApp2) {
            return -1;
        }
        if (!isInRestrictApp1 && isInRestrictApp2) {
            return 1;
        }

        String name1 = ULStringUtil.deleteMessyCode(appInfo1.label.trim());
        String name2 = ULStringUtil.deleteMessyCode(appInfo2.label.trim());

        if (name1.length() == 0) {
            return -1;
        } else if (name2.length() == 0) {
            return 1;
        }

        char c1 = name1.charAt(0);
        char c2 = name2.charAt(0);
        int type1 = getLetterType(c1);
        int type2 = getLetterType(c2);
        int result = 0;
        if (type1 != type2) {
            return c1 - c2;
        } else if ((type1 == type2) && type1 == TYPE_LETTER) {
            char c11 = Character.toLowerCase(c1);
            char c22 = Character.toLowerCase(c2);
            if (c11 == c22 && c1 != c2) {
                result = c1 - c2;
            } else {
                result = c11 - c22;
            }
        } else if((type1 == type2) && type1 == TYPE_CHINESE) {
            Collator collator = Collator.getInstance(Locale.CHINA);
            result = collator.compare(name1, name2);
        }
        return result;
    }

    private int getLetterType(char c) {
        if (c >= 0x4e00 && c <= 0x9fa5) {
            return TYPE_CHINESE;
        } else if (isNum(c)) {
            return TYPE_NUMBER;
        } else if (isLetter(c)) {
            return TYPE_LETTER;
        } else {
            return TYPE_OTHER;
        }
    }

    private boolean isNum(char c) {
        return c >= 0x0030 && c <= 0x0039;
    }

    private boolean isLetter(char c) {
        return (c >= 0x0041 && c <= 0x005a) || (c >= 0x0061 && c <= 0x007a);
    }

    private boolean isInRestrictList(String packageName) {
        return (null != mNotRestrictL && mNotRestrictL.contains(packageName))
                || (null != mNewStartL && mNewStartL.contains(packageName));
    }

    public static class AppInfoWrapper {
        public String pkgName;
        public String label;
        public ApplicationInfo appInfo;

        public AppInfoWrapper() {

        }
    }

}
