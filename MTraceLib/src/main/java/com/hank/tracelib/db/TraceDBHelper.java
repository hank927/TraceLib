package com.hank.tracelib.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.hank.tracelib.MLogger;

/**
 * Note:修改表字段要考虑数据同步问题。添加字段后要把数据清除并将lastUpdateTime设置为0.因为如果app还没有升级，但服务器或者主机已经升级最新字段，此时app已经把最新字段数据更新到app，但因为app没有对应字段显示且app已经更新了lastUpdateTime，当升级完app后去数据同步时，将无法显示新增的数据库字段。
 */
public class TraceDBHelper extends SQLiteOpenHelper {
    public static final Object LOCK = new Object();

    /**
     * 是否要拷贝devicedesc表
     */
    private static final String COPY_DEVICE_DESC = "copyDeviceDesc";

    /**
     * 名称不能修改，日志模块用到
     */
    public static final String DATABASE_NAME = "trace.db";
    /**
     */
    private static final int DATABASE_VERSION = 1;

    private static TraceDBHelper sHelper = null;
    private Context mContext;

    private TraceDBHelper(Context context) {
        super(context, DATABASE_NAME,  null,
                DATABASE_VERSION, null);
        mContext = context;
    }

    public static TraceDBHelper getInstance(Context context) {
        if (sHelper == null) {
            init(context);
        }
        return sHelper;
    }

    private synchronized static void init(Context context) {
        if (sHelper == null) {
            sHelper = new TraceDBHelper(context);
        }
    }

    public static void close(SQLiteDatabase db, Cursor c) {
        if (db != null) {
            try {
                db.close();
            } catch (Exception e) {
                MLogger.e(e);
            }
        }
        closeCursor(c);
    }

    public static void closeCursor(Cursor c) {
        if (c != null) {
            try {
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
                MLogger.e(e);
            }
        }
    }

    /**
     * 结束事务
     *
     * @param db
     */
    public static void endTransaction(SQLiteDatabase db) {
        endTransaction(db, false);
    }

    public static void endTransaction(SQLiteDatabase db, boolean transactionSuccessful) {
        if (db != null) {
            try {
                synchronized (LOCK) {
                    if (transactionSuccessful) {
                        db.setTransactionSuccessful();
                    }
                    db.endTransaction();
                }
            } catch (Exception e) {
                MLogger.e(e);
            }
        } else {
            MLogger.w("endTransaction()-db is null.");
        }
    }

    public static void setTransactionSuccessful(SQLiteDatabase db) {
        try {
            synchronized (LOCK) {
                if (db.inTransaction()) {
                    db.setTransactionSuccessful();
                }
            }
        } catch (Exception e) {
            MLogger.e(e);
        }
    }

    public static void beginTransaction(SQLiteDatabase db) {
        if (db != null) {
            try {
                synchronized (LOCK) {
                    db.beginTransaction();
                }
            } catch (Exception e) {
                e.printStackTrace();
                MLogger.e(e);
            }
        } else {
            MLogger.w("beginTransaction()-db is null.");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(methodInfoSQL());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /**
     * 账号表
     *
     * @return
     */
    private String methodInfoSQL() {
        String body = new StringBuffer().append("create table IF NOT EXISTS ")
                .append(MethodInfo.TABLENAME)
                .append(" (")
                .append(MethodInfo.NAME + " text,")
                .append(MethodInfo.COST_TIME + " integer,")
                .append(MethodInfo.START_TIME + " long,")
                .append(MethodInfo.END_TIME + " long,")
                .append(MethodInfo.IS_MAIN_THREAD + " integer")
                .append(");")
                .toString();
        return body;
    }

}
