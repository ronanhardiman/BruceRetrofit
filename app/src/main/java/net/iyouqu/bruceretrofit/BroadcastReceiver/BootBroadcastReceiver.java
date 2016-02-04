package net.iyouqu.bruceretrofit.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import net.iyouqu.bruceretrofit.Service.BootIntentService;

/**
 * Created by q on 2016/2/4.
 */
public class BootBroadcastReceiver extends WakefulBroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent) {
		//Launch the specified service when this message is received
		Intent intent1 = new Intent(context, BootIntentService.class);
		context.startService(intent1);
	}
}
