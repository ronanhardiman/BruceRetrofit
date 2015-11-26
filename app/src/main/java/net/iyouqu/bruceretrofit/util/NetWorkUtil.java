package net.iyouqu.bruceretrofit.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;

import net.iyouqu.bruceretrofit.R;

/**
 */
public class NetWorkUtil {

	public static final int TYPE_UNKNOWN = -1;

	public NetWorkUtil() {
	}

	private static NetworkInfo getActiveNetworkInfo(Context context) {
		if (context == null) {
			return null;
		} else {
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			return cm == null ? null : cm.getActiveNetworkInfo();
		}
	}

	private static int getActiveNetworkType(Context context) {
		NetworkInfo info = getActiveNetworkInfo(context);
		return info != null && info.isConnected() ? info.getType() : -1;
	}

	public static boolean isNetworkAvailable(Context context) {
		NetworkInfo info = getActiveNetworkInfo(context);
		return info != null && info.isConnected();
	}

	public static boolean isWiFiConnected(Context context) {
		return getActiveNetworkType(context) == 1;
	}

	@TargetApi(17)
	public static boolean isAirplaneModeOn(Context context) {
		return Build.VERSION.SDK_INT < 17 ? Settings.System.getInt(context.getContentResolver(), "airplane_mode_on", 0) != 0 : Settings.Global.getInt(context.getContentResolver(), "airplane_mode_on", 0) != 0;
	}

	public static boolean checkConnection(Context context) {
		if (isNetworkAvailable(context)) {
			return true;
		} else {
			ToastUtils.showToast(context, R.string.no_network_message);
			return false;
		}
	}

	/**
	 * 判断当前网络是否已连接
	 *
	 * @param context
	 * @return
	 */
	@Deprecated
	public static boolean isNetWorkConnected(Context context) {
		boolean result;
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		result = netInfo != null && netInfo.isConnected();
		return result;
	}


	/**
	 * 判断当前的网络连接方式是否为WIFI
	 *
	 * @param context
	 * @return
	 */
	@Deprecated
	public static boolean isWifiConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return wifiNetworkInfo.isConnected();
	}

}
