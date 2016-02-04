package net.iyouqu.bruceretrofit.Service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import net.iyouqu.bruceretrofit.util.ToastUtils;

/**
 * Created by q on 2016/2/4.
 */
public class AlarmIntentService extends IntentService{
	private static final String TAG = "AlarmIntentService";

	/**
	 * Creates an IntentService.  Invoked by your subclass's constructor.
	 *
	 * @param name Used to name the worker thread, important only for debugging.
	 */
	public AlarmIntentService() {
		super("AlarmIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.e(TAG, "onHandleIntent");
		// error
		//Handler (android.os.Handler) {1b2ab73f} sending message to a Handler on a dead thread
		//ToastUtils.showToast(this, "onHandleIntent do something");
		String var = intent.getStringExtra("foo");
		Handler handler = new Handler(getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {
				ToastUtils.showToast(getApplicationContext(), "onHandleIntent do something");
			}
		});
	}
}
