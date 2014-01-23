package com.dreamfly.timeschedule.maininterface;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.dreamfly.debuginfo.logPrint;
import com.dreamfly.timeschedule.R;

public class MainInterface extends Activity{
	
	private ImageView mImgAdd = null;
	private EditText mEditText = null;
	private ListView mListView;
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
//		for(int i = 0; i < 10; i++){
//			mAdapter.addItem("item " + i);
//		}
		mListView.setAdapter(mAdapter);
		mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					
					/**
					 * arg2  当前点击的item ？
					 * arg3 当前点击的item ？
					 * */
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
		
	}
	
	private void initUI(){
		mEditText = (EditText)findViewById(R.id.main_edit_task);
		mImgAdd = (ImageView)findViewById(R.id.main_add);
		mListView = (ListView)findViewById(R.id.main_list);
	}
	
	private void initData(){
	}
	
	private void setClickListener(){
		View.OnClickListener onClickListener = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch(v.getId()){
					case R.id.main_add:
						Toast.makeText(MainInterface.this, "点击添加", Toast.LENGTH_SHORT).show();
						SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd   hh:mm:ss");     
					    String   date   =   sDateFormat.format(new   java.util.Date()); 
						mMap = new HashMap<String, Object>();
						mMap.put("heading", mEditText.getText());
						mMap.put("stateinfo", "紧急重要性");
						mMap.put("time", date);
						
						mAdapter.addItem(mMap);
						break;
					default:
						break;
				}
			}
		};
		
		mImgAdd.setOnClickListener(onClickListener);
	}
	
	
}