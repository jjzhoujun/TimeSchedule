package com.dreamfly.timeschedule.bo;

/**
 * Created by jayden on 1/3/16.
 */
public class TimeEntity extends Entity{

	private String month;
	private String day;
	private String hour;
	private String minute;

	public TimeEntity() {

	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getMonth() {
		return month;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getDay() {
		return day;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getHour() {
		return hour;
	}

	public void setMinute(String minute) {
		this.minute = minute;
	}

	public String getMinute() {
		return minute;
	}

	@Override
	public String toString() {
		return "month = " + month
				+ "; day = " + day
				+ "; hour = " + hour
				+ "; min = " + minute;
	}

}
