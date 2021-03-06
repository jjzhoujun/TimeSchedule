package com.dreamfly.timeschedule.adapter;


import java.util.ArrayList;
import java.util.List;

import com.dreamfly.debuginfo.LogPrint;
import com.dreamfly.timeschedule.R;
import com.dreamfly.timeschedule.bo.Entity;
import com.dreamfly.timeschedule.bo.TimeItemEntity;
import com.dreamfly.timeschedule.utils.CommonUtils;
import com.dreamfly.timeschedule.utils.Tools;

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
	private List<Entity> mData;
	
	private LayoutInflater mInflater;
	
	public MainBaseAdapter(Context context){
		mContext = context;
		mData = new ArrayList<Entity>();
	}

	public void addItem(final Entity item){
		mData.add(item);
		notifyDataSetChanged();
	}

	public void updateItem(int position, Entity entity) {
		LogPrint.Debug("==>>updateItem position = " + position + "; entity = " + entity);
		mData.set(position, entity);
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
		ViewHolder holder = null;
		// Optimize the speed
		if(convertView == null){
			convertView = View.inflate(mContext, R.layout.item, null);
			holder = new ViewHolder();
            holder.rlayout = (RelativeLayout) convertView.findViewById(R.id.ly_main_item);
			holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.titleCenter = (TextView) convertView.findViewById(R.id.title_center);
			holder.comment = (TextView) convertView.findViewById(R.id.comment);
			holder.monthDay = (TextView) convertView.findViewById(R.id.month_day);
			holder.hourMin = (TextView) convertView.findViewById(R.id.hour_min);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}

		Entity entity = (Entity)this.getItem(position);
		TimeItemEntity bo = (TimeItemEntity) entity;
		String title = bo.getS_titile();
		String comment = bo.getS_notice();
		int status = bo.getI_status();
		String startTime = bo.getS_start_time();
		String endTime = bo.getS_end_time();
		boolean bAlarm = bo.getB_alarm();

		LogPrint.Debug("position = " + position + ";title = " + title + "; comment = " + comment + "; status = " + status + "; s_time = " + startTime + "; endTime = " + endTime + "; alarm = " + bAlarm);
        if(comment == null || "".equals(comment)) {
            holder.titleCenter.setText(title);
            holder.titleCenter.setVisibility(View.VISIBLE);
            holder.title.setVisibility(View.GONE);
            holder.comment.setVisibility(View.GONE);
        } else {
            holder.title.setText(title);
            holder.comment.setText(comment);
			holder.title.setVisibility(View.VISIBLE);
			holder.comment.setVisibility(View.VISIBLE);
			holder.titleCenter.setVisibility(View.GONE);
        }

		if(startTime != null){
			String[] strArray = startTime.split(" ");
			if(strArray.length > 1) {
				String monthDay = strArray[0];
				String hourMin = strArray[1];
				holder.monthDay.setText(monthDay);
				holder.hourMin.setText(hourMin);
			}
		}
        holder.rlayout.setBackgroundResource(Tools.getTaskStatus(status));
		return convertView;
	}
	
	public static class ViewHolder{
        public RelativeLayout rlayout;
		public TextView title;
        public TextView titleCenter;
		public TextView comment;
		public TextView monthDay;
		public TextView hourMin;
	}
	
}