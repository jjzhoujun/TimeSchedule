package com.dreamfly.timeschedule.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dreamfly.debuginfo.LogPrint;
import com.dreamfly.timeschedule.InitConfiguration;
import com.dreamfly.timeschedule.R;
import com.dreamfly.timeschedule.ad.AdManager;
import com.dreamfly.timeschedule.adapter.ViewPagerAdapter;
import com.dreamfly.timeschedule.interfaces.AdViewSpreadListener;
import com.dreamfly.timeschedule.manager.AdViewSpreadManager;
import com.dreamfly.timeschedule.utils.ApplicationVersionUtils;
import com.dreamfly.timeschedule.utils.ApplicationVersionUtils.ClientVersionInfo;
import com.dreamfly.timeschedule.utils.ConfigUtils;

import java.io.InputStream;
import java.util.ArrayList;

public class SplashActivity extends Activity implements OnClickListener, OnPageChangeListener, AdViewSpreadListener {
	private static final String TAG = SplashActivity.class.getSimpleName();
//	private static final int[] mPics = {R.drawable.introduce_1,
//										R.drawable.introduce_2,
//										R.drawable.introduce_3,
//										R.drawable.introduce_4};
	private static final int[] mPics = {R.drawable.sp_0001,
										R.drawable.sp_0002,
										R.drawable.sp_0003};
	private static final int TIMEOUT_CHECK_AD = 3000;
	private static final int TIMEOUT_SHOW_AD = 3000;
	private static final int MSG_SHOW_SPLASH_AD = 0;
	private static final int MSG_SHOW_MAIN = 1;
	private ImageView[] mPoints;
	private ViewPager mViewPager;
	private ViewPagerAdapter mVpAdapter;
	private Button mBtnEnter;
	private ArrayList<View> mViews;
	private int mCount = 0;
	private int mCurrIndex;
	private AdManager mAdManager;
	public static InitConfiguration initConfiguration;
	private static final String mKey1 = "SDK20171917070119c16d0pryy8h4q9y";
	private static String[] mKeySet = new String[]{mKey1};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ClientVersionInfo clientVersionInfo = ApplicationVersionUtils
				.getApplicationVersionInfo(this);
		int versionCode = clientVersionInfo.getVersionCode();
		ConfigUtils configUtils = new ConfigUtils(this);
		int oldVersionCode = configUtils.getCurVersionCode();
		mAdManager = new AdManager(this);
		if(configUtils.isAppFirstRun() || oldVersionCode != versionCode) {
			doFirstEnter();
			configUtils.setAppFirstRun(false);
			configUtils.setCurVersionCode(versionCode);
		} else {
			doNotFirstEnter();
//			startMainListActivity();
			loadAd();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "tstest===>>> onResume, canJump = " + canJumpImmediately);
		if (canJumpImmediately) {
			jumpWhenCanClick();
		}
		canJumpImmediately = true;
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "tstest===>>> onPause, canJump = " + canJumpImmediately);
		canJumpImmediately = true;
	}

	private void doFirstEnter() {
		setContentView(R.layout.guide);
		initView();
		initData();
	}

	private void doNotFirstEnter() {
		setContentView(R.layout.splash);
	}

	public void initView(){
		mViews = new ArrayList<View>();
		mViewPager = (ViewPager)findViewById(R.id.viewpager);
		mVpAdapter = new ViewPagerAdapter(mViews);
		mBtnEnter = (Button) findViewById(R.id.btn_enter);
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

		mBtnEnter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startMainListActivity();
			}
		});
		
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
		//intent.setClass(SplashActivity.this, UIMainListActivity.class);
		intent.setClass(SplashActivity.this, UIMainRecyActivity.class);
		startActivity(intent);
		finish();
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
		if(arg0 == mPics.length - 1) {
			mBtnEnter.setVisibility(View.VISIBLE);
		} else {
			mBtnEnter.setVisibility(View.GONE);
		}
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

	private final Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			int what = msg.what;
			switch (what) {
				case MSG_SHOW_SPLASH_AD:
					showAd();
					break;
				case MSG_SHOW_MAIN:
					startMainListActivity();
					break;
				default:
					break;
			}
		}
	};

	private void loadAd() {
//		mHandler.sendEmptyMessageDelayed(MSG_SHOW_MAIN, TIMEOUT_CHECK_AD);
		initConfiguration = InitConfiguration.createDefault(this);
		AdViewSpreadManager.getInstance(this).init(initConfiguration, mKeySet);
		AdViewSpreadManager.getInstance(this).request(this, mKey1,
				(RelativeLayout) findViewById(R.id.adsRl), this);
	}

	private void showAd() {

	}

	/**
	 * 当设置开屏可点击时，需要等待跳转页面关闭后，再切换至您的主窗口。故此时需要增加canJumpImmediately判断。 另外，点击开屏还需要在onResume中调用jumpWhenCanClick接口。
	 */
	public boolean canJumpImmediately = false;

	private void jumpWhenCanClick() {
		Log.d("test", "this.hasWindowFocus():" + this.hasWindowFocus() + "canjump = " + canJumpImmediately);
		if (canJumpImmediately) {
			startMainListActivity();
		} else {
			canJumpImmediately = true;
		}
	}

	private void jump() {
		startMainListActivity();
	}


	@Override
	public void onAdDisplay(String s) {
		Log.d(TAG, "tstest==>>  onAdDisplay, s = " + s);
	}

	@Override
	public void onAdClose(String s) {
		Log.d(TAG, "tstest==>>  onAdClose, s = " + s);
		jump();
	}

	@Override
	public void onAdRecieved(String s) {
		Log.d(TAG, "tstest==>>  onAdRecieved, s = " + s);
	}

	@Override
	public void onAdClick(String s) {
		Log.d(TAG, "tstest==>>  onAdClick, s = " + s);
		canJumpImmediately = false;
		jumpWhenCanClick();
	}

	@Override
	public void onAdFailed(String s) {
		Log.d(TAG, "tstest==>>  onAdFailed, s = " + s);
		jump();
	}

	@Override
	public void onAdSpreadNotifyCallback(String s, ViewGroup viewGroup, int i, int i1) {

	}
}
