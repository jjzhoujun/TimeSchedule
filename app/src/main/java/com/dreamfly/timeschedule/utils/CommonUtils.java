package com.dreamfly.timeschedule.utils;

import android.content.Context;

import com.dreamfly.timeschedule.R;

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
        mContext = context.getApplicationContext();
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
}
