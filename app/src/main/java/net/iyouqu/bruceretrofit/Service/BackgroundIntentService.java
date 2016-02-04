package net.iyouqu.bruceretrofit.Service;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by q on 2016/2/2.
 */
public class BackgroundIntentService extends IntentService{
	private Context mContext;

	public BackgroundIntentService() {
		super("BackgroundIntentService");
	}

//	/**
//	 * Creates an IntentService.  Invoked by your subclass's constructor.
//	 *
//	 * @param name Used to name the worker thread, important only for debugging.
//	 */
//	public BackgroundIntentService(String name) {
//		super(name);
//	}

	@Override
	public void onCreate() {
		super.onCreate();
		// If a Context object is needed, call getApplicationContext() here.
		this.mContext = getApplicationContext();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// This describes what will happen when service is triggered
		ResultReceiver receiver = intent.getParcelableExtra("receiver");
		String val = intent.getStringExtra("foo");

		Bundle bundle = new Bundle();
		bundle.putString("foo",val);
		receiver.send(Activity.RESULT_OK,bundle);
	}
}
