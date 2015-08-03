package com.example.testhttp;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.testhttp.entity.HttpEntity;
import com.example.testhttp.entity.HttpEntity_;
import com.example.testhttp.entity.WeatherInfo;
import com.example.testhttp.httputil.HttpUtil;
import com.example.testhttp.interfaceimp.RequestCallBack;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;


public class MainActivity extends Activity {
	WebView mWebView;
	WeatherInfo mWeatherInfo;
	HttpEntity mHttpEntity;
	HttpEntity_<WeatherInfo> mHttpEntity_ = new HttpEntity_<WeatherInfo>();
	RequestCallBack mCallBack = new RequestCallBack() {
		
		@Override
		public void onSuccess(String data) {
			//需要增加异常捕捉，不让app崩溃;注、异常不捕捉，就会导致app崩溃
//			①解析方式
//			mHttpEntity = JSON.parseObject(data, HttpEntity.class);
//			mWeatherInfo = JSON.parseObject(mHttpEntity.retData, WeatherInfo.class);
//			Log.i("testff", mWeatherInfo.toString());
			//②解析方式
			mHttpEntity_ = JSON.parseObject(data, new TypeReference<HttpEntity_<WeatherInfo> >(){});
			Log.i("testff", mHttpEntity_.toString());
			reRefresh();
		}
		
		@Override
		public String onFail(String code) {
			return code;
		}
		
		@Override
		public String onError(String code) {
			return code;
		}
	};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.webview);
        new Thread(){
        	public void run() {
        		
        		String url ="http://apis.baidu.com/apistore";
        		String path ="/weatherservice/weather";
        		Map<String,String> param = new HashMap<String,String>();
        		param.put("citypinyin", "beijing");
//        		HttpUtil.request("http://apis.baidu.com/apistore/weatherservice/weather?citypinyin=beijing", "", param,mCallBack);
        		HttpUtil.httpGet(url, path, param, mCallBack);
        	}
        }.start();
    }
    
    public void reRefresh(){
    }

}