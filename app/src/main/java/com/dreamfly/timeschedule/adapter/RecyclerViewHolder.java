package com.dreamfly.timeschedule.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dreamfly.timeschedule.R;
import com.dreamfly.timeschedule.listener.RecItemClickListener;

/**
 * Created by Jayden on 2016-01-03
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public RelativeLayout rlayout;
    public TextView title;
    public TextView titleCenter;
    public TextView comment;
    public TextView monthDay;
    public TextView hourMin;
    private RecItemClickListener mItemClickListener;

    public RecyclerViewHolder(View itemView, RecItemClickListener itemClickListener) {
        super(itemView);
        rlayout = (RelativeLayout) itemView.findViewById(R.id.ly_main_item);
        title = (TextView) itemView.findViewById(R.id.title);
        titleCenter = (TextView) itemView.findViewById(R.id.title_center);
        comment = (TextView) itemView.findViewById(R.id.comment);
        monthDay = (TextView) itemView.findViewById(R.id.month_day);
        hourMin = (TextView) itemView.findViewById(R.id.hour_min);

        this.mItemClickListener = itemClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(mItemClickListener != null) {
            mItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
