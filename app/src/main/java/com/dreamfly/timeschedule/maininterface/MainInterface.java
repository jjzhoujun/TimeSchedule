package com.dreamfly.timeschedule.maininterface;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;

import com.dreamfly.debuginfo.LogPrint;
import com.dreamfly.timeschedule.R;
import com.dreamfly.timeschedule.maininterface.MyListView.OnRefreshListener;
import com.dreamfly.timeschedule.model.TimeStruct;
import com.dreamfly.timeschedule.utils.greendao.TSDatabaseMgr;
import com.dreamfly.timeschedule.utils.greendao.TSDatabaseMgrMul;
import com.dreamfly.timeschedule.utils.greendao.TSRepository;

import greendao.TSBox;

public class MainInterface extends Activity{
	
	private ImageView mImgAdd = null;
	private EditText mEditText = null;
	private MyListView mListView;
	private MainBaseAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainlistinterface);
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
                        // pop new Activity to create a new task.
                        Intent intent = new Intent();
                        intent.setClass(MainInterface.this, AddTask.class);
                        startActivity(intent);
//						mAdapter.notifyDataSetChanged();
                        mListView.onRefreshComplete();
                    }

                }.execute();
            }
        });

        showDatabaseView();
		
	}
	
	private void initUI(){
		mEditText = (EditText)findViewById(R.id.main_edit_task);
		mImgAdd = (ImageView)findViewById(R.id.main_add);
		mListView = (MyListView)findViewById(R.id.main_list);
		View headerView = getLayoutInflater().inflate(R.layout.showlisthead, mListView, false);
		mListView.addHeaderView(headerView);
	}
	
	private void initData(){

	}

    private void showDatabaseView() {
		TSDatabaseMgrMul tsDatabaseMgrMul = new TSDatabaseMgrMul(this);
		List<TimeStruct> timeStructList = tsDatabaseMgrMul.getAllBoxesData();
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
						if(("").equals(mEditText.getText().toString())){
							Intent intent = new Intent();
							intent.setClass(MainInterface.this, AddTask.class);
							startActivity(intent);
						} else {
							SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");     
							String date = sDateFormat.format(new java.util.Date()); 
//							TSDatabaseMgrMul tsDatabaseMgrMul = new TSDatabaseMgrMul(getApplicationContext()); // Memory leak??
//							tsDatabaseMgrMul.newDataBase();
//							tsDatabaseMgrMul.setTSFinish(false);
//                            tsDatabaseMgrMul.setTSTitle(mEditText.getText().toString());
//							tsDatabaseMgrMul.setTSStatus(0);
//							tsDatabaseMgrMul.setTSStartTime(date);
//							mAdapter.addItem(tsDatabaseMgrMul);
							TimeStruct timeStruct = new TimeStruct(); // Memory leak??
							timeStruct.setB_finish(false);
							timeStruct.setS_titile(mEditText.getText().toString());
							timeStruct.setI_status(0);
							timeStruct.setS_start_time(date);
							TSDatabaseMgrMul tsDatabaseMgrMul = new TSDatabaseMgrMul(getApplicationContext()); // Memory leak??
							tsDatabaseMgrMul.newDataBase();
							tsDatabaseMgrMul.setDataBox(timeStruct);
							mAdapter.addItem(timeStruct);
							mEditText.setText("");
						}
						break;
					default:
						break;
				}
			}
		};
		
		mImgAdd.setOnClickListener(onClickListener);
	}
	
	
}