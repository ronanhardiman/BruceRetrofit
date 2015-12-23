package net.iyouqu.bruceretrofit.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by q on 2015/12/20.
 */
public class ThreadPoolUtils {
	private static final String TAG = "ThereadPoolUtils";
	private static ThreadPoolUtils mInstance = null;
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 0:
					ToastUtils.showToast(mContext, "init");
					break;
				case 1:
					break;
				default:break;
			}
		}
	};
	private ThreadPoolUtils(Context mContext) {
		this.mContext = mContext;
	}
	private Context mContext;
	public static synchronized ThreadPoolUtils getInstance(Context mContext) {
		if (mInstance == null) {
			mInstance = new ThreadPoolUtils(mContext);
		}
		return mInstance;
	}
	/**
	 * 	定长线程池，支持定时及周期性任务执行;
	 *  用于线程管理的线程池，它可以让线程在设定时间执行；由于它内部的线程不会自动消毁，所以此线程池只保留一个线程
	 */
	private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
	/**
	 * 可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程.
	 */
	private ExecutorService executors = Executors.newCachedThreadPool();
	/**
	 * 定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
	 */
	private ExecutorService executorService = Executors.newFixedThreadPool(1);
	/**
	 * 单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行
	 */
	private ExecutorService executorService2 = Executors.newSingleThreadExecutor();

	public void init() {
		Log.e(TAG,"init");
		scheduledExecutorService.schedule(new Runnable() {
			@Override
			public void run() {
				Log.e(TAG,"scheduledExecutorService run");
//				ToastUtils.showToast(mContext, "init");
				handler.obtainMessage(0).sendToTarget();
			}
		}, 4,TimeUnit.SECONDS);
	}
}
