package com.dreamfly.debuginfo;

import android.util.Log;

public class LogPrint {
	private static boolean debug = false;
	private static boolean warning = false;
	private static boolean fatal = true;

	public static void Debug(String msg) {
		if( debug ) {
			StackTraceElement line = new Throwable().getStackTrace()[1];
			if( line != null ) {
				Log.d("Debug_TS","["+line.getFileName() + ":" + line.getLineNumber() +"]: " + msg);
			}
		}
	}
	public static void Warning(String msg) {
		if( warning ) {
			StackTraceElement line = new Throwable().getStackTrace()[1];
			if( line != null ) {
				Log.w("Warning_TS","["+line.getFileName() + ":" + line.getLineNumber() +"]: " + msg);
			}
		}
	}
	public static void Fatal(String msg) {
		if( fatal ) {
			StackTraceElement line = new Throwable().getStackTrace()[1];
			if( line != null ) {
				Log.e("Fatal_TS","["+line.getFileName() + ":" + line.getLineNumber() +"]: " + msg);
			}
		}
	}
}
