package com.dreamfly.timeschedule.utils.greendao;

import android.content.Context;

import com.dreamfly.timeschedule.TSApplication;

import java.util.List;

import greendao.TSBox;
import greendao.TSBoxDao;


/**
 * Created by jayden on 15-9-13.
 */
public class TSRepository {

    private static TSBoxDao getBoxDao(Context c) {
        return ((TSApplication) c.getApplicationContext()).getDaoSession().getTSBoxDao();
    }

    public static TSBox getBoxForId(Context context, long id) {
        return getBoxDao(context).load(id);
    }

    public static void insertOrUpdate(Context context, TSBox tsBox) {
        getBoxDao(context).insertOrReplace(tsBox);
    }

    public static void deleteBoxWithId(Context context, long id) {
        getBoxDao(context).delete(getBoxForId(context, id));
    }

    public static void clearBoxs(Context context) {
        getBoxDao(context).deleteAll();
    }

    public static List<TSBox> getAllBoxes(Context context) {
        return getBoxDao(context).loadAll();
    }
}
