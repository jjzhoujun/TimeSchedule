package com.dreamfly.debuginfo;

import android.util.Log;

public class LogPrint {
	public static boolean debug = true;
	public static boolean warning = true;
	public static boolean fatal = true;

	public static void Debug(String msg) {
		if( debug ) {
			StackTraceElement line = new Throwable().getStackTrace()[1];
			if( line != null ) {
				Log.d("DEBUG_TIMESCHEDULE","["+line.getFileName() + ":" + line.getLineNumber() +"]: " + msg);
			}
		}
	}
	public static void Warning(String msg) {
		if( warning ) {
			StackTraceElement line = new Throwable().getStackTrace()[1];
			if( line != null ) {
				Log.w("WARNING_TIMESCHEDULE","["+line.getFileName() + ":" + line.getLineNumber() +"]: " + msg);
			}
		}
	}
	public static void Fatal(String msg) {
		if( fatal ) {
			StackTraceElement line = new Throwable().getStackTrace()[1];
			if( line != null ) {
				Log.e("FATAL_TIMESCHEDULE","["+line.getFileName() + ":" + line.getLineNumber() +"]: " + msg);
			}
		}
	}
}