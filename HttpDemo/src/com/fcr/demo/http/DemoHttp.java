package com.fcr.demo.http;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

/**
 * 所有请求的业务中间层，如果多个页面存在同一请求时，代码只会有一份
 * 在需要做统计的时候，这里可以作为一个很好的统计入口
 * 
 * @author chaoranf
 * 
 */
public class DemoHttp {

    private static final Context mContext = null;

    /**
     * 设置关注关系
     * 
     * @param guanzhu
     *            是否关注，加关注传true
     * @param id
     *            被关注或被取消关注的uid
     */
    public static void setGuanzhu(boolean guanzhu, String id) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("uid", id);
        if (guanzhu) {
            params.put("relation", "cancel");
        } else {
            params.put("relation", "follow");
        }
//        MeitianHttp.httpPost(mContext, Constants.URL_USERINFO,
//                Constants.PATH_GUANZHU, params, null, null);
    }

}
