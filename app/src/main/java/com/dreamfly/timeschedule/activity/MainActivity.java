package com.dreamfly.timeschedule.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.dreamfly.debuginfo.LogPrint;
import com.dreamfly.timeschedule.R;
import com.dreamfly.timeschedule.adapter.ViewPagerAdapter;
import com.umeng.analytics.MobclickAgent;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends Activity implements OnClickListener, OnPageChangeListener{
	private static final String TAG = "ViewPagerTest";
	private static final int[] mPics = {R.drawable.introduce_1,
										R.drawable.introduce_2,
										R.drawable.introduce_3,
										R.drawable.introduce_4};
	private ImageView[] mPoints;
	private ViewPager mViewPager;
	private ViewPagerAdapter mVpAdapter;
	private ArrayList<View> mViews;
	private int mCount = 0;
	
	private int mCurrIndex;

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// Start UMeng device notice.
//		LogPrint.Debug("device = " + getDeviceInfo(this));
		MobclickAgent.setDebugMode(true);
		if(true) {
			startMainListActivity();
			finish();
		} else {
			initView();
			initData();
		}
	}

	public static String getDeviceInfo(Context context) {
		try{
			org.json.JSONObject json = new org.json.JSONObject();
			android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);

			String device_id = tm.getDeviceId();

			android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

			String mac = wifi.getConnectionInfo().getMacAddress();
			json.put("mac", mac);

			if( TextUtils.isEmpty(device_id) ){
				device_id = mac;
			}

			if( TextUtils.isEmpty(device_id) ){
				device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
			}

			json.put("device_id", device_id);

			return json.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


	public void initView(){
		mViews = new ArrayList<View>();
		mViewPager = (ViewPager)findViewById(R.id.viewpager);
		mVpAdapter = new ViewPagerAdapter(mViews);
	}
	
	public void initData(){
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		for(int i = 0; i < mPics.length; i++){
			ImageView imgView = new ImageView(this);
			imgView.setLayoutParams(params);
			imgView.setScaleType(ScaleType.FIT_XY);
			imgView.setImageResource(mPics[i]);
			mViews.add(imgView);
//			Bitmap bitmap = readBitMap(this, mPics[i]);
//			imgView.setImageBitmap(bitmap);
//			mViews.add(imgView);
			
		}
		// set data adapter
		mViewPager.setAdapter(mVpAdapter);
		mViewPager.setOnPageChangeListener(this);
		
		initPoint();
	}
	
	private void initPoint(){
		LinearLayout linearLayout = (LinearLayout)findViewById(R.id.ll_dot);
		mPoints = new ImageView[mPics.length];
		for(int i = 0; i < mPics.length; i++){
			mPoints[i] = (ImageView)linearLayout.getChildAt(i);
			mPoints[i].setEnabled(true);
			mPoints[i].setOnClickListener(this);
			mPoints[i].setTag(i);
		}
		mCurrIndex = 0;
		mPoints[mCurrIndex].setEnabled(false);
	}
	
	 /**
     * @param context
     * @param resId
     * @return
     */  
	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();  
		opt.inPreferredConfig = Bitmap.Config.RGB_565;   
		opt.inPurgeable = true;  
		opt.inInputShareable = true;  
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is,null,opt);  
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		LogPrint.Debug("===onPageScrollStateChanged====arg0 = " + arg0 + "; mCount = " + mCount);
		if(arg0 == 0 && mCount > 3){
			startMainListActivity();
			overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
			finish();
		} 
		mCount = 0;
		
	}

	private void startMainListActivity() {
		Intent intent = new Intent();
		//intent.setClass(MainActivity.this, UIMainListActivity.class);
		intent.setClass(MainActivity.this, UIMainRecyActivity.class);
		startActivity(intent);
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		if(arg0 == mPics.length - 1){
			mCount++;
		}
		LogPrint.Debug("====onPageScrolled===arg0 = " + arg0 + "; arg1 = " + arg1
				+ "; arg2 = " + arg2 + "; mPics.length = " + mPics.length + "; mCount = " + mCount);
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		LogPrint.Debug("====onPageSelected==== arg0 = " + arg0);
		setCurDot(arg0);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int position = (Integer)arg0.getTag();
		LogPrint.Debug("====onClick ==== position = " + position);
		setCurView(position);
		setCurDot(position);
	}
	
	private void setCurView(int position){
		if(position < 0 || position >= mPics.length){
			return ;
		}
		LogPrint.Debug("====setCurView====");
		mViewPager.setCurrentItem(position);
	}
	
	private void setCurDot(int position){
		if(position < 0 || position >= mPics.length || position == mCurrIndex){
			return ;
		}
		LogPrint.Debug("======setCurDot======");
		mPoints[position].setEnabled(false);
		mPoints[mCurrIndex].setEnabled(true);
		mCurrIndex = position;
	}

}
