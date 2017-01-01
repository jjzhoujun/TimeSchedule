package com.dreamfly.timeschedule.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamfly.debuginfo.LogPrint;
import com.dreamfly.timeschedule.R;
import com.dreamfly.timeschedule.bo.Entity;
import com.dreamfly.timeschedule.bo.TimeItemEntity;
import com.dreamfly.timeschedule.listener.RecItemClickListener;
import com.dreamfly.timeschedule.utils.CommonUtils;
import com.dreamfly.timeschedule.utils.Tools;

import java.util.List;

/**
 * Created by Jayden on 2016-01-03
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<Entity> datas;
    private RecItemClickListener mItemClickListener;

    public RecyclerViewAdapter(List<Entity> datas){
        this.datas = datas;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        RecyclerViewHolder holder = new RecyclerViewHolder(view, mItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        LogPrint.Debug("==>> position = " + position);

        TimeItemEntity bo = (TimeItemEntity) datas.get(position);
        int status = bo.getI_status();
        String title = bo.getS_titile();
        String comment = bo.getS_notice();
        String sTime = bo.getS_start_time();
        String eTime = bo.getS_end_time();

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
        if(eTime != null){
            String[] strArray = eTime.split(" ");
            if(strArray.length > 1) {
                String monthDay = strArray[0];
                String hourMin = strArray[1];
                holder.monthDay.setText(monthDay);
                holder.hourMin.setText(hourMin);
            }
        }
        holder.rlayout.setBackgroundResource(Tools.getTaskStatus(status));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setOnItemClickListener(RecItemClickListener listener) {
        this.mItemClickListener = listener;
    }

}