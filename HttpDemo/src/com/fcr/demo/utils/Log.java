package com.fcr.demo.utils;

public class Log {
	public static boolean isDebug = true;
	private static final String TAG = "MeitianHttp";

	// 下面四个是默认tag的函数
	public static void i(String msg) {
		if (isDebug)
			android.util.Log.i(TAG, msg);
	}

	public static void d(String msg) {
		if (isDebug)
			android.util.Log.d(TAG, msg);
	}

	public static void e(String msg) {
		if (isDebug)
			android.util.Log.e(TAG, msg);
	}

	public static void v(String msg) {
		if (isDebug)
			android.util.Log.v(TAG, msg);
	}

	// 下面是传入自定义tag的函数
	public static void i(String tag, String msg) {
		if (isDebug)
			android.util.Log.i(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (isDebug)
			android.util.Log.d(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (isDebug)
			android.util.Log.e(tag, msg);
	}

	public static void v(String tag, String msg) {
		if (isDebug)
			android.util.Log.v(tag, msg);
	}
	public static void w(String tag, String msg) {
		if (isDebug)
			android.util.Log.w(tag, msg);
	}

}
