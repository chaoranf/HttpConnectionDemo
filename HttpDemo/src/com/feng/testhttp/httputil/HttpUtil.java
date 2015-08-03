package com.feng.testhttp.httputil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import android.util.Log;

import com.feng.testhttp.interfaceimp.RequestCallBack;

/**
 * 网络基础通信
 * @author chaoranf 2015-7-31
 */
public class HttpUtil {
	
	private static String charSet = "UTF-8";
	private static String GET = "GET";
	private static String POST = "POST";
	private static int TIME_OUT_CON = 20000;
	private static int TIME_OUT_READ = 10000;
	
	
	/** 连接失败*/
	private static final int ERROR =10000;
	/** 请求连接不合法*/
	private static final int ERROR_URL =10001;
	/** 连接打开失败*/
	private static final int ERROR_IO =10002;
	/** 请求成功*/
	private static final int SUCCESS =20000;
	
	/** 请求失败*/
	private static final int FAIL = 30000;
	/** 请求失败,传参错误*/
	private static final int FAIL_PARAM_ERROR =30001;

	/**
	 * 
	 * @param url
	 * @param path
	 * @param param
	 * @param callback
	 * @return
	 */
	public static int httpGet(String url, String path,
			Map<String, String> param, RequestCallBack callback) {
		try {
			//拼接链接
			String requestUrl = andString(url+path,param);
			//创建连接
			URL httpUrl = new URL(requestUrl);
			//打开连接，也就是获得连接对象
			HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
			//设置连接参数
			
			//首先设置header，包括cookie等都在这里
			/** 常用的参数有，连接方式、超时连接时间、超时读取时间、cookie*/
			conn.setRequestMethod(GET);
			conn.setConnectTimeout(TIME_OUT_CON);
			conn.setReadTimeout(TIME_OUT_READ);
			conn.setRequestProperty("cookie", "nihao=1234");
			conn.addRequestProperty("cookie", "abc=123");
			conn.addRequestProperty("cookie", "aadsasd=123");
			/**setRequestProperty会覆盖已经存在的key的所有values，有清零重新赋值的作用。而addRequestProperty则是在原来key的基础上继续添加其他value。*/
			/** header里的信息都存在requestProperty*/
			//这里是不同应用特殊的头部参数,也是放在这个requestProperty中,本例测试的特殊参数
			conn.setRequestProperty("apikey","315ec2b823c85f76d98449369427068f");
			
			//上传文件的形式参数暂未考虑
			
			//进行连接操作
			conn.connect();
			
			//连接的细节已经被屏蔽掉了，这里不需要进行处理，直接做数据返回的处理
			
			//数据返回
			if(conn.getResponseCode() == 200){
				//200只是单纯与服务器联通，并不代表正确返回
				StringBuffer sb = new StringBuffer();
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				String str ="";
				while ((str = reader.readLine()) != null) {
					sb.append(str);
				}
				callback.onSuccess(sb.toString());
			}else{
				
			}
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return ERROR_URL;
		} catch (IOException e) {
			e.printStackTrace();
			return ERROR_IO;
		}
		return SUCCESS;
	}

	public static void request(String url, String path,
			Map<String, String> param, RequestCallBack callback) {

		try {
			URL httpUrl = new URL(url);

			HttpURLConnection conn = (HttpURLConnection) httpUrl
					.openConnection();

			// HttpsURLConnection conn1 = (HttpsURLConnection)
			// httpUrl.openConnection();

			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("apikey",
					"315ec2b823c85f76d98449369427068f");
			conn.connect();
			
			
			StringBuffer sb = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String str ="";
			while ((str = reader.readLine()) != null) {
				sb.append(str);
			}
//			callback.onSuccess(getEntityString(sb.toString()));
			callback.onSuccess(sb.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getEntityString(String param){
		if(param == null){
			return "";
		}
		String label = "\"retData\":";
		int index = param.indexOf(label);
		if(index!=-1){
			param = param.substring(index+label.length(),param.length()-1);
		}else{
			return "";
		}
		return param;
	}
	
	public static String andString(String url,Map<String,String> param){
		StringBuilder sb = new StringBuilder();
		//暂时不考虑sb的回收问题
		sb.append(url).append("?");
		for (Map.Entry<String, String> entry: param.entrySet()) {
				sb.append(entry).append("&");
		}
		String result = sb.toString();
		result = result.substring(0, result.length()-1);
		sb = null;
		return result;
	}

}
