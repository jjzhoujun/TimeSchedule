package com.dreamfly.timeschedule;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;

import greendao.DaoMaster;
import greendao.DaoSession;

/**
 * Created by jayden on 15-9-13.
 */
public class TSApplication extends Application {

    public DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initDataBase();
        initFeedback();
    }

    private void initDataBase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "timeschedule-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    private void initFeedback() {
        FeedbackAPI.init(this, "23591093");
    }
}
