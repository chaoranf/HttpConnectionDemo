package com.fcr.demo.http;

/**
 * 通过这种类型进行fastJson的反序列化，需要在JSON.parseObject()中的class参数中写入
 * new TypeReference<HttpEntity_<T> >(){}
 * 其中T为传入的T
 * @author chaoranf@jumei.com
 * @CreateTime 2015-8-8
 * 
 * @param <B> 传入的类型参数
 */
public class MeitianHttpEntity<B> {
	public MeitianHttpHeader respHeader;
	public B response;
	@Override
	public String toString() {
		return "MeitianHttpEntity [respHeader=" + respHeader + ", response="
				+ response + "]";
	}
	
}
