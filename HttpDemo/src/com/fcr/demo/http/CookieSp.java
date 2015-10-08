package com.fcr.demo.http;

import java.util.ArrayList;
import java.util.List;

import com.fcr.demo.utils.Constants;
import com.fcr.demo.utils.DefaultTools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

/**
 * 该类保证cookie的本地存储和读取,sharedPreference方式
 * 
 * @author chaoranf@jumei.com
 * @CreateTime 2015-08-12
 */
public class CookieSp {
	public static final String TAG = "MeitianPreference";
	private Context mContext;
	private static CookieSp mInstance = null;
	private SharedPreferences mSharedPreferences;
	private String LOCAL_COOKIE = "local_cookie";
	private String USER_AGENT = "user_agent";
	private String HTTPHEAD = "fcr_http_head";

	private CookieSp(Context context) {
		mContext = context;
		mSharedPreferences = mContext.getSharedPreferences(HTTPHEAD,
				Context.MODE_PRIVATE);
	}

	public static synchronized CookieSp getInstance(Context context) {
		if (mInstance == null)
			mInstance = new CookieSp(context);
		return mInstance;
	}

	/**
	 * 把api返回的set-cookie存储到本地
	 * 
	 * @param cookies
	 * @return
	 */
	public boolean putSettedCookieToSp(List<MeitianCookie> cookies) {
		if (cookies == null || cookies.isEmpty()) {
			return false;
		}
		Editor editor = mSharedPreferences.edit();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < cookies.size(); i++) {
			sb.append(cookies.get(i).toString()).append(";");
			editor.putString(cookies.get(i).getName(), cookies.get(i)
					.getValue());
		}
		String havedCookies = mSharedPreferences.getString(LOCAL_COOKIE, "");
		if (TextUtils.isEmpty(havedCookies)) {
			editor.putString(LOCAL_COOKIE, sb.toString());
		} else {
			editor.putString(LOCAL_COOKIE, havedCookies + ";" + sb.toString());
		}
		editor.commit();
		return true;
	}

	public boolean putCookieToSp(MeitianCookie cookie) {
		if (cookie == null || TextUtils.isEmpty(cookie.getName())) {
			return false;
		}
		Editor editor = mSharedPreferences.edit();
		editor.putString(cookie.getName(), cookie.getValue());
		editor.commit();
		return true;
	}

	public boolean putValueToSp(String name, String value) {
		if (TextUtils.isEmpty(name)) {
			return false;
		}
		Editor editor = mSharedPreferences.edit();
		editor.putString(name, value);
		editor.commit();
		return true;
	}

	public String getCookieValue(String name) {
		if (TextUtils.isEmpty(name)) {
			return "";
		}
		return mSharedPreferences.getString(name, "");
	}

	/**
	 * 获取设置到本地的cookie列表
	 * 
	 * @return
	 */
	public List<MeitianCookie> getCookieListFromSp() {
		List<MeitianCookie> list = new ArrayList<MeitianCookie>();
		String cookieString = getSettedCookie();
		if (TextUtils.isEmpty(cookieString)) {
			return list;
		}
		String[] cookies = cookieString.split(";");
		for (String items : cookies) {
			String[] cookie = items.split("=");
			if (cookie.length > 1) {
				MeitianCookie _cookie = new MeitianCookie(cookie[0], cookie[1]);
				list.add(_cookie);
			}
		}
		return list;
	}

	/**
	 * 获取api设置到本地的cookie
	 * 
	 * @return
	 */
	private String getSettedCookie() {
		return mSharedPreferences.getString(LOCAL_COOKIE, "");
	}

	public void clearLocalCookie() {
		Editor editor = mSharedPreferences.edit();
		editor.putString(LOCAL_COOKIE, "");
		editor.commit();
	}

	public String getUserAgent() {
		String temp_r = mSharedPreferences.getString(USER_AGENT, "");
		if (TextUtils.isEmpty(temp_r)) {
			// "User-Agent",
			// "JuMei/3.230 (1107; Android; Android OS ; 4.4.4; zh) ApacheHttpClient/4.0"
			StringBuilder sb = new StringBuilder();
			sb.append("JuMei/");
			sb.append(Constants.CLIENT_V_VALUE);
			sb.append("(");
			sb.append(Constants.MODEL_VALUE + "; ");
			sb.append("Android" + "; ");
			sb.append("Android OS " + "; ");
			sb.append(DefaultTools.getSDKVersion(mContext) + "; ");
			sb.append("zh");
			sb.append(") ");
			sb.append("ApacheHttpClient/4.0");
			Editor editor = mSharedPreferences.edit();
			editor.putString(USER_AGENT, sb.toString());
			editor.commit();
		}
		return mSharedPreferences.getString(USER_AGENT, "");
	}

	public void putUserAgent(String userAgent) {
		Editor editor = mSharedPreferences.edit();
		editor.putString(USER_AGENT, userAgent);
		editor.commit();
	}

	// /**
	// * 设置服务器时间和本地时间的时间差，注意这个时间不准确，
	// * 这里只考虑了服务器开始返回时间并未考虑返回的传输时间
	// * @param timeDiff
	// */
	// private void putServerTimeDiff(long timeDiff){
	// Editor editor = mSharedPreferences.edit();
	// editor.putLong(SERVER_TIME_DIFF, timeDiff);
	// editor.commit();
	// }
	//
	// /**
	// * 返回服务器时间和本地时间的时间差，注意这个时间不准确
	// * 这里只考虑了服务器开始返回时间并未考虑返回的传输时间
	// * @return
	// */
	// private long getServerTimeDiff(){
	// return mSharedPreferences.getLong(SERVER_TIME_DIFF, 0);
	// }

	// /**
	// * 返回服务器时间和本地时间的时间，注意这个时间不准确
	// * 计算过程中只考虑了服务器开始返回时间并未考虑返回的传输时间
	// * %%%%%%%%%%%%%不要用来做秒杀%%%%%%%%%%%%%%%%%%%
	// * @return
	// */
	// public Date getServerDateTime(){
	// return new Date(System.currentTimeMillis() + getServerTimeDiff());
	// }

	// /**
	// * 手机发短信是否一致显示验证码
	// * @param isShow
	// */
	// public void storeSMSShowImgCode(boolean isShow){
	// Editor editor = mSharedPreferences.edit();
	// editor.putBoolean(SMS_SHOW_IMGCODE, isShow);
	// editor.commit();
	// }
	//
	// /**
	// * 手机发短信是否一致显示验证码Handler
	// */
	// public boolean isSMSShowImgCodeHandler(){
	// return mSharedPreferences.getBoolean(SMS_SHOW_IMGCODE_HANDLER, false);
	// }
	//
	// /**
	// * 手机发短信是否一致显示验证码Handler
	// * @param isShow
	// */
	// public void storeSMSShowImgCodeHandler(boolean isShow){
	// Editor editor = mSharedPreferences.edit();
	// editor.putBoolean(SMS_SHOW_IMGCODE_HANDLER, isShow);
	// editor.commit();
	// }
	//
	// /**
	// * 手机发短信是否一致显示验证码
	// */
	// public boolean isSMSShowImgCode(){
	// return mSharedPreferences.getBoolean(SMS_SHOW_IMGCODE, false);
	// }

}
