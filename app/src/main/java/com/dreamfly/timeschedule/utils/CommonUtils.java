package com.dreamfly.timeschedule.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.dreamfly.debuginfo.LogPrint;
import com.dreamfly.timeschedule.R;
import com.dreamfly.timeschedule.activity.UIAddTaskActivity;
import com.dreamfly.timeschedule.bo.ConstantVar;
import com.dreamfly.timeschedule.bo.Entity;
import com.dreamfly.timeschedule.bo.TimeItemEntity;
import com.dreamfly.timeschedule.utils.greendao.TSDatabaseMgrMul;
import com.dreamfly.timeschedule.utils.greendao.TSRepository;

/**
 * Created by jayden on 15-9-14.
 */
public class CommonUtils {

    private static final String TAG = "CommonUtils";
    private static CommonUtils sInstance;
    private Context mContext;
    private long mId;


    public static synchronized CommonUtils getInstance(Context context) {
        if(sInstance == null) {
            sInstance = new CommonUtils(context);
        }
        return sInstance;
    }

    private CommonUtils(Context context) {
        mContext = context;
    }

    public int getTaskStatus(final int value) {
        switch(value){
            // 备注: 这里面的pressed的颜色效果, 跟我设置的不一样,默认都变成系统的press颜色了. why ?
            case ConstantVar.STATUS_FIRST_LEVEL:
                return R.drawable.selector_first_level;
            case ConstantVar.STATUS_SECOND_LEVEL:
                return R.drawable.selector_second_level;
            case ConstantVar.STATUS_THIRD_LEVEL:
                return R.drawable.selector_third_level;
            case ConstantVar.STATUS_FOURTH_LEVEL:
                return R.drawable.selector_fourth_level;
            default:
                break;
        }
        return R.drawable.selector_first_level;
    }

    public void startActivity(String pkgName, String clzName) {
        try {
            Intent intent = new Intent();
            intent.setClassName(pkgName, clzName);
            mContext.startActivity(intent);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void startAddTaskActivity(Activity activity, Entity entity) {
        try {
            Intent intent = new Intent();
            intent.setClass(activity, UIAddTaskActivity.class);
            intent.putExtra(ConstantVar.ADD_TASK, entity);
            mContext.startActivity(intent);
        } catch(Exception e){
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
