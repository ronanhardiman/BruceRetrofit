package net.iyouqu.bruceretrofit.ui.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import net.iyouqu.bruceretrofit.R;
import net.iyouqu.bruceretrofit.Service.BroadcastIntentService;
import net.iyouqu.bruceretrofit.ui.Base.BaseActivity;
import net.iyouqu.bruceretrofit.util.ToastUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by q on 2016/2/4.
 */
public class BroadcastActivity extends BaseActivity{
	BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction() == BroadcastIntentService.ACTION) {
				int resultCode = intent.getIntExtra("resultCode",RESULT_CANCELED);
				if (resultCode == RESULT_OK) {
					String foo = intent.getStringExtra("foo");
					ToastUtils.showToast(BroadcastActivity.this, foo);
				}
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.broadcast_layout);
		ButterKnife.bind(this);
	}
	@OnClick({R.id.tv_optimize_broadcast})
	public void OnClick(View view) {
		switch (view.getId()) {
			case R.id.tv_optimize_broadcast:
				onStartService();
				break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter intentFilter = new IntentFilter(BroadcastIntentService.ACTION);
		LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, intentFilter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
	}

	private void onStartService() {
		Intent intent = new Intent(this, BroadcastIntentService.class);
		intent.putExtra("foo", "foo");
		startService(intent);
	}
}
