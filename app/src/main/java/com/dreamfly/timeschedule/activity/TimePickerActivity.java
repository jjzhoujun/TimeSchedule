package com.dreamfly.timeschedule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.dreamfly.debuginfo.LogPrint;
import com.dreamfly.timeschedule.R;
import com.dreamfly.timeschedule.bo.ConstantVar;
import com.dreamfly.timeschedule.bo.TimeItemEntity;
import com.dreamfly.timeschedule.utils.Tools;
import com.dreamfly.widget.TimePicker;

import de.greenrobot.event.EventBus;

/**
 * Created by jayden on 1/1/16.
 */
public class TimePickerActivity extends BaseActivity {

	// 临时做法，strTime的格式必须是 “MM月dd日 HH:mm” 的，不然截取出来的不对
	private String[] mTimeArray = {"", ""};
	private TimePicker mStartTimePicker;
	private TimePicker mEndTimePicker;
	private ImageButton mBtnBack;
	private Button mBtnSave;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		if(savedInstanceState != null) {
			restoreInstance(savedInstanceState);
		} else {
			if(intent != null) {
				mTimeArray = intent.getStringArrayExtra(ConstantVar.TASK_TIME);
				LogPrint.Debug("==> mTimeArray = " + mTimeArray);
			}
		}
		setContentView(R.layout.layout_time_picker);
		initView();
		initListener();
		initData();
	}

	private void initView() {
		mStartTimePicker = (TimePicker) findViewById(R.id.timepicker_start);
		mEndTimePicker = (TimePicker) findViewById(R.id.timepicker_end);
		mBtnBack = (ImageButton) findViewById(R.id.icon_back);
		mBtnSave = (Button) findViewById(R.id.icon_save);
	}

	private void initListener() {
		mBtnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		mBtnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LogPrint.Debug("00000 mTimeArray = " + mTimeArray[0]
					+ "; 1 = " + mTimeArray[1]);
				mTimeArray[0] = mStartTimePicker.getMonth() + "月"
						+ mStartTimePicker.getDay() + "日 "      // 日后面要加空格。
						+ mStartTimePicker.getHour() + ":"
						+ mStartTimePicker.getMinute();
				mTimeArray[1] = mEndTimePicker.getMonth() + "月"
						+ mEndTimePicker.getDay() + "日 "
						+ mEndTimePicker.getHour() + ":"
						+ mEndTimePicker.getMinute();
				LogPrint.Debug("1111 mTimeArray = " + mTimeArray[0]
					+ "; 1 = " + mTimeArray[1]);
				EventBus.getDefault().post(mTimeArray);
				finish();
			}
		});

	}

	public void initData() {
		if(mTimeArray != null) {
			String month;
			String day;
			String hour;
			String min;
			String startTime = mTimeArray[0];
			month = Tools.getMonth(startTime);
			day = Tools.getDay(startTime);
			hour = Tools.getHour(startTime);
			min = Tools.getMin(startTime);
			LogPrint.Debug("start, month = " + month + "; day = " + day + "; hour = " + hour + "; min = " + min);
			mStartTimePicker.setMonth(month);
			mStartTimePicker.setDay(day);
			mStartTimePicker.setHour(hour);
			mStartTimePicker.setMinute(min);

			String endTime = mTimeArray[1];
			month = Tools.getMonth(endTime);
			day = Tools.getDay(endTime);
			hour = Tools.getHour(endTime);
			min = Tools.getMin(endTime);
			LogPrint.Debug("start, month = " + month + "; day = " + day + "; hour = " + hour + "; min = " + min);
			mEndTimePicker.setMonth(month);
			mEndTimePicker.setDay(day);
			mEndTimePicker.setHour(hour);
			mEndTimePicker.setMinute(min);

			LogPrint.Debug("==>> startTime = " + startTime + "; endTime = " + endTime);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putStringArray(ConstantVar.TASK_TIME, mTimeArray);
		super.onSaveInstanceState(outState);
	}

	private void restoreInstance(Bundle savedInstanceState) {
		mTimeArray = savedInstanceState.getStringArray(ConstantVar.TASK_TIME);
	}

}
