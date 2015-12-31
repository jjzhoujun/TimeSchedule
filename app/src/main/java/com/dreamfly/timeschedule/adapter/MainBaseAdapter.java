package com.dreamfly.timeschedule.adapter;


import java.util.ArrayList;
import java.util.List;

import com.dreamfly.debuginfo.LogPrint;
import com.dreamfly.timeschedule.R;
import com.dreamfly.timeschedule.bo.Entity;
import com.dreamfly.timeschedule.bo.TimeItemEntity;
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

        if(endTime == null || "".equals(endTime)) {
            holder.centerTime.setText(startTime);
            holder.centerTime.setVisibility(View.VISIBLE);
            holder.startTime.setVisibility(View.GONE);
            holder.endTime.setVisibility(View.GONE);
        } else {
            holder.startTime.setText(startTime);
            holder.endTime.setText(endTime);
			holder.startTime.setVisibility(View.VISIBLE);
			holder.endTime.setVisibility(View.VISIBLE);
			holder.centerTime.setVisibility(View.GONE);
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
						TimeItemEntity itemEntity = (TimeItemEntity)getItem(position);
						LogPrint.Debug("checked, del, id = " + itemEntity.getId() + "; title = " + itemEntity.getS_titile());
						CommonUtils.getInstance(mContext).delTimeStruct(itemEntity.getId());
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