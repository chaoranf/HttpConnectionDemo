package com.fcr.demo.http;

import java.util.Map;

/**
 * http请求的返回header的处理
 * @author chaoranf@jumei.com
 * @date 2015-8-13
 */
public interface ResponseHeaderHandler {
	public void handleCookie(Map<String, String> headers);
}
