package com.tigerfour.libraryutils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

//获取设备相关信息
public class DeviceHelper {

	private static Context mContext;
	private static TelephonyManager mTelephonyManager;

	public static void setContext(Context context) {
		mContext = context;
	}

	public static String getDpi() {
		String info;
//		DisplayMetrics metric = new DisplayMetrics();
		DisplayMetrics metric = mContext.getResources().getDisplayMetrics();

		int width = metric.widthPixels;  // 屏幕宽度（像素）
		int height = metric.heightPixels;  // 屏幕高度（像素）
		float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
		int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
		info = "width:"+width+" height:"+height+" d:"+density+" dpi:"+densityDpi;
		return info;
	}

	public static String getImsi() {
		String imsi = "";
		imsi = getTelephonyManager().getSubscriberId();
		return imsi;
	}

	public static String getImei() {
		String imei = "";
		imei = getTelephonyManager().getDeviceId();
		return imei;
	}

	private static TelephonyManager getTelephonyManager() {
		if (mTelephonyManager == null)
			mTelephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		return mTelephonyManager;
	}

	public static String getPhoneNumber() {
		String NativePhoneNumber = "";
		NativePhoneNumber = getTelephonyManager().getLine1Number();
		return NativePhoneNumber;
	}

	public static String getProviderName() {
		String provider = "";
		String imsi = getImsi();
		// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
		if (imsi != null) {

			if (imsi.startsWith("46000") || imsi.startsWith("46002") || imsi.startsWith("46007")) {
				provider = "mobile";//移动
			} else if (imsi.startsWith("46001")) {
				provider = "unicom";//联通
			} else if (imsi.startsWith("46003")) {
				provider = "telecom";//电信
			}
		}

		return provider;
	}

	public static String getMacAddress() {
		String mac = "";
		WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		if (wifi != null) {
			WifiInfo info = wifi.getConnectionInfo();
			if (info != null) {
				mac = info.getMacAddress();
			}
		}

		return mac;
	}

	public static String getPhoneModel() {
		String model = "";
		model = android.os.Build.MODEL;
		return model;
	}

	public static String getLanguage() {
		String language = "";
		Locale locale = mContext.getResources().getConfiguration().locale;
		if (locale != null) {
			language = locale.getLanguage();
		}
		return language;
	}

	public static String getNetworkType() {
		ConnectivityManager connManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager != null) {
			NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI); // wifi
			NetworkInfo gprs = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE); // gprs

			if (wifi != null && wifi.getState() == State.CONNECTED) {
				return "wifi";
			} else if (gprs != null && gprs.getState() == State.CONNECTED) {
				return "gprs";
			}
		}

		return "none";
	}

	public static String getOsVersion() {
		String osv = "";
		osv = android.os.Build.VERSION.RELEASE;
		return osv;
	}

	public static int getVersionCode() {
		int versionCode = 0;
		PackageManager packageManager = mContext.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		try {
			if (packageManager != null) {
				packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
				if (packInfo != null) {
					versionCode = packInfo.versionCode;
				}
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
//			AppLog.e("DeviceHelper", "getVersionCode error" + e);
		}
		return versionCode;
	}

	public static String getAppVersion() {
		// 获取packagemanager的实例
		PackageManager packageManager = mContext.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		String versionName = "";
		try {
			if (packageManager != null) {
				packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
				if (packInfo != null) {
					versionName = packInfo.versionName;
				}
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
//			AppLog.e("DeviceHelper", "getAppVersion error" + e);
		}
		return versionName;
	}

	/**
	 * 获取CPU信息
	 */
	public static String getCpuInfo() {
		String str1 = "/proc/cpuinfo";
		String str2 = "";
		String cpuInfo = "";
		String[] arrayOfString = null;
		try {
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			str2 = localBufferedReader.readLine();
			if (!TextUtils.isEmpty(str2)) {
				arrayOfString = str2.split("\\s+");
				for (int i = 2; i < arrayOfString.length; i++) {
					cpuInfo = cpuInfo + arrayOfString[i] + " ";
				}
			}
			localBufferedReader.close();
		} catch (IOException e) {
			return cpuInfo;
		}
		return cpuInfo;
	}

	/**
	 * 获取蓝牙地址
	 */
	public static String getBluetoothAddress() {
		String address = "";
		try {
			BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
				address = bluetoothAdapter.getAddress();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return address;
		}
		return address;
	}

	/**
	 * 获取屏幕分辨率
	 */
	public static String getResolution() {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
		int height = sp.getInt("screen_height", 0);
		int width = sp.getInt("screen_width", 0);
		return height + "*" + width;
	}
}
