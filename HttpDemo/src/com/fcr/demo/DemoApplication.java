package com.fcr.demo;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.fcr.demo.http.MeitianBitmapCachePool;
import com.fcr.demo.http.MeitianHurlStack;
import com.fcr.demo.http.MeitianX509TrustManager;
import com.fcr.demo.utils.Constants;
import com.fcr.demo.utils.DefaultTools;
import com.fcr.demo.utils.PackageUtils;

import android.app.Application;
import android.text.TextUtils;

/**
 * 初始化工作
 * 
 * @author chaoranf
 * @CreateTime 2015-10-08
 * 
 */
public class DemoApplication extends Application {

	private static final String TAG = "fcrDemo";

	private static DemoApplication instance;

	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		initMobileInfo();
		MeitianX509TrustManager.allowAllSSL();
	}

	public static DemoApplication getInstance() {
		return instance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley
					.newRequestQueue(this, new MeitianHurlStack());
		}

		return mRequestQueue;
	}

	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue,
					new MeitianBitmapCachePool());
		}
		return this.mImageLoader;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

	public void initMobileInfo() {
		Constants.mCookieMap.put(Constants.CLIENT_V, Constants.CLIENT_V_VALUE);
		Constants.mCookieMap.put(Constants.MODEL, Constants.MODEL_VALUE);
		Constants.mCookieMap.put(Constants.SETUP_SOURCE, "");
		Constants.mCookieMap
				.put(Constants.APPSECRET, Constants.APPSECRET_VALUE);
		Constants.mCookieMap.put(Constants.APPID,
				PackageUtils.getPackageName(getInstance()));
		Constants.mCookieMap.put(Constants.MAC,
				DefaultTools.getMacAddress(getInstance()));
		Constants.mCookieMap.put(Constants.RESOLUTION,
				DefaultTools.getResolution(getInstance()));
		Constants.mCookieMap.put(Constants.NETWORK, DefaultTools
				.getAccessNetworkType(getInstance()).equals("wifi") ? "wifi"
				: "");
		Constants.mCookieMap.put(Constants.PLATFORM_V,
				DefaultTools.getSDKVersion(getInstance()));
		Constants.mCookieMap.put(Constants.PLATFORM, "android");
		Constants.mCookieMap.put(Constants.IMEI,
				DefaultTools.getImeiInfo(getInstance()));
		Constants.mCookieMap.put(Constants.SMSCENTER,
				DefaultTools.getServiceCenterAddress());
		Constants.mCookieMap.put(Constants.SOURCE, "");
		Constants.mCookieMap.put(Constants.LANGUAGE, "zh");
		Constants.mCookieMap.put(Constants.IMSI,
				DefaultTools.getImeiInfo(getInstance()));
	}
}
