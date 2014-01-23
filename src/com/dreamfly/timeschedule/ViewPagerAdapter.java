/**
 * ViewPager Adapter， Bind data and view
 * */
package com.dreamfly.timeschedule;
import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ViewPagerAdapter extends PagerAdapter{

	private ArrayList<View> views;
	
	public ViewPagerAdapter(ArrayList<View> views){
		this.views = views;
	}
	
	/**
	 * 获得当前界面数
	 * */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(views != null){
			return views.size();
		}
		return 0;
	}

	/**
	 * 判断是否由对象生成界面
	 * */
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return (arg0 == arg1);
	}
	
	/**
	 * 初始化position位置的界面
	 * */
	@Override
	public Object instantiateItem(View view, int position){
		((ViewPager) view).addView(views.get(position), 0);
		return views.get(position);
	}
	
	/**
	 * 销毁position位置的界面
	 * */
	@Override
	public void destroyItem(View view, int position, Object arg2){
		((ViewPager) view).removeView(views.get(position));
		
	}
	
}
