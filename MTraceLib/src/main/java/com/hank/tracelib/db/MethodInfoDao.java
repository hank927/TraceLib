package com.hank.tracelib.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.hank.tracelib.MLogger;

import java.util.List;

/**
 * @author huangqiyao
 * @date 2015/1/6
 */
public class MethodInfoDao extends BaseDao {

    private static MethodInfoDao ourInstance = new MethodInfoDao();

    public static MethodInfoDao getInstance() {
        return ourInstance;
    }

    private MethodInfoDao() {
        tableName = MethodInfo.TABLENAME;
    }


    public void insertData(MethodInfo baseBo) {
        synchronized (LOCK) {

            try {
                getDB().replace(tableName, null, getContentValues(baseBo));
            } catch (Exception e) {
                MLogger.e(e);
            } finally {

            }

        }
    }

    public void insertData(List<MethodInfo> baseBos) {
        synchronized (LOCK) {
            getDB().beginTransaction();
            try {
                int size = baseBos.size();
                for(int i=0;i<baseBos.size();i++){
                    getDB().replace(tableName, null, getContentValues(baseBos.get(i)));
                }
                getDB().setTransactionSuccessful();
            } catch (Exception e) {
                MLogger.e(e);
            } finally {
                getDB().endTransaction();
            }
        }
    }

    public ContentValues getContentValues(MethodInfo baseBo) {
        ContentValues cv = new ContentValues();
        cv.put(MethodInfo.NAME, baseBo.getName());
        cv.put(MethodInfo.COST_TIME, baseBo.getCostTime());
        cv.put(MethodInfo.START_TIME, baseBo.getStartTime());
        cv.put(MethodInfo.END_TIME, baseBo.getEndTime());
        cv.put(MethodInfo.IS_MAIN_THREAD, baseBo.isMainThread()?1:0);
        return cv;
    }

    public MethodInfo getSingleData(Cursor cursor) {
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.setName(cursor.getString(cursor.getColumnIndex(MethodInfo.NAME)));
        methodInfo.setCostTime(cursor.getLong(cursor.getColumnIndex(MethodInfo.COST_TIME)));
        methodInfo.setEndTime(cursor.getLong(cursor.getColumnIndex(MethodInfo.END_TIME)));
        methodInfo.setStartTime(cursor.getLong(cursor.getColumnIndex(MethodInfo.START_TIME)));
        methodInfo.setMainThread(cursor.getInt(cursor.getColumnIndex(MethodInfo.IS_MAIN_THREAD))==1?true:false);
        return methodInfo;
    }
}
