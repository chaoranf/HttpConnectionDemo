/**
 * 
 */
package com.fcr.demo.http;

import org.apache.http.impl.cookie.BasicClientCookie;

/**
 * 实例化api返回cookie
 * @author chaoranf@jumei.com
 * @date 2015-8-13
 */
public class MeitianCookie extends BasicClientCookie {
    /**
	 * @param name
	 * @param value
	 */
	public MeitianCookie(String name, String value) {
		super(name, value);
	}

	@Override
	public String toString() {
		return getName() + "="+ getValue() ;
	}
	
//	@Override
//    public String toString() {
//        final StringBuilder buffer = new StringBuilder();
//        buffer.append(this.getName()+"="+this.getValue());
//        buffer.append(";");
//        buffer.append("domain="+this.getDomain());
//        buffer.append(";");
//        buffer.append("path="+this.getPath());
//        buffer.append(";");
//        buffer.append("expiry=");
//        try{
//			if (this.getExpiryDate() != null)
//				buffer.append(MeitianHeaderParser.cookieDateFormater.format(this
//						.getExpiryDate()));
//			else
//				buffer.append("");
//        }catch(Exception e){
//        	buffer.append("");
//        }
//        buffer.append(";");
//        buffer.append("version="+Integer.toString(this.getVersion()));
//        
//        return buffer.toString();
//    }
}
