package com.dreamfly.timeschedule.ad;

import android.content.Context;

/**
 * 广告管理控制器
 * @author jayden
 * @date 2017-01-01
 */

public class AdManager {

	private static final String TAG = AdManager.class.getSimpleName();
	private final Context mContext;

	public AdManager(Context context) {
		mContext = context;
	}

	public boolean isReadyShowSplashAd() {
		return false;
	}

	public void downloadSplashAd() {

	}
}
