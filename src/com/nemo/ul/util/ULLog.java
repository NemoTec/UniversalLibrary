package com.nemo.ul.util;

import android.util.Log;


public class ULLog {
    private static final String TAG = "UniversalLibrary";
    private static String DOT = ".";
	
	private ULLog() {
	    
    }
    
    //Android Log level is "v, d, i, w, e", and the default level is "i" by using "Log.isLoggable()" to check,
    //so if we do not run "adb shell setprop log.tag.UniversalLibrary DEBUG", isDebug() will be false.
    private static boolean sDebug = Log.isLoggable(TAG, Log.DEBUG/*Log.INFO*/);

	public static boolean isDebug() {
        return true;//sDebug;
    }
	
	public static void setDot(String dot) {
        DOT = dot;
    }
	
    /*remove first.
    public static void v(String tag, String msg) {
        if (isDebug()) {
            Log.v(TAG + DOT + tag, msg);
        }
    }
	
	public static void v(String tag, String msg, Throwable tr) {
        if (isDebug()) {
	        Log.v(TAG + DOT + tag, msg, tr);
        }
    }
    */
    
    public static void d(String tag, String msg) {
        if (isDebug()) {
	        Log.d(TAG + DOT + tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (isDebug()) {
	        Log.d(TAG + DOT + tag, msg, tr);
        }
    }

    /*cause somebody may use "i" as "d", so remove first.
    public static void i(String tag, String msg) {
	    Log.i(TAG + DOT + tag, msg);
    }

    public static void i(String tag, String msg, Throwable tr) { 
	    Log.i(TAG + DOT + tag, msg, tr);
    }
    */

    public static void w(String tag, String msg) {
	    Log.w(TAG + DOT + tag, msg);
    }

    public static void w(String tag, String msg, Throwable tr) {
	    Log.w(TAG + DOT + tag, msg, tr);
    }

    public static void e(String tag, String msg) {
	    Log.e(TAG + DOT + tag, msg);
    }

    public static void e(String tag, String msg, Throwable tr) {
	    Log.e(TAG + DOT + tag, msg, tr);
    } 
    
}