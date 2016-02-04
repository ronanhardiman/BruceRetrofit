package net.iyouqu.bruceretrofit.Service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by q on 2016/2/4.
 */
public class BootIntentService extends IntentService{
	/**
	 * Creates an IntentService.  Invoked by your subclass's constructor.
	 *
	 * @param name Used to name the worker thread, important only for debugging.
	 */
	public BootIntentService() {
		super("BootIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		//Release the wake lock provided by the WakefulBroadcastReceiver
		WakefulBroadcastReceiver.completeWakefulIntent(intent);
	}
}
