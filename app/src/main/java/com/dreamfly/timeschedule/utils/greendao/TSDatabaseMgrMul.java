package com.dreamfly.timeschedule.utils.greendao;

import android.content.Context;

import com.dreamfly.debuginfo.LogPrint;
import com.dreamfly.timeschedule.bo.Entity;
import com.dreamfly.timeschedule.bo.TimeItemEntity;

import java.util.ArrayList;
import java.util.List;

import greendao.TSBox;

/**
 * Created by jayden on 15-9-13.
 */
public class TSDatabaseMgrMul {

    private Context mContext;
    private TSBox mTSBox;


    public TSDatabaseMgrMul(Context context) {
        mContext = context;
    }

    public void newDataBase(TimeItemEntity timeStruct) {
        mTSBox = new TSBox();
//            mTSBox.setId(mCurrentId);     // Set What ? id 可以自增长
        if (mTSBox.getB_finish() == null) {
            mTSBox.setB_finish(false);
        }
        if (mTSBox.getS_title() == null) {
            mTSBox.setS_title("");
        }
        if (mTSBox.getI_status() == null) {
            mTSBox.setI_status(0);      //0 means Import & Urgent
        }
        if (mTSBox.getS_notice() == null) {
            mTSBox.setS_notice("");
        }
        if (mTSBox.getS_start_time() == null) {
            mTSBox.setS_start_time("");
        }
        if (mTSBox.getS_end_time() == null) {
            mTSBox.setS_end_time("");
        }
        if (mTSBox.getB_alarm() == null) {
            mTSBox.setB_alarm(false);
        }

        mTSBox.setB_finish(timeStruct.getB_finish());
        mTSBox.setS_title(timeStruct.getS_titile());
        mTSBox.setI_status(timeStruct.getI_status());
        mTSBox.setS_notice(timeStruct.getS_notice());
        mTSBox.setS_start_time(timeStruct.getS_start_time());
        mTSBox.setS_end_time(timeStruct.getS_end_time());
        mTSBox.setB_alarm(timeStruct.getB_alarm());
        TSRepository.insertOrUpdate(mContext, mTSBox);
    }

    public List<TimeItemEntity> getAllBoxesData() {
        LogPrint.Debug("==>>>> get the all Boxes??? memory..");
        List<TSBox> tsBoxList = TSRepository.getAllBoxes(mContext);
        List<TimeItemEntity> timeStructList = new ArrayList<TimeItemEntity>();
        for(int i=0; i<tsBoxList.size(); i++) {
            TSBox tsBox = tsBoxList.get(i);
            LogPrint.Debug("===>>>i = " + i
                    + "; b_f = " + tsBox.getB_finish()
                    + "; s_title = " + tsBox.getS_title()
                    + "; i_status = " + tsBox.getI_status()
                    + "; s_notice = " + tsBox.getS_notice()
                    + "; s_start_time = " + tsBox.getS_start_time()
                    + "; s_end_time = " + tsBox.getS_end_time()
                    + "; b_alarm = " + tsBox.getB_alarm());
            TimeItemEntity timeStruct = new TimeItemEntity();
            timeStruct.setId(tsBox.getId());
            timeStruct.setB_finish(tsBox.getB_finish());
            timeStruct.setS_titile(tsBox.getS_title());
            timeStruct.setI_status(tsBox.getI_status());
            timeStruct.setS_notice(tsBox.getS_notice());
            timeStruct.setS_start_time(tsBox.getS_start_time());
            timeStruct.setS_end_time(tsBox.getS_end_time());
            timeStruct.setB_alarm(tsBox.getB_alarm());
            timeStructList.add(timeStruct);
        }
        return timeStructList;
    }

    public int getAllBoxesCount() {
        List<TSBox> tsBoxList = TSRepository.getAllBoxes(mContext);
        LogPrint.Debug(";;-=>>> the max ==>>> is  " + tsBoxList.size());
        return tsBoxList.size();
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
