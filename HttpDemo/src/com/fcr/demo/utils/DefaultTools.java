package com.fcr.demo.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.StatFs;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsMessage;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class DefaultTools {
    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * 获取sdk版本信息
     */
    public static String getSDKVersion(Context context) {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取运营商信息
     */
    public static String getCarrier(Context context) {
        TelephonyManager telephony = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = telephony.getSubscriberId();
        if (imsi != null && !"".equals(imsi)) {
            if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
                return "ChinaMobile";
            } else if (imsi.startsWith("46001")) {
                return "ChinaUnicom";
            } else if (imsi.startsWith("46003")) {
                return "ChinaTelecom";
            } else {
                return "othes";
            }
        }

        return null;
    }

    /** 获取imsi */
    public static String getImsiInfo(Context context) {
        TelephonyManager telephony = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = telephony.getSubscriberId();
        return imsi;
    }

    /**
     * 读取手机串号imei
     */
    public static String getImeiInfo(Context con) {
        TelephonyManager telephonyManager = (TelephonyManager) con
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    /**
     * MD5加密字符串
     */
    public static String md5(String source) {
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(source.getBytes());
            byte[] mess = digest.digest();
            return toHexString(mess);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return source;
    }

    public static String toHexString(byte[] b) { // byte to String
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[b[i] & 0x0f]);
        }
        return sb.toString();
    }

    /**
     * sha加密字符串
     */
    public static String sha(String source) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(source.getBytes());
            byte[] mess = digest.digest();
            return toHexString(mess);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return source;
    }

    /**
     * 获取路径中的文件名
     */
    public static String getFileNameFromURL(String url) {
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        return fileName;
    }

    /** 获取手机的服务中心号码 */
    @SuppressWarnings("deprecation")
    public static String getServiceCenterAddress() {
        String str = "";
        SmsMessage[] messages = new SmsMessage[1];
        SmsMessage message = messages[0];
        if (null != message) {
            str = message.getServiceCenterAddress();
            if (str == null || "".equals(str)) {
                str = "";
            }

        }
        return str;
    }

    /**
     * 保存文件到指定路径，后缀名修改为指定的后缀postfix
     */
    public static void saveImageToSDAndChangePostfix(Bitmap bitmap,
            String path, String fileName, String postfix) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        File imageFile = new File(path + name + postfix);
        FileOutputStream fileOutputStream = null;
        BufferedOutputStream bos = null;
        try {
            fileOutputStream = new FileOutputStream(imageFile);
            bos = new BufferedOutputStream(fileOutputStream);
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.flush();
                    bos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存文件到指定路径，后缀名不修改
     */
    public static void saveImageToSD(Bitmap bitmap, String path, String fileName) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File imageFile = new File(path + fileName);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(imageFile);
            BufferedOutputStream bos = new BufferedOutputStream(
                    fileOutputStream);
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                bos.flush();
                bos.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从asset中得到source id
     */
    public static String getSourceIdFromAsset(Context context) {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        ByteArrayOutputStream baos = null;
        try {
            inputStream = assetManager.open("sourid.txt");
            baos = new ByteArrayOutputStream();
            byte buf[] = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }

            return baos.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    /**
     * 判断网络是否可用̬
     */
    public static boolean isAccessNetwork(Context context) {
        boolean bExistNetwork = isAccessNetwork_hard(context);
        if (!bExistNetwork) {
            bExistNetwork = isMobileConnected(context);
        }
        return bExistNetwork;
    }

    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (null != mConnectivityManager) {
                NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (mWiFiNetworkInfo != null && mWiFiNetworkInfo.isAvailable()) {
                    return mWiFiNetworkInfo.isConnectedOrConnecting();
                }
            }
        }
        return false;
    }

    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (null != mConnectivityManager) {
                NetworkInfo mMobileNetworkInfo = mConnectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (mMobileNetworkInfo != null
                        && mMobileNetworkInfo.isAvailable()) {
                    return mMobileNetworkInfo.isConnectedOrConnecting();
                }
            }
        }
        return false;
    }

    /***
     * 宽松判断网络连接
     * 
     * @param context
     * @return
     */
    public static boolean isAccessNetwork_loose(Context context) {
        boolean bExistNetwork = false;

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivity.getAllNetworkInfo();

        for (int i = 0; i < networkInfos.length; i++) {
            if (null != networkInfos[i] && networkInfos[i].isAvailable()) {
                bExistNetwork = true;
                break;
            }
        }

        return bExistNetwork;
    }

    /***
     * 严格判断网连接
     * 
     * @param context
     * @return
     */
    public static boolean isAccessNetwork_hard(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connectivity.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isAvailable()
                && activeInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    /**
     * 获取网络类型信息
     */
    public static String getAccessNetworkType(Context context) {

        if (null == context) {
            return "";
        }

        int type = 0;
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        NetworkInfo info = connManager.getActiveNetworkInfo();
        if (info == null || telephonyManager == null) {
            return "";
        }
        type = info.getType();
        if (type == ConnectivityManager.TYPE_WIFI) {
            return "wifi";
        }
        if (type == ConnectivityManager.TYPE_MOBILE) {
            type = telephonyManager.getNetworkType();
            if (type == TelephonyManager.NETWORK_TYPE_CDMA
                    || type == TelephonyManager.NETWORK_TYPE_GPRS) {
                return "2G";
            } else if (type == TelephonyManager.NETWORK_TYPE_EDGE) {
                return "2G";
            } else if (type == TelephonyManager.NETWORK_TYPE_UMTS
                    || type == TelephonyManager.NETWORK_TYPE_EVDO_0
                    || type == TelephonyManager.NETWORK_TYPE_EVDO_A) {
                return "3G";
            } else {
                return "4G";
            }
        }

        return null;
    }

    /**
     * 获取SD卡剩余空间，true-可用空间大于100MB，false-空间不足或无SD卡
     */
    public static boolean isSDSizeAvailable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            // 取得SD卡文件路径
            String path = Environment.getExternalStorageDirectory().getPath();
            StatFs statFs = new StatFs(path);
            // 获取单个数据块的大小(Byte)
            long l = statFs.getBlockSize();
            if ((statFs.getAvailableBlocks() * l / 1024 / 1024) < 100) { // 可用空间小于100MB
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前时间
     */
    public static String getNowTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(Calendar.getInstance().getTime());
    }

    /**
     * 清理緩存
     * 
     * @return
     */
    public static long clearFile(String catchFiles) {
        File file = new File(catchFiles);
        long time = 0;
        if (!file.exists()) {
            return 0;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    clearFile(files[i].getAbsolutePath());
                }
            }

        } else {
            long curTime = System.currentTimeMillis();
            time = file.lastModified();
            if (curTime - time > 2 * 24 * 60 * 60 * 1000) {
                file.delete();
            }
        }
        return time;
    }

    /**
     * 判断缓存是否过期
     * 
     * @return
     */
    public boolean isExpired(File file, long cacheTime) {
        if (file.exists()) {
            return (file.lastModified() + cacheTime * 1000) < System
                    .currentTimeMillis();
        }
        return true;
    }

    /**
     * 删除指定路径的文件
     * 
     */
    public static void deleteAllFiles(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            int len = 0;
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    deleteAllFiles(files[i].getPath());

                }
            }
        } else {
            file.deleteOnExit();
        }

    }

    /**
     * 网络不可用提示对话框
     */
    public static void netErrorToBack(final Context con) {
        // netErrorToBack(con, true);

        netErrorToToast(con);
    }

    public static void netErrorToToast(final Context con) {
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(con, "网络无法连接，请检查您的网络.", Toast.LENGTH_SHORT)
                        .show();
                Looper.loop();
            }
        }.start();
    }

    /**
     * 获得文件夹的大小
     * 
     * @param cacheFile
     *            要获得文件的大小
     * @return 文件的大小
     */
    protected long getFileSize(File cacheFile) {
        long size = 0;
        File flist[] = cacheFile.listFiles();
        if (flist != null) {
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].isDirectory()) {
                    size = size + getFileSize(flist[i]);
                } else {
                    size = size + flist[i].length();
                }
            }
        }
        return size;
    }

    /**
     * 删除文件夹下的所有内容
     * 
     * @param cacheFile
     */
    protected void deleteFiles(File cacheFile) {
        File flist[] = cacheFile.listFiles();
        if (flist != null) {
            for (int i = 0; i < flist.length; i++) {
                if (flist[i].isDirectory()) {
                    deleteFiles(flist[i]);
                } else {
                    flist[i].delete();
                }
            }
        }
    }

    /**
     * 如果应用程序缓存大于指定的内存大小则清空缓存
     * 
     * @param 当前调用的activity
     */
    protected void clearCacheFile(final Context context) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                File cacheFile = context.getCacheDir();
                long size = getFileSize(cacheFile);
                if (size > 100) {
                    deleteFiles(cacheFile);
                }
            }
        }).start();
    }

    /**
     * 判断当前运行环境是否为模拟器
     * 
     * @return true 当前设备为模拟器 false 当前设备为真机
     */
    public static boolean isEmulator() {
        return ("unknown".equals(Build.BOARD))
                && ("generic".equals(Build.DEVICE))
                && ("generic".equals(Build.BRAND));
    }

    /**
     * 获取设备编号， 若获取不到，用 android_id 代替。
     * 
     * @param context
     * @return null 若为模拟器或无法获取 模拟器也可能反回全0字符串
     */
    public static String getDeviceId(Context context) {
        TelephonyManager telManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telManager.getDeviceId();
        if (imei != null) {
            return imei;
        }
        String dv;
        if ((dv = Settings.Secure.getString(context.getContentResolver(),
                Secure.ANDROID_ID)) != null) {
            dv = md5(dv).toUpperCase();
        }
        return dv;
    }

    /**
     * 获取设备的MAC地址
     * 
     * @param context
     * @return 无法取到或发生错误时返回的都是00:00:00:00:00:00
     */
    public static String getMacAddress(Context context) {
        // 获取mac地址：
        String macAddress = "00:00:00:00:00:00";
        try {
            WifiManager wifiMgr = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = (null == wifiMgr ? null : wifiMgr
                    .getConnectionInfo());
            if (null != info) {
                if (!TextUtils.isEmpty(info.getMacAddress()))
                    macAddress = info.getMacAddress();
                else
                    return macAddress;
            }
        } catch (Exception e) {
            // e.printStackTrace();
            return macAddress;
        }
        return macAddress;
    }

    /**
     * 获取 meta 中设定的值 例： <code>
     * <meta-data android:name="keyName"
     * android:value="0123456789abc" />
     * </code>
     * 
     * @param context
     * @param keyName
     *            name of meta-data
     * @return value of meta-data null 没有获取到
     */
    public static String readMetaByKey(Context context, String keyName) {
        try {
            ApplicationInfo appi = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            Bundle bundle = appi.metaData;
            return (String) bundle.get(keyName);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 检查指定的字符串是否为空。
     * <ul>
     * <li>Util.isEmpty(null) = true</li>
     * <li>Util.isEmpty("") = true</li>
     * <li>Util.isEmpty("   ") = true</li>
     * <li>Util.isEmpty("abc") = false</li>
     * </ul>
     * 
     * @param value
     *            待检查的字符串
     * @return true/false
     */
    public static boolean isEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(value.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    public static String urlGet(String url) throws IOException {
        URLConnection conn = new URL(url).openConnection();
        conn.setConnectTimeout(20000);
        InputStream input = conn.getInputStream();
        String result = inputStreamToString(input);
        return result;
    }

    public static String urlPost(String url, String encodePostParam)
            throws IOException {
        HttpURLConnection conn;
        conn = (HttpURLConnection) new URL(url).openConnection();
        return urlPost(conn, encodePostParam);
    }

    public static String urlPost(HttpURLConnection conn, String encodePostParam)
            throws IOException {
        int responseCode = -1;
        OutputStream osw = null;
        try {
            conn.setConnectTimeout(20000);
            conn.setReadTimeout(12000);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            byte[] bytes = encodePostParam.getBytes("UTF-8");
            conn.setRequestProperty("Content-Length",
                    Integer.toString(bytes.length));
            osw = conn.getOutputStream();
            osw.write(bytes);
            osw.flush();
            responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("请求错误");
            } else {
                String s = inputStreamToString(conn.getInputStream());
                return s;
            }
        } finally {
            try {
                if (osw != null)
                    osw.close();
            } catch (Exception ignore) {
            }
        }
    }

    /**
     * 将inputStream 以系统默认编码转换为字符串
     * 
     */
    public static String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line = null;
        byte arr[] = new byte[1024];
        int len = 0;
        try {
            while ((len = is.read(arr)) != -1) {
                line = new String(arr, 0, len);
                sb.append(line);
            }
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // do nothing
            }
        }
        return sb.toString();
    }

    /**
     * 将Assists 中文件读取为字符串
     */
    public static String getAssistFileAsString(Context context,
            String assistFile) throws IOException {
        String result = null;
        AssetManager assetManager = context.getAssets();
        result = inputStreamToString(assetManager.open(assistFile));
        return result;
    }

    /**
     * 检查邮箱格式
     */
    public static boolean isEmail(String emailAddress) {
        String strPattern = "^[a-zA-Z0-9][ \\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";

        // 利用了Java里面的正则表达式
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(emailAddress);
        // 当目标string与传入的string完全匹配，返回true,否则返回false
        return m.matches();
    }

    /**
     * 检查手机格式
     */
    public static boolean isCellPhoneNumber(String cellPhoneNumber) {
        // 手机号码验证,11位，不知道详细的手机号码段，只是验证开头必须是1和位数
        String strPattern = "^[1][\\d]{10}";
        // 利用了Java里面的正则表达式
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(cellPhoneNumber);
        // 当目标string与传入的string完全匹配，返回true,否则返回false
        return m.matches();
    }

    /*
     * 保存文件到指定路径，后缀名不修改
     */
    public static void saveImageToCache(Context context, Bitmap bitmap,
            String fileName) {
        File dir = new File(context.getCacheDir(), fileName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File imageFile = new File(context.getCacheDir() + fileName);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(imageFile));
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File dirFile : files) {
                deleteFile(dirFile);
            }
        } else {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 开启关闭GPS定位
     * 
     * */
    public static boolean openOrCloseGPS(Context context) {
        boolean isOpen = false;
        LocationManager locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                200, 10, locationListener);
        getcity(locationManager, context);
        // List <Address> addList = null;
        return isOpen;
    }

    static LocationListener locationListener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub

        }
    };

    public static void getcity(LocationManager locationManager, Context context) {
        Location location = locationManager
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
        String lat = Double.toHexString(location.getLatitude());
        String lon = Double.toHexString(location.getLongitude());
        Geocoder geocoder = new Geocoder(context);
        try {
            List<Address> list = geocoder.getFromLocation(
                    location.getLatitude(), location.getLongitude(), 1);
            System.out.println("ddddd");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static void formatRMB(TextView tv) {
        Spannable span = new SpannableString(tv.getText());
        span.setSpan(new RelativeSizeSpan(0.5f), 0, 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(span);
    }

    // public static void limitTextLines(final TextView tv_text, final int
    // limitLine){
    // ViewTreeObserver vto = tv_text.getViewTreeObserver();
    // vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
    // @Override
    // public void onGlobalLayout() {
    // ViewTreeObserver obs = tv_text.getViewTreeObserver();
    // obs.removeGlobalOnLayoutListener(this);
    // if(tv_text.getLineCount() > limitLine){
    // int lineEndIndex = tv_text.getLayout().getLineEnd(5);
    // String text = tv_text.getText().subSequence(0, lineEndIndex-3)+"...";
    // tv_text.setText(text);
    // }
    // }
    // });
    // }
    /**
     * 根据rgb的值返回灰度
     * 
     * @param rgb
     * @return <128代表偏黑
     */
    public static int getGrayOfRGB(String rgb) {
        int gray = 255;
        if (!TextUtils.isEmpty(rgb)) {
            String sixteenRgb = rgb.replace("#", "");
            int sixteenIntRgb = Integer.parseInt(sixteenRgb, 16);
            int r = (sixteenIntRgb & 0xff0000) >> 16;
            int g = (sixteenIntRgb & 0xff00) >> 8;
            int b = (sixteenIntRgb & 0xff);
            gray = (int) (r * 0.3 + g * 0.59 + b * 0.11); // 计算灰度值
        }
        return gray;
    }

    /**
     * 根据文字内容、字号与TextView固定宽度判断显示时是否只会显示一行
     * 
     * @param context
     *            调用该方法的Context
     * @param textViewWidth
     *            TextView固定宽度，单位px
     * @param textSize
     *            字号，单位sp
     * @param textContent
     *            文字内容
     * @return 一行（或传入参数有错误）为true，多行为false
     **/
    public static boolean isSingleLine(Context context, float textViewWidth,
            float textSize, String textContent) {
        if (context == null || TextUtils.isEmpty(textContent)) {
            return true;
        }
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        float textWidth = paint.measureText(textContent);
        if (textWidth <= textViewWidth) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取设备的ip地址
     * 
     * @param context
     * @return
     */
    public static String getIpAddress(Context context) {
        String ipAddress = "0.0.0.0";
        WifiManager wifiMan = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        if (wifiMan != null) {
            WifiInfo info = wifiMan.getConnectionInfo();
            if (info != null) {
                int ip = info.getIpAddress();
                ipAddress = (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "."
                        + ((ip >> 16) & 0xFF) + "." + ((ip >> 24) & 0xFF);
            }
        }
        return ipAddress;
    }

    public static String getResolution(Context context) {
        WindowManager window = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = window.getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        return width + "*" + height;
    }

    /**
     * 获取图片缓存路径
     * 
     * @return
     */
    public static String getImgCachePath(Context context) {
        String externalPath;
        if (DefaultTools.isSDSizeAvailable()) {
            externalPath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
        } else {
            externalPath = context.getFilesDir().getAbsolutePath();
        }
        return externalPath;
    }

    public static String getChannel(Context context) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(appInfo == null){
            return "jumei_channel";
        }
        String msg = appInfo.metaData.getString("hilo_channel");
        return msg;
    }

}
