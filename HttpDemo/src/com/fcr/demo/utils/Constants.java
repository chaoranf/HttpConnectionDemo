package com.fcr.demo.utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    /** 极光代码推送开关 */
    public static final boolean JPushCodeSwitch = true;


    // 请求cookie 开始**********************************

    public static final Map<String, String> mCookieMap = new HashMap<String, String>();

    /** 客户端软件版本号 */
    public static final String CLIENT_V = "client_v";
    public static final String CLIENT_V_VALUE = "1.00";
    /** 手机型号 */
    public static final String MODEL = "model";
    public static final String MODEL_VALUE = android.os.Build.MODEL;
    /** 安装渠道 */
    public static final String SETUP_SOURCE = "setup_source";
    public static String SETUP_SOURCE_VALUE = "";
    /** 密钥 每个版本都有一个注意改 */
    public static final String APPSECRET = "appsecret";
    public static final String APPSECRET_VALUE = "114ab1fa";
    /** appid,例如com.jm.android.jumei，可以传包名 */
    public static final String APPID = "appid";
    /** mac */
    public static final String MAC = "mac";
    /** 用户手机屏幕分辨率 */
    public static final String RESOLUTION = "resolution";

    /** 网络类型 ,每次请求请求重新判断 */
    public static final String NETWORK = "network";
    /** App第一次安装,暂时注掉，没有用 */
    // public static final String APPFIRSTINSTALL = "appfirstinstall";
    /** 是否第一次打开：0 或 1 */
    public static final String IS_FIRST_OPEN = "is_first_open";
    public static String IS_FIRST_OPEN_VALUE = "0";
    /** android版本，如4.0.3 */
    public static final String PLATFORM_V = "platform_v";
    /** 平台，例如android还是ios */
    public static final String PLATFORM = "platform";
    public static final String PLATFORM_VALUE = "android";

    public static final String IMEI = "imei";
    public static final String SMSCENTER = "smscenter";
    public static final String OPERATOR = "operator";
    public static final String SOURCE = "source";

    public static final String LANGUAGE = "language";
    public static final String IMSI = "imsi";

    /** 登陆返回的 */

    /** tk */
    public static final String TK = "tk";
    /** 用户 hash 串 */
    public static final String ACCOUNT = "account";

    // 请求cookie 结束**********************************
    
    public static final String HOST_RD = "http://hilo.qa.jumei.com/";
    public static final String HOST_ONLINE = "http://hilo.jumei.com/";
    public static String HOST = HOST_ONLINE;
    /**
     * 是否为线上版本
     */
    public static boolean isOnLine = true;

    // 用户信息相关
    public static final String URL_USERINFO_API = "UserApi";

    public static final String PATH_YANZHENGMA ="/sendSmsCode";

}
