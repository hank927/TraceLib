package com.hank.tracelib;

import android.util.Log;

/**
 * author : Administrator
 * date   : 2021/5/6
 * desc   :
 */
public class MLogger {
    public static final String TAG = "MLogger";

    public static void d(String msg){
        Log.d(TAG,msg);
    }

    public static void e(String msg){
        Log.e(TAG,msg);
    }

    public static void e(Exception e){
        e.printStackTrace();
    }

    public static void v(String msg){
        Log.v(TAG,msg);
    }

    public static void i(String msg){
        Log.i(TAG,msg);
    }

    public static void w(String msg){
        Log.w(TAG,msg);
    }
}
