package net.iyouqu.bruceretrofit.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by q on 2016/2/4.
 */
public class TestService extends Service{

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}


	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}
