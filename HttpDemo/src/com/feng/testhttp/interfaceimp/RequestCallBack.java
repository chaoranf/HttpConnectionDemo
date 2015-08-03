package com.feng.testhttp.interfaceimp;

public interface RequestCallBack {
	
	public void onSuccess(String data);
	public String onFail(String code);
	public String onError(String code);

}
