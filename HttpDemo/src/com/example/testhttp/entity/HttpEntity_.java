package com.example.testhttp.entity;

/**
 * 通过这种类型进行fastJson的反序列化，需要在JSON.parseObject()中的class参数中写入
 * new TypeReference<HttpEntity_<T> >(){}
 * 其中T为传入的T
 * @author chaoranf
 * 
 * @param <T> 传入的类型参数
 */
public class HttpEntity_<T> {
	public String errNum;
	public String errMsg;
	public T retData;
	
	@Override
	public String toString() {
		return "HttpEntity_ [errNum=" + errNum + ", errMsg=" + errMsg
				+ ", retData=" + retData.toString() + "]";
	}
	
}
