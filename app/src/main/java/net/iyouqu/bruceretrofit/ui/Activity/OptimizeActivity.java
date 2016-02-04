package net.iyouqu.bruceretrofit.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import net.iyouqu.bruceretrofit.R;
import net.iyouqu.bruceretrofit.Service.BackgroundIntentService;
import net.iyouqu.bruceretrofit.Service.OptimizeReceiver;
import net.iyouqu.bruceretrofit.ui.Base.BaseActivity;
import net.iyouqu.bruceretrofit.util.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by q on 2016/2/2.
 */
public class OptimizeActivity extends BaseActivity{
	@Bind(R.id.tv_optimize_background)
	TextView optimize_activity_title;
	private OptimizeReceiver optimizeReceiver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.optimize_layout);
		ButterKnife.bind(this);
		setupServiceReceiver();
	}
	@OnClick({R.id.tv_optimize_background,R.id.tv_optimize_broadcast,R.id.tv_alarm_broadcast})
	void OnItemClick(View view) {
		switch (view.getId()) {
			case R.id.tv_optimize_background:
				onStartService();
				break;
			case R.id.tv_optimize_broadcast:
				startActivity(new Intent(OptimizeActivity.this, BroadcastActivity.class));
				break;
			case R.id.tv_alarm_broadcast:
				startActivity(new Intent(OptimizeActivity.this,AlarmActivity.class));
				break;
		}
	}

	private void setupServiceReceiver() {
		optimizeReceiver = new OptimizeReceiver(new Handler());
		optimizeReceiver.setReceiver(new OptimizeReceiver.Receiver() {
			@Override
			public void onReceiveResult(int resultCode, Bundle resultData) {
				if (resultCode == RESULT_OK) {
					String result = resultData.getString("foo");
					ToastUtils.showToast(OptimizeActivity.this, result);
				}
			}
		});
	}

	private void onStartService() {
		Intent intent = new Intent(this, BackgroundIntentService.class);
		intent.putExtra("foo", "foo");
		intent.putExtra("receiver", optimizeReceiver);
		startService(intent);
	}

}
