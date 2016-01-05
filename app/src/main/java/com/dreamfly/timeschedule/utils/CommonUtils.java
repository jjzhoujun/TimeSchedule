package com.dreamfly.timeschedule.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.dreamfly.debuginfo.LogPrint;
import com.dreamfly.timeschedule.R;
import com.dreamfly.timeschedule.activity.TimePickerActivity;
import com.dreamfly.timeschedule.activity.UIAddTaskActivity;
import com.dreamfly.timeschedule.bo.ConstantVar;
import com.dreamfly.timeschedule.bo.Entity;
import com.dreamfly.timeschedule.bo.TimeItemEntity;
import com.dreamfly.timeschedule.utils.greendao.TSDatabaseMgrMul;
import com.dreamfly.timeschedule.utils.greendao.TSRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jayden on 15-9-14.
 */
public class CommonUtils {

    private static final String TAG = "CommonUtils";
    private static CommonUtils sInstance;
    private Context mContext;
    private long mId;


    public static synchronized CommonUtils getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new CommonUtils(context);
        }
        return sInstance;
    }

    private CommonUtils(Context context) {
        mContext = context;
    }

    public void startActivity(String pkgName, String clzName) {
        try {
            Intent intent = new Intent();
            intent.setClassName(pkgName, clzName);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startAddTaskActivity(Activity activity, Entity entity) {
        try {
            Intent intent = new Intent();
            intent.setClass(activity, UIAddTaskActivity.class);
            intent.putExtra(ConstantVar.ADD_TASK, entity);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startTimePickerActivity(Activity activity, String[] entity) {
        try {
            Intent intent = new Intent();
            intent.setClass(activity, TimePickerActivity.class);
            intent.putExtra(ConstantVar.TASK_TIME, entity);
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveTimeStruct(TimeItemEntity timeItemEntity) {
        TSDatabaseMgrMul tsDatabaseMgrMul = new TSDatabaseMgrMul(mContext);
        tsDatabaseMgrMul.insertOrUpdate(timeItemEntity);
    }

    public void delTimeStruct(long id) {
        LogPrint.Warning("start to delTimeStruct by id = " + id);
        TSRepository.deleteBoxWithId(mContext, id);
    }

    public void setId(long id) {
        mId = id;
    }

    public long getId() {
        return mId;
    }
}

