package com.dreamfly.timeschedule;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.umeng.socialize.PlatformConfig;

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
        //说明：需要使用第三方公司api的平台，新浪，腾讯等，是需要去申请appkey的。
        // 人人豆瓣只能在服务器端设置。其它需要配置appid的平台，如qq，微信，易信,twitter等都需要在本地设置。
        // 还有一部分平台需要在mainfest中配置，前面已经提到过。
        //友盟微信平台,需要去微信开放平台申请, 参数:appId,appSecret
//        PlatformConfig.setWeixin("wxb7ff70f8ed986b3f", "b5a58046118fb410b788d827ef9a6559");
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
