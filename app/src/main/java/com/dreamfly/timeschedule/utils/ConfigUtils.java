package com.dreamfly.timeschedule.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 配置工具类
 * @author jayden
 * @date 2017-01-01
 */

public class ConfigUtils {

	private static final String CONFIG_NAME="config_list";
	private static final String CONFIG_FIRST_RUN = "config_first_run";
	private static final String CONFIG_VERSION_CODE = "config_version_code";

	private Context mContext;
	private SharedPreferences mSharedPreferences;

	public ConfigUtils(Context context) {
		mContext = context;
		mSharedPreferences = context.getSharedPreferences(CONFIG_NAME, Context.MODE_PRIVATE);
	}

	public boolean isAppFirstRun() {
		boolean result = mSharedPreferences.getBoolean(CONFIG_FIRST_RUN, true);
		return result;
	}

	public void setAppFirstRun(boolean firstRun) {
		Editor editor = mSharedPreferences.edit();
		editor.putBoolean(CONFIG_FIRST_RUN, firstRun);
		editor.commit();
	}

	public int getCurVersionCode() {
		int curVer = mSharedPreferences.getInt(CONFIG_VERSION_CODE, 1);
		return curVer;
	}

	public void setCurVersionCode(int versionCode) {
		Editor editor = mSharedPreferences.edit();
		editor.putInt(CONFIG_VERSION_CODE, versionCode);
		editor.commit();
	}
}
