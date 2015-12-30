package com.dreamfly.timeschedule.adapter;


import java.util.ArrayList;
import java.util.List;

import com.dreamfly.debuginfo.LogPrint;
import com.dreamfly.timeschedule.R;
import com.dreamfly.timeschedule.model.TimeItemEntity;
import com.dreamfly.timeschedule.utils.CommonUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainBaseAdapter extends BaseAdapter{
	private Context mContext;
	private List<TimeItemEntity> mData;
	
	private LayoutInflater mInflater;
	
	public MainBaseAdapter(Context context){
		mContext = context;
		mData = new ArrayList<TimeItemEntity>();
	}

	public void addItem(final TimeItemEntity item){
		mData.add(item);
		notifyDataSetChanged();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
//		LogPrint.Debug("====getView; position = " + position);
		ViewHolder holder = null;
		// Optimize the speed
		if(convertView == null){
			convertView = View.inflate(mContext, R.layout.item, null);
			holder = new ViewHolder();
            holder.rlayout = (RelativeLayout) convertView.findViewById(R.id.ly_main_item);
			holder.checkBox = (CheckBox) convertView.findViewById(R.id.checked);
			holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.titleCenter = (TextView) convertView.findViewById(R.id.title_center);
			holder.comment = (TextView) convertView.findViewById(R.id.comment);
			holder.startTime = (TextView) convertView.findViewById(R.id.start_time);
			holder.endTime = (TextView) convertView.findViewById(R.id.end_time);
            holder.centerTime = (TextView) convertView.findViewById(R.id.time_center);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		String title = mData.get(position).getS_titile();
		String comment = mData.get(position).getS_notice();
		int status = mData.get(position).getI_status();
		String startTime = mData.get(position).getS_start_time();
		String endTime = mData.get(position).getS_end_time();
		boolean bAlarm = mData.get(position).getB_alarm();

		LogPrint.Debug("title = " + title + "; comment = " + comment + "; status = " + status + "; s_time = " + startTime + "; endTime = " + endTime + "; alarm = " + bAlarm);

        if(comment == null || "".equals(comment)) {
            holder.titleCenter.setText(title);
            holder.titleCenter.setVisibility(View.VISIBLE);
            holder.title.setVisibility(View.GONE);
            holder.comment.setVisibility(View.GONE);
        } else {
            holder.title.setText(title);
            holder.comment.setText(comment);
        }

        if(endTime == null || "".equals(endTime)) {
            holder.centerTime.setText(startTime);
            holder.centerTime.setVisibility(View.VISIBLE);
            holder.startTime.setVisibility(View.GONE);
            holder.endTime.setVisibility(View.GONE);
        } else {
            holder.startTime.setText(startTime);
            holder.endTime.setText(endTime);
        }
        holder.rlayout.setBackgroundResource(CommonUtils.getInstance(mContext).getTaskStatus(status));
		holder.checkBox.setChecked(false);
		holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					LogPrint.Debug("===== delete the item task = position = " + position);
					if(position < 0 || position >= mData.size()){
						LogPrint.Debug("===== out of position = " + position);
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
        public RelativeLayout rlayout;
		public CheckBox checkBox;
		public TextView title;
        public TextView titleCenter;
		public TextView comment;
		public TextView startTime;
		public TextView endTime;
        public TextView centerTime;
	}
	
}