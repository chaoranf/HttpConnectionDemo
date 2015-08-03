package com.feng.testhttp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.testhttp.R;
import com.feng.testhttp.entity.HttpEntity;
import com.feng.testhttp.entity.HttpEntity_;
import com.feng.testhttp.entity.TestBigJson;
import com.feng.testhttp.entity.WeatherInfo;
import com.feng.testhttp.httputil.HttpUtil;
import com.feng.testhttp.interfaceimp.RequestCallBack;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	TextView mContentTV;
//	WeatherInfo mWeatherInfo;
	HttpEntity mHttpEntity;
	HttpEntity_<WeatherInfo> mHttpEntity_ ;
//	HttpEntity_<List<WeatherInfo>> mHttpEntityArray ;
	HttpEntity_<List<TestBigJson>> mHttpEntityArray ;
	
	RequestCallBack mCallBackBib = new RequestCallBack() {
		
		@Override
		public void onSuccess(String data) {
//			mHttpEntityArray = JSON.parseObject(data, new TypeReference<HttpEntity_<List<WeatherInfo>>>(){});
			mHttpEntityArray = JSON.parseObject(data, new TypeReference<HttpEntity_<List<TestBigJson>>>(){});
			for (int i = 0; i < mHttpEntityArray.retData.size(); i++) {
				Log.i("testff", mHttpEntityArray.retData.get(i).toString());
			}
			reRefresh();
		}
		
		@Override
		public String onFail(String code) {
			return null;
		}
		
		@Override
		public String onError(String code) {
			return null;
		}
	};
	
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
        mContentTV = (TextView) findViewById(R.id.data_content);
//        parseJson();
        parseBigJson();
    }
    
    /**
     * 刷新页面
     */
    public void reRefresh(){
    	runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				mContentTV.setText(mHttpEntityArray.retData.get(0).pinyin+ "_____"+mHttpEntityArray.retData.size());
			}
		});
    }
    
    public void parseBigJson(){
        new Thread(){
        	public void run() {
        		String url ="http://apis.baidu.com/apistore";
        		String path ="/weatherservice/weather";
        		Map<String,String> param = new HashMap<String,String>();
        		param.put("citypinyin", "beijing");
        		HttpUtil.httpGet(url, path, param, mCallBackBib);
        	}
        }.start();
    }
    
    public void parseJson(){
    	new Thread(){
        	public void run() {
        		String url ="http://apis.baidu.com/apistore";
        		String path ="/weatherservice/weather";
        		Map<String,String> param = new HashMap<String,String>();
        		param.put("citypinyin", "beijing");
        		HttpUtil.httpGet(url, path, param, mCallBack);
        	}
        }.start();
    }

}