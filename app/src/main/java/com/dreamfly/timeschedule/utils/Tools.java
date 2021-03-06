package com.dreamfly.timeschedule.utils;

import com.dreamfly.debuginfo.LogPrint;
import com.dreamfly.timeschedule.R;
import com.dreamfly.timeschedule.bo.ConstantVar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jayden on 1/3/16.
 */
public class Tools {

	public static String getCurTimeStr() {
		long curTimeMills = System.currentTimeMillis();
		// 暂时写死？ 不考虑多语言， 如果需要考虑的话， 可以用getString代替，但是需要传context.
		SimpleDateFormat sDateFormat = new SimpleDateFormat("MM月dd日 HH:mm", Locale.getDefault());
		String date = sDateFormat.format(new Date(curTimeMills));
		return date;
	}

	/**
	 * 临时做法，strTime的格式必须是 “MM月dd日 HH:mm” 的，不然截取出来的不对
	 * */
	public static String getMonth(String strTime) {
		String month = strTime.substring(0, 2);
		LogPrint.Debug("==>> month = " + month);
		return month;
	}

	/**
	 * 临时做法，strTime的格式必须是 “MM月dd日 HH:mm” 的，不然截取出来的不对
	 * */
	public static String getDay(String strTime) {
		String day = strTime.substring(3, 5);
		LogPrint.Debug("==> day = " + day);
		return day;
	}

	/**
	 * 临时做法，strTime的格式必须是 “MM月dd日 HH:mm” 的，不然截取出来的不对
	 * */
	public static String getHour(String strTime) {
		String hour = strTime.substring(7, 9);
		LogPrint.Debug("==>> hour = " + hour);
		return hour;
	}

	/**
	 * 临时做法，strTime的格式必须是 “MM月dd日 HH:mm” 的，不然截取出来的不对
	 * */
	public static String getMin(String strTime) {
		String minute = strTime.substring(10);
		LogPrint.Debug("==>> minute = " + minute);
		return minute;
	}

	/**
	 * 获取对应的任务的优先级别.
	 * */
	public static int getTaskStatus(final int value) {
		switch (value) {
			// 备注: 这里面的pressed的颜色效果, 跟我设置的不一样,默认都变成系统的press颜色了. why ?
			case ConstantVar.STATUS_FIRST_LEVEL:
				return R.drawable.selector_first_level;
			case ConstantVar.STATUS_SECOND_LEVEL:
				return R.drawable.selector_second_level;
			case ConstantVar.STATUS_THIRD_LEVEL:
				return R.drawable.selector_third_level;
			case ConstantVar.STATUS_FOURTH_LEVEL:
				return R.drawable.selector_fourth_level;
			default:
				break;
		}
		return R.drawable.selector_first_level;
	}
}
