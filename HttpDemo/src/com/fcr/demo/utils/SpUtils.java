package com.fcr.demo.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author chaoranf@jumei.com
 * @CreateTime 2015-08-12
 */
public class SpUtils {
    public static final String TAG = "HiloPreference";
    private Context mContext;
    private static SpUtils mInstance = null;
    private SharedPreferences mSharedPreferences;

    private SpUtils(Context context) {
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(TAG,
                Context.MODE_PRIVATE);
    }

    public static synchronized SpUtils getInstance(Context context) {
        if (mInstance == null)
            mInstance = new SpUtils(context);
        return mInstance;
    }

    public boolean putValueToSp(String name, String value) {
        if (TextUtils.isEmpty(name)) {
            return false;
        }
        Editor editor = mSharedPreferences.edit();
        editor.putString(name, value);
        editor.commit();
        return true;
    }

    public String getValueFromSp(String name) {
        if (TextUtils.isEmpty(name)) {
            return "";
        }
        return mSharedPreferences.getString(name, "");
    }

    public boolean putObject(String name, Object obj) {
        if (obj == null) {
            Editor e = mSharedPreferences.edit();
            e.putString(name, "");
            return e.commit();
        } else {
            String res = JSON.toJSONString(obj);
            Editor e = mSharedPreferences.edit();
            e.putString(name, res);
            boolean result = e.commit();
            return result;
        }
    }

    public Object getObject(String key) {
        String result = mSharedPreferences.getString(key, "");
        
        if(TextUtils.isEmpty(result)){
            return null;
        }
        Object obj = JSON.parseObject(result);
        return obj;
    }
    
    public Object getObject(String key, Class clazz) {
        String result = mSharedPreferences.getString(key, "");
        
        if(TextUtils.isEmpty(result)){
            return null;
        }
        Object obj = JSON.parseObject(result,clazz);
        return obj;
    }
    
}
