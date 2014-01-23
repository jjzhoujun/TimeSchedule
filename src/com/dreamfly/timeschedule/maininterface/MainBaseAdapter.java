package com.dreamfly.timeschedule.maininterface;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dreamfly.debuginfo.logPrint;
import com.dreamfly.timeschedule.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MainBaseAdapter extends BaseAdapter{
	private Context mContext;
	private List<Map<String, Object>> mData;
	
	private LayoutInflater mInflater;
	
	public MainBaseAdapter(Context context){
		mContext = context;
		mData = new ArrayList<Map<String, Object>>();
		mInflater = (LayoutInflater)mContext.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
	}

	public void addItem(final Map item){
//		logPrint.Debug("====addItem ..item = " + item);
		mData.add(item);
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		logPrint.Debug("====getCount === mData.size() = " + mData.size());
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		logPrint.Debug("====getItem === mData.get(position) = " + mData.get(position));
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		logPrint.Debug("====getItemId === position = " + position);
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
//		logPrint.Debug("====getView; position = " + position + "; convertView = " + convertView);
		ViewHolder holder = null;
		// 节省避免重复findViewById而导致的性能下降
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.item, null);
			holder = new ViewHolder();
			holder.checkBox = (CheckBox) convertView.findViewById(R.id.checked);
			holder.TxtHeading = (TextView) convertView.findViewById(R.id.heading);
			holder.TxtStateinfo = (TextView) convertView.findViewById(R.id.stateinfo);
			holder.TxtTime = (TextView) convertView.findViewById(R.id.time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		holder.TxtHeading.setText(mData.get(position).get("heading").toString());
		holder.TxtStateinfo.setText(mData.get(position).get("stateinfo").toString());
		holder.TxtTime.setText(mData.get(position).get("time").toString());
		holder.checkBox.setChecked(false);
		holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
//				logPrint.Debug("====onCheckedChanged==== buttonView = " + buttonView
//						+ "; isChecked = " + isChecked);
				if(isChecked){
					logPrint.Debug("===== delete the item task ， position = " + position);
					if(position < 0 || position >= mData.size()){
						logPrint.Debug("===== out of positio = " + position);
					} else {
						mData.remove(position);
						notifyDataSetChanged();
					}
				}
			}
		});
		return convertView;
	}
	
	public static class ViewHolder{
		public CheckBox checkBox;
		public TextView TxtHeading;
		public TextView TxtStateinfo;
		public TextView TxtTime;
	}
	
}