package com.dreamfly.timeschedule.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dreamfly.timeschedule.R;

/**
 * Created by jayden on 1/8/16.
 */
public class LeftFragment extends BaseFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_profile, container, false);
        return view;
    }
}
