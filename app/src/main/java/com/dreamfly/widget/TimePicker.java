package com.dreamfly.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.dreamfly.timeschedule.R;

import java.util.ArrayList;


/**
 * container 3 wheelView implement timePicker
 * Created by JiangPing on 2015/6/17.
 * Modify by Jayden on 2016-01-02
 */
public class TimePicker extends LinearLayout {
    private WheelView mWheelYear;  // 暂时不支持润年的情况，29天。。。
    private WheelView mWheelMonth;
    private WheelView mWheelDay;
    private WheelView mWheelHour;
    private WheelView mWheelMin;
    private ArrayList<String> mMonthList = new ArrayList<>();
    private ArrayList<String> mDayList = new ArrayList<>();
    private int mDayIndex = 0;
    private String mCurMonth;
    private String mCurDay;
    private String mCurHour;
    private String mCurMinute;

    public TimePicker(Context context) {
        this(context, null);
    }

    public TimePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setMonth(String month) {
        this.mCurMonth = month;
        mWheelMonth.setDefault(Integer.parseInt(mCurMonth) - 1);

    }

    public String getMonth() {
        return mCurMonth;
    }

    public void setDay(String day) {
        this.mCurDay = day;
        mDayList = getDayData(mCurMonth);
        mWheelDay.setData(mDayList);
        int index = Integer.parseInt(mCurDay) - 1;
        mWheelDay.setDefault(index);
        mDayIndex = index;
    }

    public String getDay() {
        return mCurDay;
    }

    public void setHour(String hour) {
        this.mCurHour = hour;
        mWheelHour.setDefault(Integer.parseInt(mCurHour));
    }

    public String getHour() {
        return mCurHour;
    }

    public void setMinute(String minute) {
        this.mCurMinute = minute;
        mWheelMin.setDefault(Integer.parseInt(mCurMinute));
    }

    public String getMinute() {
        return mCurMinute;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.time_picker, this);
        mWheelMonth = (WheelView) findViewById(R.id.month);
        mWheelDay = (WheelView) findViewById(R.id.day);
        mWheelHour = (WheelView) findViewById(R.id.hour);
        mWheelMin = (WheelView) findViewById(R.id.minute);

        mMonthList = getMonthData();
        mWheelMonth.setData(mMonthList);
        mWheelDay.setData(getDayData());
        mWheelHour.setData(getHourData());
        mWheelMin.setData(getMinData());

        mWheelMonth.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                // 回调某个接口，让外部的可以实现该接口，从而存储数据。
                mDayList = getDayData(text);
                mWheelDay.setData(mDayList);
                if (mDayIndex >= mDayList.size()) {
                    mDayIndex = mDayList.size() - 1;
                    mCurDay = mDayList.get(mDayIndex);
                }
                mWheelDay.setDefault(mDayIndex);
                mCurMonth = text;
            }

            @Override
            public void selecting(int id, String text) {
            }
        });

        mWheelDay.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                mDayIndex = id;
                mCurDay = text;
            }

            @Override
            public void selecting(int id, String text) {
            }
        });

        mWheelHour.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                mCurHour = text;
            }

            @Override
            public void selecting(int id, String text) {

            }
        });

        mWheelMin.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                mCurMinute = text;
            }

            @Override
            public void selecting(int id, String text) {

            }
        });
    }

    private ArrayList<String> getMonthData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            list.add(String.format("%02d", i));
        }
        return list;
    }

    // 默认有31天。
    private ArrayList<String> getDayData() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i < 31; i++) {
            list.add(String.format("%02d", i));
        }
        return list;
    }

    private ArrayList<String> getDayData(final String month) {
        ArrayList<String> list = new ArrayList<>();
        int dayCount = 1;
        final int iMonth = Integer.parseInt(month);
        switch(iMonth) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                dayCount = 31;
                break;
            case 2:
                dayCount = 28;  //WTF, 还需要判断是否是润年。
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                dayCount = 30;
                break;
            default:
                dayCount = 31;
                break;
        }

        for(int i=1; i<=dayCount; i++) {
            list.add(String.format("%02d", i));
        }
        return list;
    }

    private ArrayList<String> getHourData() {
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i <= 23; i++) {
            list.add(String.format("%02d", i));
        }
        return list;
    }

    private ArrayList<String> getMinData() {
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i <= 59; i++) {
            list.add(String.format("%02d", i));
        }
        return list;
    }
}
