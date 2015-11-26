package net.iyouqu.bruceretrofit.network;

/**
 * Created by q on 2015/9/27.
 */
public class BruceFactory {
	public static String[] image_urllist = new String[]{
			"http://ww2.sinaimg.cn/large/7a8aed7bjw1eye51p41xlj20go0m8mz0.jpg",
			"http://ww3.sinaimg.cn/large/7a8aed7bjw1eyd07uugyvj20qo0hqgom.jpg",
			"http://ww2.sinaimg.cn/large/7a8aed7bjw1eybuo04j6dj20hq0qon0s.jpg",
			"http://ww4.sinaimg.cn/large/7a8aed7bjw1eyaov0c9z4j20iz0sg40t.jpg",
			"http://ww2.sinaimg.cn/large/7a8aed7bjw1ey77s2wab8j20zk0nmdm2.jpg",
			"http://ww2.sinaimg.cn/large/7a8aed7bjw1ey6238m03pj20gy0op77l.jpg"
	};
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
