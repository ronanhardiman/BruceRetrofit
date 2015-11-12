package net.iyouqu.bruceretrofit.network;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by q on 2015/9/27.
 */
public class BruceRetrofit {
	final BruceApi bruceApi;
	final static Gson gson =
			new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").serializeNulls().create();

	BruceRetrofit(){
		OkHttpClient okHttpClient = new OkHttpClient();
		okHttpClient.setReadTimeout(12, TimeUnit.SECONDS);
		okHttpClient.networkInterceptors()
		.add(new StethoInterceptor());

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

		RestAdapter restAdapter = new RestAdapter.Builder().setClient(new OkClient(okHttpClient))
				.setEndpoint("http://gank.avosapps.com/api")
				.setConverter(new GsonConverter(gson))
				.setLogLevel(RestAdapter.LogLevel.FULL)
				.build();

		bruceApi = restAdapter.create(BruceApi.class);
	}


	public BruceApi getService(){
		return bruceApi;
	}
}
