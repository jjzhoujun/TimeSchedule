package com.dreamfly.timeschedule.maininterface;

import java.text.SimpleDateFormat;
import java.util.HashMap;
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
import android.widget.ListView;
import android.widget.Toast;

import com.dreamfly.debuginfo.logPrint;
import com.dreamfly.timeschedule.R;
import com.dreamfly.timeschedule.maininterface.MyListView.OnRefreshListener;

public class MainInterface extends Activity{
	
	private ImageView mImgAdd = null;
	private EditText mEditText = null;
	private MyListView mListView;
	private MainBaseAdapter mAdapter;
	private Map<String, Object> mMap = null;

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
						logPrint.Debug("======onItemSelected===arg0 = " + arg0 + "; arg1 = " + arg1
								+"; arg2 = " + arg2 + "; arg3 = " + arg3);
						
						
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						logPrint.Debug("======onNothingSelected===arg0 = " + arg0);
						
					}
		});
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				logPrint.Debug("======onItemClick== arg0 = " + arg0 + ";arg1 = " +arg1
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
						logPrint.Debug("========noting to do=after refresh the item....");
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
	
	private void setClickListener(){
		View.OnClickListener onClickListener = new View.OnClickListener() {
			
			@SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch(v.getId()){
					case R.id.main_add:
						logPrint.Debug("====mEditText.getText() = " + mEditText.getText().toString());
						if(("").equals(mEditText.getText().toString())){
							Intent intent = new Intent();
							intent.setClass(MainInterface.this, AddTask.class);
							startActivity(intent);
						} else {
							SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");     
							String date = sDateFormat.format(new java.util.Date()); 
							mMap = new HashMap<String, Object>();
							mMap.put("heading", mEditText.getText().toString());
							mMap.put("stateinfo", "Emergency Important");
							mMap.put("time", date);
							logPrint.Debug("====mMap = " + mMap);
							mAdapter.addItem(mMap);
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