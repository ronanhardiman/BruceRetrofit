package net.iyouqu.bruceretrofit.Service;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by q on 2016/2/4.
 */
public class BroadcastIntentService extends IntentService{
	private static final String TAG = "BroadcastIntentService";
	public static final String ACTION = "net.iyouqu.bruceretrofit.action";

	/**
	 * Creates an IntentService.  Invoked by your subclass's constructor.
	 *
	 * @param name Used to name the worker thread, important only for debugging.
	 */
	public BroadcastIntentService() {
		super("BroadcastIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.e(TAG, "onHandleIntent");
		String var = intent.getStringExtra("foo");
		Intent intent1 = new Intent(ACTION);
		intent1.putExtra("foo", var);
		intent1.putExtra("resultCode", Activity.RESULT_OK);
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent1);
	}
}
