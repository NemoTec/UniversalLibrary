package com.nemo.ul.util.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class ULReflectUtil {
	private static final String TAG = ULReflectUtil.class.getSimpleName();
	
	public static Class getClass(String className) {
        Class class_by_name = null;
        try {
            class_by_name = Class.forName(className);
        } catch(java.lang.ClassNotFoundException e) {
            //Log.e(TAG, Log.getStackTraceString(e));
        }
        return class_by_name;
    }
	
	
	public static class ClassUtil {
	
	}
	
	
	
	
	
	
	
	
	
	

}