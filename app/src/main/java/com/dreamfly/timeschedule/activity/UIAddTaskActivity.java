package com.dreamfly.timeschedule.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamfly.debuginfo.LogPrint;
import com.dreamfly.timeschedule.R;
import com.dreamfly.timeschedule.bo.ConstantVar;
import com.dreamfly.timeschedule.bo.TimeItemEntity;
import com.dreamfly.timeschedule.utils.CommonUtils;
import com.dreamfly.timeschedule.utils.Tools;
import com.dreamfly.widget.EditTextWithDel;
import com.umeng.analytics.MobclickAgent;

import de.greenrobot.event.EventBus;

public class UIAddTaskActivity extends BaseActivity{

	private static final String TAG = "UIAddTaskActivity";
	private View mVFirstLevel;
	private View mVSecondLevel;
	private View mVThirdLevel;
	private View mVFourthLevel;
	private CheckBox mCBFirstLevel;
	private CheckBox mCBSecondLevel;
	private CheckBox mCBThirdLevel;
	private CheckBox mCBFourthLevel;
	private ImageButton mBackBtn;
	private Button mSaveBtn;
	private EditTextWithDel mEditTitle;
	private EditTextWithDel mEditNotice;
	private View mLyAlarm;
	private TextView mTextSTime;
	private TextView mTextETime;
	private TimeItemEntity mTimeItemEntity;
	private boolean mFinish;
	private String mTitle;
	private String mComment;
	private int mLevel = ConstantVar.STATUS_FIRST_LEVEL;
	private String mStartTime;
	private String mEndTime;
	private boolean mAlarm;
	private String[] mTimeArray = {"", ""};


	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		if(savedInstanceState != null) {
//			LogPrint.Warning("saveInstanceState != null... resotre");
			restoreInstance(savedInstanceState);
		} else {
			if(intent != null) {
				mTimeItemEntity = (TimeItemEntity)intent.getSerializableExtra(ConstantVar.ADD_TASK);
			}
		}
		if(mTimeItemEntity != null) {
//			LogPrint.Debug("timeEntity = " + mTimeItemEntity.toString());
			mFinish = mTimeItemEntity.getB_finish();
			mTitle = mTimeItemEntity.getS_titile();
			mComment = mTimeItemEntity.getS_notice();
			mLevel = mTimeItemEntity.getI_status();
			mStartTime = mTimeItemEntity.getS_start_time();
			mEndTime = mTimeItemEntity.getS_end_time();
		}
		setContentView(R.layout.layout_add_task);
		initView();
		initListener();
		EventBus.getDefault().register(this);
		configData();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
//		LogPrint.Warning("=>>start to onSavedInstanceState");
		outState.putSerializable(ConstantVar.TASK_DATA, mTimeItemEntity);
		super.onSaveInstanceState(outState);
	}

	private void restoreInstance(Bundle savedInstanceState) {
		mTimeItemEntity = (TimeItemEntity)savedInstanceState.getSerializable(ConstantVar.TASK_DATA);
	}

	private void initView() {
		mVFirstLevel = findViewById(R.id.ll_first_level);
		mVSecondLevel = findViewById(R.id.ll_second_level);
		mVThirdLevel = findViewById(R.id.ll_third_level);
		mVFourthLevel = findViewById(R.id.ll_fourth_level);
		mCBFirstLevel = (CheckBox) findViewById(R.id.cb_first_level);
		mCBSecondLevel = (CheckBox) findViewById(R.id.cb_second_level);
		mCBThirdLevel = (CheckBox) findViewById(R.id.cb_third_level);
		mCBFourthLevel = (CheckBox) findViewById(R.id.cb_fourth_level);
		mBackBtn = (ImageButton) findViewById(R.id.icon_back);
		mSaveBtn = (Button) findViewById(R.id.icon_save);
		mEditTitle = (EditTextWithDel) findViewById(R.id.main_edit_task);
		mEditNotice = (EditTextWithDel) findViewById(R.id.edit_notice);
		mLyAlarm = findViewById(R.id.alarm_notice);
		mTextSTime = (TextView) findViewById(R.id.start_time);
		mTextETime = (TextView) findViewById(R.id.end_time);

		mEditTitle.setText(mTitle);
		mEditNotice.setText(mComment);
		setStatusView(mLevel);
	}

	private void initListener() {
		mVFirstLevel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mLevel = ConstantVar.STATUS_FIRST_LEVEL;
				setStatusView(ConstantVar.STATUS_FIRST_LEVEL);
				MobclickAgent.onEvent(UIAddTaskActivity.this, "StatusFirst");
			}
		});
		mVSecondLevel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mLevel = ConstantVar.STATUS_SECOND_LEVEL;
				setStatusView(ConstantVar.STATUS_SECOND_LEVEL);
				MobclickAgent.onEvent(UIAddTaskActivity.this, "StatusSecond");
			}
		});
		mVThirdLevel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mLevel = ConstantVar.STATUS_THIRD_LEVEL;
			    setStatusView(ConstantVar.STATUS_THIRD_LEVEL);
				MobclickAgent.onEvent(UIAddTaskActivity.this, "StatusThird");
			}
		});
		mVFourthLevel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mLevel = ConstantVar.STATUS_FOURTH_LEVEL;
				setStatusView(ConstantVar.STATUS_FOURTH_LEVEL);
				MobclickAgent.onEvent(UIAddTaskActivity.this, "StatusFourth");
			}
		});

		mLyAlarm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LogPrint.Debug("==>> Click Alarm Layout, show.. = " + mTimeArray);
				CommonUtils.getInstance(UIAddTaskActivity.this).startTimePickerActivity(
						UIAddTaskActivity.this, mTimeArray);
				MobclickAgent.onEvent(UIAddTaskActivity.this, "TimeChoose");
			}
		});

		mBackBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				MobclickAgent.onEvent(UIAddTaskActivity.this, "BackInAddTask");
				finish();
			}
		});
		mSaveBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				LogPrint.Debug("start to save the item to database...");
				MobclickAgent.onEvent(UIAddTaskActivity.this, "SaveInAddTask");
				mTitle = mEditTitle.getText().toString();
				if ("".equals(mTitle)) {
					Toast.makeText(UIAddTaskActivity.this, R.string.str_plz_title, Toast.LENGTH_SHORT).show();
					return;
				}
				mComment = mEditNotice.getText().toString();
				long id = 0;
				if (mTimeItemEntity == null) {
					mTimeItemEntity = new TimeItemEntity();
					id = CommonUtils.getInstance(UIAddTaskActivity.this).getId();
					mTimeItemEntity.setId(id);
					id++;
					LogPrint.Debug("==>>jayden, lastId id = " + id);
					CommonUtils.getInstance(UIAddTaskActivity.this).setId(id);
					mTimeItemEntity.setAddFlag(true);
				} else {
					id = mTimeItemEntity.getId();
					mTimeItemEntity.setAddFlag(false);
				}
				LogPrint.Debug("==>>Add Task id = " + id);
				mTimeItemEntity.setS_titile(mTitle);
				mTimeItemEntity.setI_status(mLevel);
				mTimeItemEntity.setS_notice(mComment);
				mTimeItemEntity.setS_start_time(mStartTime);
				mTimeItemEntity.setS_end_time(mEndTime);
				CommonUtils.getInstance(UIAddTaskActivity.this).saveTimeStruct(mTimeItemEntity);
				EventBus.getDefault().post(mTimeItemEntity);
				finish();
			}
		});
	}

	private void setStatusView(final int level) {
		switch (level) {
			case ConstantVar.STATUS_FIRST_LEVEL:
				mCBFirstLevel.setChecked(true);
				mCBSecondLevel.setChecked(false);
				mCBThirdLevel.setChecked(false);
				mCBFourthLevel.setChecked(false);
				break;
			case ConstantVar.STATUS_SECOND_LEVEL:
				mCBFirstLevel.setChecked(false);
				mCBSecondLevel.setChecked(true);
				mCBThirdLevel.setChecked(false);
				mCBFourthLevel.setChecked(false);
				break;
			case ConstantVar.STATUS_THIRD_LEVEL:
				mCBFirstLevel.setChecked(false);
				mCBSecondLevel.setChecked(false);
				mCBThirdLevel.setChecked(true);
				mCBFourthLevel.setChecked(false);
				break;
			case ConstantVar.STATUS_FOURTH_LEVEL:
				mCBFirstLevel.setChecked(false);
				mCBSecondLevel.setChecked(false);
				mCBThirdLevel.setChecked(false);
				mCBFourthLevel.setChecked(true);
				break;
			default:
				mCBFirstLevel.setChecked(true);
				mCBSecondLevel.setChecked(false);
				mCBThirdLevel.setChecked(false);
				mCBFourthLevel.setChecked(false);
				break;
		}
	}

	private void configData() {
		if(mStartTime == null || "".equals(mStartTime)) {
			String sTime = Tools.getCurTimeStr();
			mTextSTime.setText(sTime);
			mStartTime = sTime;
		} else {
			mTextSTime.setText(mStartTime);
		}

		if(mEndTime == null || "".equals(mEndTime)) {
			String endTime;
			if(mStartTime != null) {
				endTime = mStartTime;
			} else {
				endTime = Tools.getCurTimeStr();
			}
			mTextETime.setText(endTime);
			mEndTime = endTime;
		} else {
			mTextETime.setText(mEndTime);
		}

		mTimeArray[0] = mStartTime;
		mTimeArray[1] = mEndTime;
	}

	/**
	 * 接收从TimePickerActivity发生过来的事件。
	 * */
	public void onEventMainThread(String[] timeArray) {
		LogPrint.Debug("==>> receive.. timeArray 0 = " + timeArray[0]
			+ "; 1 = " + timeArray[1]);
		mStartTime = timeArray[0];
		mEndTime = timeArray[1];
		mTextSTime.setText(mStartTime);
		mTextETime.setText(mEndTime);
	}
}