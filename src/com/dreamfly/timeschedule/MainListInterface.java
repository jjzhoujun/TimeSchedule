package com.dreamfly.timeschedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainListInterface extends Activity{
	private ListView mListView;
	private List<Map<String, Object>> mList;
	private Map<String, Object> mMap;
	private String[] mFrom = new String[]{"heading", "stateinfo", "time"};
	private int[] mTo = new int[]{R.id.heading, R.id.stateinfo, R.id.time};
	private ImageView imgAdd = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainlistinterface);
		initUI();
		initData();
		setClickListener();
		// create adapt
		SimpleAdapter mAdapter = new SimpleAdapter(this, mList, R.layout.item, mFrom, mTo){
			@Override
			public View getView(final int position, View convertView, ViewGroup parent){
				System.out.println("============call getView===============");
				View view = super.getView(position, convertView, parent);
				@SuppressWarnings("unchecked")
				final HashMap<String, Object> map = (HashMap<String, Object>) this.getItem(position);
				//获取相应View中的Checkbox对象  
				CheckBox checkBox = (CheckBox)view.findViewById(R.id.checked);
				checkBox.setChecked((Boolean) map.get("checked"));
				checkBox.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						map.put("checked", ((CheckBox) v).isChecked());
					}
					
				});
				return view;
			}
		};
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				// 获取当前点击item所对应的数据
				@SuppressWarnings("unchecked")
				HashMap<String, Object> map = (HashMap<String, Object>)parent.getItemAtPosition(position);
				// 处理其他事务
				
			}
			
		});
	}
	
	private void initUI(){
		mListView = (ListView)findViewById(R.id.main_list);
		imgAdd = (ImageView)findViewById(R.id.main_add);
	}
	
	private void initData(){
		mList = new ArrayList<Map<String,Object>>();
		for(int i = 0; i < 1000; i++){
			mMap = new HashMap<String, Object>();
			mMap.put("heading", "标题");
			mMap.put("stateinfo", "重要紧急性");
			mMap.put("time", "2014-01-20 22:10");
			mMap.put("checked", true);
			mList.add(mMap);
		}
	}
	
	private void setClickListener(){
		View.OnClickListener onClickListener = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch(v.getId()){
					case R.id.main_add:
						Toast.makeText(MainListInterface.this, "点击添加", Toast.LENGTH_SHORT).show();
						break;
					default:
						break;
				}
			}
		};
		
		imgAdd.setOnClickListener(onClickListener);
	}
	
	
}
