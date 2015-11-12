package net.iyouqu.bruceretrofit.network;

/**
 * Created by q on 2015/9/27.
 */
public class BruceFactory {
	protected static final Object monitor = new Object();
	static BruceApi mSingleton;
	public static final int pageSize = 10;
	public static final int gankSize = 5;

	public static BruceApi getSingLeton(){
		synchronized (monitor){
			if (mSingleton == null){
				mSingleton = new BruceRetrofit().getService();
			}
			return mSingleton;
		}
	}
}
