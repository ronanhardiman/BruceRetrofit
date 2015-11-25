package net.iyouqu.bruceretrofit;

import android.app.Application;
import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.facebook.stetho.Stetho;

/**
 * Created by q on 2015/9/29.
 */
public class App extends Application{
	private static final int GLIDE_DISKCACHE = 1024 * 1024 * 100;
	private static Context mContext;

	public static Context getmContext() {
		return mContext;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		this.mContext = getApplicationContext();
		initStetho();
		initGlide();
	}

	private void initGlide() {
		new GlideBuilder(this).setDiskCache(new InternalCacheDiskCacheFactory(this,GLIDE_DISKCACHE));
	}

	private void initStetho() {
		Stetho.initializeWithDefaults(this);
	}
}
