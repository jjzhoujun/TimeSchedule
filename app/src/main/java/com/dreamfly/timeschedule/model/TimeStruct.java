package com.dreamfly.timeschedule.model;

/**
 * Created by jayden on 15-9-15.
 */
public class TimeStruct {
    private long _id;
    private boolean b_finish;
    private String s_titile;
    private int i_status;
    private String s_start_time;

    public TimeStruct() {

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

    public void setS_start_time(String str) {
        this.s_start_time = str;
    }

    public String getS_start_time() {
        return this.s_start_time;
    }
}
