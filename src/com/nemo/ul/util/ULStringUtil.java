package com.nemo.ul.util;



public class ULStringUtil {

}

/*1.CharSequence to String
直接调用toString()方法

2.String to CharSequence
不用转换，String是CharSequence的子类。

3.Android中Html与String转换
String html = trashInfo.deleteAdvice + "<br>" + mContext.getString(R.string.clear_white_file_path)
                            + trashInfo.path;
            
return Html.fromHtml(html);*/