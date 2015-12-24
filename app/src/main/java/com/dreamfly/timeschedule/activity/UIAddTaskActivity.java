package com.dreamfly.timeschedule.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;

import com.dreamfly.timeschedule.R;
import com.dreamfly.timeschedule.model.ConstantVar;
import com.dreamfly.timeschedule.utils.CommonUtils;
import com.dreamfly.timeschedule.view.widget.EditTextWithDel;

public class UIAddTaskActivity extends Activity{

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
	private ImageButton mDelBtn;
	private EditTextWithDel mEditTitle;
	private EditTextWithDel mEditNotice;
	private int mStatus = ConstantVar.STATUS_FIRST_LEVEL;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_add_task);
		initView();
		initListener();
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
		mDelBtn = (ImageButton) findViewById(R.id.icon_delete);
		mEditTitle = (EditTextWithDel) findViewById(R.id.main_edit_task);
		mEditNotice = (EditTextWithDel) findViewById(R.id.edit_notice);
		setStatusView(ConstantVar.STATUS_FIRST_LEVEL);
	}

	private void initListener() {
		mVFirstLevel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mStatus = ConstantVar.STATUS_FIRST_LEVEL;
				setStatusView(ConstantVar.STATUS_FIRST_LEVEL);
			}
		});
		mVSecondLevel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mStatus = ConstantVar.STATUS_SECOND_LEVEL;
				setStatusView(ConstantVar.STATUS_SECOND_LEVEL);
			}
		});
		mVThirdLevel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mStatus = ConstantVar.STATUS_THIRD_LEVEL;
			    setStatusView(ConstantVar.STATUS_THIRD_LEVEL);
			}
		});
		mVFourthLevel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mStatus = ConstantVar.STATUS_FOURTH_LEVEL;
				setStatusView(ConstantVar.STATUS_FOURTH_LEVEL);
			}
		});

		mBackBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				finish();
			}
		});
		mSaveBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String strTitle = mEditTitle.getText().toString();
//				CommonUtils.getInstance(UIAddTaskActivity.this).saveTimeStuct(strTitle);
			}
		});
		mDelBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

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
				break;
		}
	}
}