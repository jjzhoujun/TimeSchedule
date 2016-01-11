package com.dreamfly.timeschedule;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

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
        //友盟微信平台,  appId  appSecret
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
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
}
