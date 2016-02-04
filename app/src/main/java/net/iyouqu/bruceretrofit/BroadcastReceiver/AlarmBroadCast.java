package net.iyouqu.bruceretrofit.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import net.iyouqu.bruceretrofit.Service.AlarmIntentService;

/**
 * Created by q on 2016/2/4.
 */
public class AlarmBroadCast extends BroadcastReceiver{
	private static final String TAG = "AlarmBroadCast";
	public static final int REQUEST_CODE = 101;
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e(TAG, "onReceive");
		Intent intent1 = new Intent(context, AlarmIntentService.class);
		intent1.putExtra("foo", "foo");
		context.startService(intent1);
	}
}
