package com.dreamfly.timeschedule.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.dreamfly.debuginfo.LogPrint;
import com.dreamfly.timeschedule.R;
import com.dreamfly.timeschedule.adapter.MainBaseAdapter;
import com.dreamfly.timeschedule.utils.CommonUtils;
import com.dreamfly.timeschedule.view.widget.ListViewPullToRef;
import com.dreamfly.timeschedule.view.widget.ListViewPullToRef.OnRefreshListener;
import com.dreamfly.timeschedule.model.TimeItemEntity;
import com.dreamfly.timeschedule.utils.greendao.TSDatabaseMgrMul;
import com.dreamfly.timeschedule.view.widget.EditTextWithDel;

import java.util.List;

public class UIMainListActivity extends Activity{
	
	private ImageView mImgAdd = null;
	private EditTextWithDel mEditText = null;
	private ListViewPullToRef mListView;
	private MainBaseAdapter mAdapter;
    private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_tasks);
		initUI();
		initData();
		setClickListener();
		// create adapt
		mAdapter = new MainBaseAdapter(this);
		// For test
//		for(int i = 0; i < 10; i++){
//			mAdapter.addItem("item " + i);
//		}
		mListView.setAdapter(mAdapter);
		mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                LogPrint.Debug("======onItemSelected===arg0 = " + arg0 + "; arg1 = " + arg1
                        + "; arg2 = " + arg2 + "; arg3 = " + arg3);


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                LogPrint.Debug("======onNothingSelected===arg0 = " + arg0);

            }
        });
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                LogPrint.Debug("======onItemClick== arg0 = " + arg0 + ";arg1 = " + arg1
                        + ";arg2 = " + arg2 + ";arg3 = " + arg3);
            }

        });
		
		mListView.setonRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                new AsyncTask<Void, Void, Void>() {
                    protected Void doInBackground(Void... params) {
//						try {
//							Thread.sleep(500);
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//						data.addFirst("刷新后的内容");
                        LogPrint.Debug("========noting to do=after refresh the item....");
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        addTimeTask();
//                        mListView.onRefreshComplete();
                    }

                }.execute();
            }
        });

        showDatabaseView();
		
	}
	
	private void initUI(){
		mEditText = (EditTextWithDel)findViewById(R.id.main_edit_task);
		mImgAdd = (ImageView)findViewById(R.id.main_add);
		mListView = (ListViewPullToRef)findViewById(R.id.main_list);
		View headerView = getLayoutInflater().inflate(R.layout.showlisthead, mListView, false);
		mListView.addHeaderView(headerView);
	}
	
	private void initData(){
        mContext = this;

	}

    private void showDatabaseView() {
		TSDatabaseMgrMul tsDatabaseMgrMul = new TSDatabaseMgrMul(this);
		List<TimeItemEntity> timeStructList = tsDatabaseMgrMul.getAllBoxesData();
		for(int i=0; i<timeStructList.size(); i++) {
			mAdapter.addItem(timeStructList.get(i));
		}
    }
	
	private void setClickListener(){
		View.OnClickListener onClickListener = new View.OnClickListener() {
			
			@SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch(v.getId()){
					case R.id.main_add:
						LogPrint.Debug("====mEditText.getText() = " + mEditText.getText().toString());
                        addTimeTask();
						break;
					default:
						break;
				}
			}
		};
		
		mImgAdd.setOnClickListener(onClickListener);
	}

    private void addTimeTask() {
        if(("").equals(mEditText.getText().toString())){
            Intent intent = new Intent();
            intent.setClass(UIMainListActivity.this, UIAddTaskActivity.class);
            startActivity(intent);
        } else {
            TimeItemEntity timeStruct = CommonUtils.getInstance(mContext)
                    .saveTimeStuct(mEditText.getText().toString());
            mAdapter.addItem(timeStruct);
            mEditText.setText("");
        }
    }
	
	
}