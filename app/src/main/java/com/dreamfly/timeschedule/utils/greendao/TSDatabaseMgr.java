package com.dreamfly.timeschedule.utils.greendao;

import android.content.Context;

import com.dreamfly.debuginfo.LogPrint;

import greendao.TSBox;

/**
 * Created by jayden on 15-9-13.
 */
public class TSDatabaseMgr {
    private static TSDatabaseMgr sInstance;
    private Context mContext;
    private static long mCurrentId = 0;
    private TSBox mTSBox;

    public static synchronized TSDatabaseMgr getInstance(Context context) {
        if(sInstance == null) {
            sInstance = new TSDatabaseMgr(context);
        }
        return sInstance;
    }

    public TSDatabaseMgr(Context context) {
        mContext = context.getApplicationContext();
    }

    public void initData() {
        // First. Read from database.
        mTSBox = TSRepository.getBoxForId(mContext, mCurrentId);
        if(mTSBox == null) {
            mTSBox = new TSBox();
            mTSBox.setId(mCurrentId);
            if(mTSBox.getB_finish() == null) {
                LogPrint.Debug("This is NULL...=getB_finish==>>>>> ... ");
                mTSBox.setB_finish(false);
            }
            if(mTSBox.getS_title() == null) {
                mTSBox.setS_title("");
            }
            if(mTSBox.getI_status() == null) {
                mTSBox.setI_status(0);      //0 means Import & Urgent
            }
            if(mTSBox.getS_start_time() == null) {
                mTSBox.setS_start_time("");
            }
        } else {
            mTSBox.setId(mCurrentId);
        }
        TSRepository.insertOrUpdate(mContext, mTSBox);
    }

    public void setTSFinish(boolean value) {
        mTSBox.setB_finish(value);
        TSRepository.insertOrUpdate(mContext, mTSBox);
    }

    public boolean getTSFinish() {
        return mTSBox.getB_finish();
    }

    public void setTSTitle(String value) {
        mTSBox.setS_title(value);
        TSRepository.insertOrUpdate(mContext, mTSBox);
    }

    public String getTSTitle() {
        return mTSBox.getS_title();
    }

    /**
     * value
     * 0  Import & Urgent
     * 1  Import & Not Urgent
     * 2  Not Import & Urgent
     * 3  Not Import & Not Urgent
     * */
    public void setTSStatus(int value) {
        mTSBox.setI_status(value);
        TSRepository.insertOrUpdate(mContext, mTSBox);
    }

    public int getTSStatus() {
        return mTSBox.getI_status();
    }

    public void setTSStartTime(String value) {
        mTSBox.setS_start_time(value);
        TSRepository.insertOrUpdate(mContext, mTSBox);
    }

    public String getTSStartTime() {
        return mTSBox.getS_start_time();
    }


}
