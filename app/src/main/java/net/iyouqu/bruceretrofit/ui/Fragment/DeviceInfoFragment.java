package net.iyouqu.bruceretrofit.ui.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tigerfour.libraryutils.DeviceHelper;

import net.iyouqu.bruceretrofit.R;
import net.iyouqu.bruceretrofit.util.ToastUtils;

import java.util.concurrent.TimeUnit;

import rx.Scheduler;
import rx.Subscription;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by q on 2016/1/11.
 */
public class DeviceInfoFragment extends Fragment{
	TextView tv_info1;
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
		}
	};
	private final static int dur_time = 30 * 1000;
	private Scheduler.Worker mWorker;
	private Subscription schedule;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.info_layout, container, false);
//		ButterKnife.bind(this, view);


		DeviceHelper.setContext(getActivity());
//		tv_info = (TextView) findViewById(R.id.tv_info);
		tv_info1 = (TextView) view.findViewById(R.id.tv_info1);
//		tv_info2 = (TextView) findViewById(R.id.tv_info2);
//		tv_info3 = (TextView) findViewById(R.id.tv_info3);
//		tv_info4 = (TextView) findViewById(R.id.tv_info4);
//		tv_info5 = (TextView) findViewById(R.id.tv_info5);
//		tv_info6 = (TextView) findViewById(R.id.tv_info6);

		tv_info1.setText(tv_info1.getText() + "" + DeviceHelper.getDpi());
		mHandler.postDelayed(mRunnable, dur_time);
//		mHandler.sendEmptyMessageDelayed();
		return view;
	}

	private void startWorker() {
		if (mWorker == null) {
			mWorker = Schedulers.newThread().createWorker();
			schedule = mWorker.schedule(new Action0() {
				@Override
				public void call() {
					showToast();
				}
			}, dur_time, TimeUnit.MILLISECONDS);
		}
	}

	private void unSub() {
		if (schedule != null && !schedule.isUnsubscribed()) {
			schedule.unsubscribe();;
		}
	}

	private void cancleHandlerMessage() {
		mHandler.removeCallbacks(mRunnable);
	}

	private Runnable mRunnable = new Runnable() {
		@Override
		public void run() {
			showToast();
		}
	};

	private void showToast() {
		ToastUtils.showToast(getActivity(), "showToast", ToastUtils.Duration.SHORT).show();
	}


//	@OnClick({R.id.btn_mvp,R.id.btn_optimize_background,R.id.btn_data_background})
//	void OnItemClick(View view) {
//		switch (view.getId()) {
//			case R.id.btn_mvp:
//				startActivity(new Intent(getContext(), MVPActivity.class));
//				break;
//			case R.id.btn_optimize_background:
//				startActivity(new Intent(getContext(), OptimizeActivity.class));
//				break;
//			case R.id.btn_data_background:
//				startActivity(new Intent(getContext(), DataStructureActivity.class));
//				break;
//		}
//	}
}
