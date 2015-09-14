package com.dreamfly.timeschedule.utils.greendao;

import android.content.Context;

import com.dreamfly.debuginfo.LogPrint;
import com.dreamfly.timeschedule.model.TimeStruct;

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

    public void newDataBase() {
        mTSBox = new TSBox();
//            mTSBox.setId(mCurrentId);     // Set What ?
        if (mTSBox.getB_finish() == null) {
            LogPrint.Debug("This is NULL...=getB_finish==>>>>> ... ");
            mTSBox.setB_finish(false);
        }
        if (mTSBox.getS_title() == null) {
            mTSBox.setS_title("");
        }
        if (mTSBox.getI_status() == null) {
            mTSBox.setI_status(0);      //0 means Import & Urgent
        }
        if (mTSBox.getS_start_time() == null) {
            mTSBox.setS_start_time("");
        }
        TSRepository.insertOrUpdate(mContext, mTSBox);
    }

    public void setDataBox(TimeStruct timeStruct) {
        mTSBox.setId(timeStruct.getId());
        mTSBox.setB_finish(timeStruct.getB_finish());
        mTSBox.setS_title(timeStruct.getS_titile());
        mTSBox.setI_status(timeStruct.getI_status());
        mTSBox.setS_start_time(timeStruct.getS_start_time());
        TSRepository.insertOrUpdate(mContext, mTSBox);
    }

    public List<TimeStruct> getAllBoxesData() {
        LogPrint.Debug("==>>>> get the all Boxes??? memory..");
        List<TSBox> tsBoxList = TSRepository.getAllBoxes(mContext);
        List<TimeStruct> timeStructList = new ArrayList<TimeStruct>();
        for(int i=0; i<tsBoxList.size(); i++) {
            LogPrint.Debug("===>>>i = " + i + "; tsBox = " + tsBoxList.get(i)
                + "; b_f = " + tsBoxList.get(i).getB_finish()
                + "; s_title = " + tsBoxList.get(i).getS_title()
                + "; i_status = " + tsBoxList.get(i).getI_status()
                + "; s_time = " + tsBoxList.get(i).getS_start_time());
            TimeStruct timeStruct = new TimeStruct();
            timeStruct.setId(tsBoxList.get(i).getId());
            timeStruct.setB_finish(tsBoxList.get(i).getB_finish());
            timeStruct.setS_titile(tsBoxList.get(i).getS_title());
            timeStruct.setI_status(tsBoxList.get(i).getI_status());
            timeStruct.setS_start_time(tsBoxList.get(i).getS_start_time());
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
