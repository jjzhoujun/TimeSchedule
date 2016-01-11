package com.dreamfly.timeschedule.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dreamfly.debuginfo.LogPrint;
import com.dreamfly.timeschedule.R;
import com.umeng.socialize.ShareAction;

/**
 * Created by jayden on 1/8/16.
 */
public class LeftFragment extends BaseFragment{

    Button mBtnSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_profile, container, false);
        mBtnSubmit = (Button) view.findViewById(R.id.icon_submit);
        initListener();
        return view;
    }

    private void initListener() {
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogPrint.Debug("==>>> Press Submit in Fragment....");

            }
        });
    }
}
