package net.iyouqu.bruceretrofit.Bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by q on 2015/11/24.
 */
public class DataSet implements Serializable{

	public Result results;
	public List<String> category;
	public class Result implements Serializable{
		@SerializedName("Android") public List<Girl> androidList;
		@SerializedName("iOS") public List<Girl> iOSList;
		@SerializedName("休息视频") public List<Girl> restList;
		@SerializedName("福利") public List<Girl> welfareList;
		@SerializedName("拓展资源") public List<Girl> resourceList;
		@SerializedName("瞎推荐") public List<Girl> recommendList;
	}
}
