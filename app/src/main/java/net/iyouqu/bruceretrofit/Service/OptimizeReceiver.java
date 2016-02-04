package net.iyouqu.bruceretrofit.Service;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by q on 2016/2/2.
 */
public class OptimizeReceiver extends ResultReceiver{
	private Receiver receiver;
	/**
	 * Create a new ResultReceive to receive results.  Your
	 * {@link #onReceiveResult} method will be called from the thread running
	 * <var>handler</var> if given, or from an arbitrary thread if null.
	 *
	 * @param handler
	 */
	public OptimizeReceiver(Handler handler) {
		super(handler);
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	public interface Receiver {
		void onReceiveResult(int resultCode, Bundle resultData);
	}

	@Override
	protected void onReceiveResult(int resultCode, Bundle resultData) {
		//		super.onReceiveResult(resultCode, resultData);
		if (receiver != null) {
			receiver.onReceiveResult(resultCode,resultData);
		}
	}
}
