package net.iyouqu.bruceretrofit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.orhanobut.logger.Logger;

import net.iyouqu.bruceretrofit.Bean.DataSet;
import net.iyouqu.bruceretrofit.Bean.FuliList;
import net.iyouqu.bruceretrofit.Bean.Girl;
import net.iyouqu.bruceretrofit.Db.GirlDaoHelper;
import net.iyouqu.bruceretrofit.R;
import net.iyouqu.bruceretrofit.adapter.BtAdapter;
import net.iyouqu.bruceretrofit.interfacebt.OnBtTouchListener;
import net.iyouqu.bruceretrofit.interfacebt.OnLongClickListener;
import net.iyouqu.bruceretrofit.network.BruceFactory;
import net.iyouqu.bruceretrofit.ui.Base.BaseActivity;
import net.iyouqu.bruceretrofit.widget.MultiSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class MainActivity extends BaseActivity implements OnBtTouchListener, OnLongClickListener {
	private static final String TAG = MainActivity.class.getSimpleName();
	private RecyclerView mRecyclerView;
	private MultiSwipeRefreshLayout mSwipeRefreshLayout;
	private BtAdapter mbtAdapter;
	private List<Girl> mGirlList = new ArrayList<>();
	int mPage = 1;
	private static final int PRELOAD_SIZE = 6;
	boolean mIsFirstTimeTouchBottom = true;
	GirlDaoHelper mDaoHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDaoHelper = GirlDaoHelper.getInstance(this);
		initCache();
		initView();
	}

	private void initView() {
		mRecyclerView = (RecyclerView) findViewById(R.id.recycleview_main);
		final StaggeredGridLayoutManager layoutManager =
				new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(layoutManager);
		mbtAdapter = new BtAdapter(this, mGirlList);
		mRecyclerView.setAdapter(mbtAdapter);
		mRecyclerView.addOnScrollListener(getScrollToBottomListener(layoutManager));
		mbtAdapter.setOnBtTouchListener(this);
		mbtAdapter.setOnLongClickListener(this);

		mSwipeRefreshLayout = (MultiSwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
		initSwipeRefresh();
		getData(true);
//		getDataOb();
	}

	private void initSwipeRefresh() {
		if (mSwipeRefreshLayout != null) {
			mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_3,
					R.color.refresh_progress_2, R.color.refresh_progress_1);
			// do not use lambda!!
			mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
				@Override public void onRefresh() {
					requestDataRefresh();
				}
			});
		}
	}

	public void requestDataRefresh() {
		setRefreshing(false);
	}

	public void setRefreshing(boolean refreshing) {
		if (mSwipeRefreshLayout == null) {
			return;
		}
		if (!refreshing) {
			// 防止刷新消失太快，让子弹飞一会儿. do not use lambda!!
			mSwipeRefreshLayout.postDelayed(new Runnable() {
				@Override public void run() {
					mSwipeRefreshLayout.setRefreshing(false);
				}
			}, 1000);
		}
		else {
			mSwipeRefreshLayout.setRefreshing(true);
		}
	}

	/**
	 * 加载缓存
	 */
	private void initCache(){
		ArrayList<Girl> girlList = mDaoHelper.getGirlList();
		Log.e(TAG, "initCache: girllist:"+girlList.size());
		mGirlList.addAll(girlList);
	}

	private void getDataOb() {

		Calendar calendar = Calendar.getInstance();
		mCurrentDate = new Date(System.currentTimeMillis());
		calendar.setTime(mCurrentDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		BruceFactory.getSingLeton().getDataObservable(year,month,day)
				.observeOn(AndroidSchedulers.mainThread())
				.map(new Func1<DataSet, DataSet.Result>() {
					@Override
					public DataSet.Result call(DataSet dataSet) {
						Logger.e(TAG,"call dataSet:");
						return dataSet.results;
					}
				})
				.map(new Func1<DataSet.Result, List<Girl>>() {
					@Override
					public List<Girl> call(DataSet.Result result) {
						Logger.e(TAG,"call result:");
						return changeData(result);
					}
				})
				.subscribe(new Subscriber<List<Girl>>() {
					@Override
					public void onCompleted() {
						Logger.e(TAG,"onComplected:");
						mCurrentDate = new Date(mCurrentDate.getTime() - DAY_OF_MILLISECOND);
					}

					@Override
					public void onError(Throwable e) {
						Logger.e(TAG,"onError e:"+e.getMessage());
					}

					@Override
					public void onNext(List<Girl> list) {
						Logger.e(TAG,"onNext:");
					}
				});
	}
	private Date mCurrentDate;
	private static final int DAY_OF_MILLISECOND = 24*60*60*1000;
	private List<Girl> girlList = new ArrayList<>();

	private List<Girl> changeData(DataSet.Result result) {
		girlList.clear();
		if (result.restList != null) {
			girlList.addAll(result.restList);
		}
		if (result.welfareList != null) {
			girlList.addAll(result.welfareList);
		}
		if (result.recommendList != null) {
			girlList.addAll(result.recommendList);
		}
		if (result.resourceList != null) {
			girlList.addAll(result.resourceList);
		}
		if (result.androidList != null) {
			girlList.addAll(result.androidList);
		}
		if (result.iOSList != null) {
			girlList.addAll(result.iOSList);
		}
		return girlList;
	}

	private void getData(final boolean isCleanList) {
		BruceFactory.getSingLeton().getListData(mPage, new Callback<FuliList>() {
			@Override
			public void success(FuliList fuliList, Response response) {
				Log.e(TAG,"getListData success:"+ response);
				if(fuliList != null){
					if(isCleanList){
						mGirlList.clear();
					} else {

					}
					mGirlList.addAll(fuliList.getResults());
					mbtAdapter.notifyDataSetChanged();
					setRefreshing(false);
					mDaoHelper.addGirlList(fuliList.getResults());
				}
			}

			@Override
			public void failure(RetrofitError error) {
				switch (error.getKind()){
					case NETWORK:
						break;
					case CONVERSION:
						break;
					case HTTP:
						break;
					case UNEXPECTED:
						break;
				}
				Log.e(TAG,"getListData failure:"+ error.getMessage());
			}
		});
//		BruceFactory.getSingLeton().getResponseData(2, new Callback<Response>() {
//			@Override
//			public void success(Response response, Response response2) {
//				String s = new String(((TypedByteArray)response.getBody()).getBytes());
//				Log.e(TAG,"getResponseData success:"+ s);
//			}
//
//			@Override
//			public void failure(RetrofitError error) {
//
//			}
//		});
	}
	RecyclerView.OnScrollListener getScrollToBottomListener(final StaggeredGridLayoutManager staggeredGridLayoutManager){
		return new RecyclerView.OnScrollListener() {

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//				super.onScrolled(recyclerView, dx, dy);
				Boolean isBottom = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1] >=
						mbtAdapter.getItemCount() - PRELOAD_SIZE;
				if(!mSwipeRefreshLayout.isRefreshing() && isBottom){
					if (!mIsFirstTimeTouchBottom) {
						mSwipeRefreshLayout.setRefreshing(true);
						mPage++;
						getData(false);
					}
				} else {
					mIsFirstTimeTouchBottom = false;
				}
			}

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
			}
		};
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mPage = 1;
	}

	@Override
	public void onTouch(View view, View baseView, View imageView, Girl girl) {
		startActivity(new Intent(this,CoordinatorActivity2.class));
	}

	@Override
	public void onLongClick() {
//		startActivity(new Intent(this,CoordinatorActivity2.class));
		getDataOb();
	}
}
