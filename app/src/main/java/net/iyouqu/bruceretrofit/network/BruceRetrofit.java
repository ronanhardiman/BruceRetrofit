package net.iyouqu.bruceretrofit.network;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import net.iyouqu.bruceretrofit.App;
import net.iyouqu.bruceretrofit.util.NetWorkUtil;

import java.io.File;
import java.util.concurrent.TimeUnit;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by q on 2015/9/27.
 */
public class BruceRetrofit {
	final BruceApi bruceApi;
	final static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").serializeNulls().create();

	BruceRetrofit() {
		File httpCacheDirectory = new File(App.getmContext().getCacheDir(), "okhttp_cache_responses");
//		if(httpCacheDirectory.exists()){
//
//		}else {
//
//		}
		Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
		OkHttpClient okHttpClient = new OkHttpClient();
		okHttpClient.setReadTimeout(12, TimeUnit.SECONDS);
		okHttpClient.networkInterceptors().add(new StethoInterceptor());
		okHttpClient.setCache(cache);

		//		File httpCacheDirectory = new File(getApplicationContext()
		//				.getCacheDir().getAbsolutePath(), "HttpCache");
		//
		//		HttpResponseCache httpResponseCache = null;
		//		try {
		//			httpResponseCache = new HttpResponseCache(httpCacheDirectory, 10 * 1024);
		//		} catch (IOException e) {
		//			Log.e(getClass().getSimpleName(), "Could not create http cache", e);
		//		}
		//
		//		OkHttpClient okHttpClient = new OkHttpClient();
		//		okHttpClient.setResponseCache(httpResponseCache);
		//		builder.setClient(new OkClient(okHttpClient));

		RestAdapter restAdapter = new RestAdapter.Builder()
				.setClient(new OkClient(okHttpClient))
				.setEndpoint(BruceApi.ENDPOINT)
				.setRequestInterceptor(new RequestInterceptor() {
					@Override
					public void intercept(RequestFacade request) {
						request.addHeader("Accept", "application/json;versions=1");
						if (NetWorkUtil.isNetWorkConnected(App.getmContext())) {
							int maxAge = 30; // read from cache for 1 minute
							request.addHeader("Cache-Control", "public, max-age=" + maxAge);
						} else {
							int maxStale = 60 * 60 * 24 /** 28*/; // tolerate 4-weeks stale
							request.addHeader("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
						}
					}
				})
				.setConverter(new GsonConverter(gson))
				.setLogLevel(RestAdapter.LogLevel.FULL)
				.build();

		bruceApi = restAdapter.create(BruceApi.class);
	}


	public BruceApi getService() {
		return bruceApi;
	}
}
