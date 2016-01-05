package com.dreamfly.timeschedule.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dreamfly.timeschedule.R;

/**
 * Created by Jayden on 2016-01-03
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public RelativeLayout rlayout;
    public TextView title;
    public TextView titleCenter;
    public TextView comment;
    public TextView monthDay;
    public TextView hourMin;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        rlayout = (RelativeLayout) itemView.findViewById(R.id.ly_main_item);
        title = (TextView) itemView.findViewById(R.id.title);
        titleCenter = (TextView) itemView.findViewById(R.id.title_center);
        comment = (TextView) itemView.findViewById(R.id.comment);
        monthDay = (TextView) itemView.findViewById(R.id.month_day);
        hourMin = (TextView) itemView.findViewById(R.id.hour_min);
    }
}
