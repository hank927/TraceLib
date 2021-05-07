package com.hank.tracelib;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.hank.tracelib.db.BaseDao;
import com.hank.tracelib.db.MethodInfo;
import com.hank.tracelib.db.MethodInfoDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Create by hank on 2020/05/18
 * 方法插桩内容
 */
public class MTrace {

    public static final String TAG = "MTrace";
    private static Context context;
    private static boolean isTrace;
    private static int thresold = 5;
    private static ExecutorService executorService;
    private static List<MethodInfo> mInfos = new ArrayList<>();

    public static void init(Context context){
        MTrace.context = context;
        try {
            BaseDao.initDB(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Context getContext() {
        return context;
    }

    public static int getThresold() {
        return thresold;
    }

    public static void setThresold(int thresold) {
        MTrace.thresold = thresold;
    }

    public static long begin() {
        if (isOpenTraceMethod()) {
            long cur = System.currentTimeMillis();
            return cur;
        }
        return 0;
    }

    public static void end(String name, long start) {
        if (isOpenTraceMethod()) {
            synchronized (mInfos){
                long end = System.currentTimeMillis();
                long duration = end - start;
                Log.i(TAG,"execute method end:" + name+","+ duration);
                if(duration>thresold){
                    mInfos.add(new MethodInfo(name, duration, start,end, isInMainThread()));
                }
            }
        }
    }

    private static boolean isInMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    private static boolean isOpenTraceMethod() {
        return isTrace;
    }

    public static void beginTraceMethod() {
        mInfos.clear();
        isTrace = true;
    }

    public static void endTraceMethod() {
        isTrace = false;
        executorService = Executors.newFixedThreadPool(1);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                long current = System.currentTimeMillis();
                MLogger.v("record to database start.");
                MethodInfoDao.getInstance().insertData(mInfos);
                MLogger.v("record to database finish."+(System.currentTimeMillis()-current));
            }
        });
    }

}
