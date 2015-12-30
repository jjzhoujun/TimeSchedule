package com.dreamfly.timeschedule.model;

/**
 * Created by jayden on 15-9-15.
 */
public class TimeItemEntity {
    private long _id;
    private boolean b_finish;   // 是否完成,并删除
    private String s_titile;    // 标题
    private int i_status;       // 优先级
    private String s_notice;    // 备注信息
    private String s_start_time;// 起始时间.
    private String s_end_time;  // 结束时间.
    private boolean b_alarm;    // 是否需要闹铃提醒.

    public TimeItemEntity() {

    }

    public void setId(long value) {
        this._id = value;
    }

    public long getId() {
        return this._id;
    }

    public void setB_finish(boolean value) {
        this.b_finish = value;
    }

    public boolean getB_finish() {
        return this.b_finish;
    }

    public void setS_titile(String str) {
        this.s_titile = str;
    }

    public String getS_titile() {
        return this.s_titile;
    }

    public void setI_status(int value) {
        this.i_status = value;
    }

    public int getI_status() {
        return this.i_status;
    }

    public void setS_notice(String str) {
        this.s_notice = str;
    }

    public String getS_notice() {
        return this.s_notice;
    }

    public void setS_start_time(String str) {
        this.s_start_time = str;
    }

    public String getS_start_time() {
        return this.s_start_time;
    }

    public void setS_end_time(String str) {
        this.s_end_time = str;
    }

    public String getS_end_time() {
        return this.s_end_time;
    }

    public void setB_alarm(boolean bOn) {
        this.b_alarm = bOn;
    }

    public boolean getB_alarm() {
        return this.b_alarm;
    }


    @Override
    public String toString() {
        return "isFinish = " + b_finish
                + "; title = " + s_titile
                + "; status = " + i_status
                + "; notice = " + s_notice
                + "; s_time = " + s_start_time
                + "; end_time = " + s_end_time
                + "; bAlarm = " + b_alarm;
    }


}
