package net.iyouqu.bruceretrofit.ui.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.iyouqu.bruceretrofit.R;
import net.iyouqu.bruceretrofit.BroadcastReceiver.AlarmBroadCast;
import net.iyouqu.bruceretrofit.ui.Base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by q on 2016/2/4.
 */
public class AlarmActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm_layout);
		ButterKnife.bind(this);
	}
	@OnClick({R.id.tv_alarm_broadcast})
	public void OnClick(View view) {
		switch (view.getId()) {
			case R.id.tv_alarm_broadcast:
				scheduleAlarm();
				break;
		}
	}

	private void scheduleAlarm() {
		Intent intent = new Intent(getApplicationContext(), AlarmBroadCast.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, AlarmBroadCast.REQUEST_CODE,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		long firstMillis = System.currentTimeMillis();
		AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,firstMillis,AlarmManager.INTERVAL_FIFTEEN_MINUTES,pendingIntent);
	}

	public void cancelAlarm() {
		Intent intent = new Intent(getApplicationContext(), AlarmBroadCast.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, AlarmBroadCast.REQUEST_CODE,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(pendingIntent);
	}

}
