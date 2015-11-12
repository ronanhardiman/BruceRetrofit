package net.iyouqu.bruceretrofit.network;

import net.iyouqu.bruceretrofit.Bean.FuliList;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by q on 2015/9/27.
 */
public interface BruceApi {
	@GET("/data/福利/"+BruceFactory.pageSize+"/{page}")
	void getListData(@Path("page") int page, Callback<FuliList> response);

	//但如果我们想获得JSON字符串，Callback的泛型里就不能写POJO类了，要写Response（retrofit.client包下）
	@GET("/data/福利/"+BruceFactory.pageSize+"/{page}")
	void getResponseData(@Path("page") int page, Callback<Response> response);
}
