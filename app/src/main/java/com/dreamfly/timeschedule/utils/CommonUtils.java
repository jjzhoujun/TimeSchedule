package com.dreamfly.timeschedule.utils;

import android.content.Context;
import android.content.Intent;

import com.dreamfly.timeschedule.R;
import com.dreamfly.timeschedule.model.ConstantVar;
import com.dreamfly.timeschedule.model.TimeItemEntity;
import com.dreamfly.timeschedule.utils.greendao.TSDatabaseMgrMul;

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

    public int getTaskStatus(final int value) {
        switch(value){
            case ConstantVar.STATUS_FIRST_LEVEL:
                return R.color.color_first_level;
            case ConstantVar.STATUS_SECOND_LEVEL:
                return R.color.color_second_level;
            case ConstantVar.STATUS_THIRD_LEVEL:
                return R.color.color_third_level;
            case ConstantVar.STATUS_FOURTH_LEVEL:
                return R.color.color_fourth_level;
            default:
                break;
        }
        return R.color.color_first_level;
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

    public void saveTimeStruct(TimeItemEntity timeItemEntity) {
        TSDatabaseMgrMul tsDatabaseMgrMul = new TSDatabaseMgrMul(mContext);
        tsDatabaseMgrMul.newDataBase();
        tsDatabaseMgrMul.setDataBox(timeItemEntity);
    }
}
