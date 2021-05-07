package com.hank.tracelib.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.hank.tracelib.MLogger;
import com.hank.tracelib.MTrace;

public class BaseDao {
    public static final Object LOCK = new Object();

    /**
     * 启动app打开，退出app时关闭
     */
    public static volatile SQLiteDatabase sDB;
    protected String tableName;

    public static SQLiteDatabase initDB(Context context) throws Exception{
        synchronized (LOCK) {
            if(context==null){
                throw new Exception("you must init MTrace first");
            }
            if (sDB == null) {
                sDB = TraceDBHelper.getInstance(context).getWritableDatabase();
                MLogger.i("Init db");
            }
        }

        return sDB;
    }

    public SQLiteDatabase getDB() {
        try {
            return initDB(MTrace.getContext());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void releaseDB() {
        synchronized (LOCK) {
            close();
            sDB = null;
        }
    }

    /**
     * 关闭数据库
     */
    public static void close() {
        synchronized (LOCK) {
            if (sDB != null) {
                try {
                    sDB.close();
                } catch (Exception e) {
                    MLogger.e(e);
                }
            }
        }
    }

}
