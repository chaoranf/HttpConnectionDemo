package com.fcr.demo.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

/**
 * 处理Api返回的header
 * 
 * @author chaoranf@jumei.com
 * @date 2015-8-13
 */
public class MeitianHeaderParser {
	public static final String TAG = "MeitianCookie";

	/**
	 * 存储set-cookie到本地
	 * @param headers
	 */
	public static void storeCookie(Context context,Map<String, String> headers) {
		if(headers == null){
			return;
		}
		CookieSp.getInstance(context).putSettedCookieToSp(getCookieMap(headers));
	}

	private static List< MeitianCookie> getCookieMap(Map<String, String> headers) {
		List< MeitianCookie> list = new ArrayList<MeitianCookie>();
		String value = headers.get("Set-Cookie");
		if(TextUtils.isEmpty(value)){
			return list;
		}
		String [] setCookies= value.split("____");
		for (String item:setCookies) {
			try {
				list.add(parseRawCookie(item));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	private static MeitianCookie parseRawCookie(String rawCookie)
			throws Exception {
		if (TextUtils.isEmpty(rawCookie)) {
			return null;
		}

		String[] rawCookieParams = rawCookie.split(";");

		String[] rawCookieNameAndValue = rawCookieParams[0].split("=");
		if (rawCookieNameAndValue.length != 2) {
			Log.i("testff", "返回的set-cookie的键值对不合法"+rawCookie);
			throw new Exception("cookie的键值对不合法");
		}

		String cookieName = rawCookieNameAndValue[0].trim();
		String cookieValue = rawCookieNameAndValue[1].trim();
		MeitianCookie cookie = new MeitianCookie(cookieName, cookieValue);

		/**
		 * 暂时不需要这些属性------begin
		 */
//		for (int i = 1; i < rawCookieParams.length; i++) {
//			String rawCookieParamNameAndValue[] = rawCookieParams[i].trim()
//					.split("=");
//
//			String paramName = rawCookieParamNameAndValue[0].trim();
//
//			if (paramName.equalsIgnoreCase("secure")) {
//				cookie.setSecure(true);
//			} else {
//				if (rawCookieParamNameAndValue.length != 2) {
//					throw new Exception("cookie属性的键值对不合法");
//				}
//
//				String paramValue = rawCookieParamNameAndValue[1].trim();
//
//				if (paramName.equalsIgnoreCase("expires")) {
//					Date expiryDate = null;
//					try {
//						expiryDate = DateUtils.parseDate(paramValue);
//					} catch (DateParseException e) {
//
//					}
//					cookie.setExpiryDate(expiryDate);
//				} else if (paramName.equalsIgnoreCase("expiry")) {
//					Date expiryDate = null;
//					try {
//						expiryDate = cookieDateFormater.parse(paramValue);
//					} catch (Exception ex) {
//					}
//					cookie.setExpiryDate(expiryDate);
//				} else if (paramName.equalsIgnoreCase("domain")) {
//					if (paramValue.contains(SPECIAL_COOKIE_DOMAIN)) {
//						cookie.setDomain(REPLACE_COOKIE_DOMAIN);
//					} else {
//						cookie.setDomain(paramValue);
//					}
//				} else if (paramName.equalsIgnoreCase("path")) {
//					if (paramValue.contains(SPECIAL_COOKIE_DOMAIN)) {
//						cookie.setPath(REPLACE_COOKIE_PATH);
//					} else {
//						cookie.setPath(paramValue);
//					}
//				} else if (paramName.equalsIgnoreCase("comment")) {
//					// cookie.setPath(paramValue);
//				} else {
//					// JuMeiLogMng.getInstance().i(TAG,
//					// "method:parseRawCookie" + "未知Cookie参数" + paramName);
//				}
//			}
//		}
		/**
		 * 暂时不需要这些属性------end
		 */

		return cookie;
	}
}