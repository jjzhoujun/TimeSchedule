package com.dreamfly.timeschedule.utils;

import android.content.Context;
import android.content.Intent;

import com.dreamfly.timeschedule.R;
import com.dreamfly.timeschedule.model.ConstantVar;
import com.dreamfly.timeschedule.model.TimeItemEntity;
import com.dreamfly.timeschedule.utils.greendao.TSDatabaseMgrMul;

import java.text.SimpleDateFormat;

/**
 * Created by jayden on 15-9-14.
 */
public class CommonUtils {

    private static final String TAG = "CommonUtils";
    private static CommonUtils sInstance;
    private Context mContext;

    public static synchronized CommonUtils getInstance(Context context) {
        if(sInstance == null) {
            sInstance = new CommonUtils(context);
        }
        return sInstance;
    }

    private CommonUtils(Context context) {
        mContext = context;
    }

    public String getTaskStatus(final int value) {
        switch(value){
            case 0:
                return mContext.getString(R.string.str_import_urgent);
            case 1:
                return mContext.getString(R.string.str_import_not_urgent);
            case 2:
                return mContext.getString(R.string.str_not_import_urgent);
            case 3:
                return mContext.getString(R.string.str_not_import_not_urgent);
            default:
                break;
        }
        return mContext.getString(R.string.str_import_urgent);
    }

    public void startActivity(String pkgName, String clzName) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClassName(pkgName, clzName);
            mContext.startActivity(intent);
        } catch(Exception e){

        }
    }


    public TimeItemEntity saveTimeStruct(String title, int status, String strNotice) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        TimeItemEntity timeStruct = new TimeItemEntity();
        timeStruct.setB_finish(false);
        timeStruct.setS_titile(title);
        timeStruct.setI_status(status);
        timeStruct.setS_start_time(date);
        TSDatabaseMgrMul tsDatabaseMgrMul = new TSDatabaseMgrMul(mContext);
        tsDatabaseMgrMul.newDataBase();
        tsDatabaseMgrMul.setDataBox(timeStruct);
        return timeStruct;
    }

    public void saveTimeStruct(TimeItemEntity timeItemEntity) {
        TSDatabaseMgrMul tsDatabaseMgrMul = new TSDatabaseMgrMul(mContext);
        tsDatabaseMgrMul.newDataBase();
        tsDatabaseMgrMul.setDataBox(timeItemEntity);
    }
}
