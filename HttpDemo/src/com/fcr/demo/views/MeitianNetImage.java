package com.fcr.demo.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.android.volley.toolbox.NetworkImageView;
import com.fcr.demo.DemoApplication;

/**
 * 利用volley实现的加载url的网络图片
 * 
 * @author chaoranf@jumei.com
 * @date 2015-8-19
 */

public class MeitianNetImage extends NetworkImageView {

    public MeitianNetImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MeitianNetImage(Context context) {
        super(context);
    }

    public MeitianNetImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置网络图片的url
     * 
     * @param url
     */
    public void setImgUrl(String url) {
        if (!TextUtils.isEmpty(url) && url.startsWith("http")) {
            setImageUrl(url, DemoApplication.getInstance().getImageLoader());
        }
        return;
    }

    /**
     * 设置默认显示图片
     * 
     * @param resId
     */
    public void setDefaultImg(int resId) {
        setDefaultImageResId(resId);
    }

    /**
     * 设置获取网络图片失败的失败图
     * 
     * @param resId
     */
    public void setErrorImg(int resId) {
        setErrorImageResId(resId);
    }

}
