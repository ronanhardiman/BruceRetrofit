package net.iyouqu.bruceretrofit;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.facebook.stetho.Stetho;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import net.iyouqu.bruceretrofit.network.ConnectionChangeReceiver;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by q on 2015/9/29.
 */
public class App extends Application{
	private static final int GLIDE_DISKCACHE = 1024 * 1024 * 20;
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

		ApplicationLifecycleMonitor applicationLifecycleMonitor = new ApplicationLifecycleMonitor();
		registerComponentCallbacks(applicationLifecycleMonitor);
		registerActivityLifecycleCallbacks(applicationLifecycleMonitor);

		//只有调试模式下 才启用日志输出
		if(BuildConfig.DEBUG){
			Logger.init("Bruce").hideThreadInfo().setMethodCount(0);
		}else{
			Logger.init("Bruce").setLogLevel(LogLevel.NONE);
		}
		/**for parser**/
		// Enable Local Datastore.
		Parse.enableLocalDatastore(this);

		// Add your initialization code here
		Parse.initialize(this);
		ParseInstallation.getCurrentInstallation().saveInBackground();

		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
		// Optionally enable public read access.
		// defaultACL.setPublicReadAccess(true);
		ParseACL.setDefaultACL(defaultACL, true);


		Map<String, String> dimensions = new HashMap<String, String>();
		// What type of news is this?
		dimensions.put("category", "politics");
		// Is it a weekday or the weekend?
		dimensions.put("dayType", "weekday");
		// Send the dimensions to Parse along with the 'read' event

		ParseAnalytics.trackEventInBackground("read", dimensions);
	}

	private void initGlide() {
		new GlideBuilder(this).setDiskCache(new InternalCacheDiskCacheFactory(this, GLIDE_DISKCACHE));
	}

	private void initStetho() {
		Stetho.initializeWithDefaults(this);
	}

	private class ApplicationLifecycleMonitor implements ActivityLifecycleCallbacks, ComponentCallbacks2{
		boolean mIsInBackground = true;

		@Override
		public void onTrimMemory(int level) {
			if (level == TRIM_MEMORY_UI_HIDDEN) {
				// We're in the Background
				mIsInBackground = true;
				onAppGoesToBackground();
			}else {
				mIsInBackground = false;
			}
			boolean evictBitmaps = false;
			switch (level) {
				case TRIM_MEMORY_COMPLETE:
				case TRIM_MEMORY_MODERATE:
				case TRIM_MEMORY_RUNNING_MODERATE:
				case TRIM_MEMORY_RUNNING_CRITICAL:
				case TRIM_MEMORY_RUNNING_LOW:
					evictBitmaps = true;
					break;
				default:
					break;
			}
		}

		@Override
		public void onConfigurationChanged(Configuration newConfig) {

		}

		@Override
		public void onLowMemory() {

		}

		@Override
		public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

		}

		@Override
		public void onActivityStarted(Activity activity) {

		}

		@Override
		public void onActivityResumed(Activity activity) {
			if (mIsInBackground) {
				onAppComesFromBackground();
			}
			mIsInBackground = false;
		}

		@Override
		public void onActivityPaused(Activity activity) {

		}

		@Override
		public void onActivityStopped(Activity activity) {

		}

		@Override
		public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

		}

		@Override
		public void onActivityDestroyed(Activity activity) {

		}

		/**
		 * This method is called when:
		 * 1. the app starts (but it's not opened by a service or a broadcast receiver, i.e. an activity is resumed)
		 * 2. the app was in background and is now foreground
		 */
		public void onAppComesFromBackground() {
			ConnectionChangeReceiver.setEnabled(App.this,true);
		}

		public void onAppGoesToBackground() {
//			AppLog.i(T.UTILS, "App goes to background");
			ConnectionChangeReceiver.setEnabled(App.this, false);
		}
	}
}
