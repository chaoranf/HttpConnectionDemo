package com.feng.testhttp.interfaceimp;

/**
 * 需要加上数据返回的自动判断，是parse的object，还是array
 * 需要进行上层封装
 * @author chaoranf 2015-8-3
 */
public interface RequestCallBack {
	
	public void onSuccess(String data);
	public String onFail(String code);
	public String onError(String code);

}
