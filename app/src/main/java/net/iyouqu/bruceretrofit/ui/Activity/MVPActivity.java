package net.iyouqu.bruceretrofit.ui.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import net.iyouqu.bruceretrofit.Bean.Girl;
import net.iyouqu.bruceretrofit.Presenter.MainPresenter;
import net.iyouqu.bruceretrofit.R;
import net.iyouqu.bruceretrofit.adapter.BtAdapter;
import net.iyouqu.bruceretrofit.ui.MainView;

import java.util.Date;
import java.util.List;

import butterknife.Bind;

/**
 * Created by q on 2016/1/10.
 */
public class MVPActivity extends BaseSwipeRefreshActivity<MainPresenter> implements MainView<Girl>{

	private static final String EXTRA_BUNDLE_GANK = "BUNDLE_GANK";
	private static final String EXTRA_BUNDLE_LOAD_MORE = "BUNDLE_LOAD_MORE";

	private static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";
	private static final String VIEW_NAME_HEADER_TITLE = "detail:header:title";

	@Bind(R.id.recycleview_main)
	RecyclerView mRecyclerView;
	private BtAdapter mbtAdapter;
	private static final int PRELOAD_SIZE = 6;
	/**
	 * the flag of has more data or not
	 */
	private boolean mHasMoreData = true;

	/**
	 * the flag to district whether scroll bottom load more data or not
	 * default is load more data
	 */
	boolean isLoadMore = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}

	@Override
	public void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// make swipeRefreshLayout visible manually
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				showRefresh();
			}
		}, 568);
		getData();
	}

	private void initView() {
		final StaggeredGridLayoutManager layoutManager =
				new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
		mRecyclerView.setLayoutManager(layoutManager);
		mbtAdapter = new BtAdapter(this);
		mRecyclerView.setAdapter(mbtAdapter);
		mRecyclerView.addOnScrollListener(getScrollToBottomListener(layoutManager));
//		mbtAdapter.setOnBtTouchListener(this);
//		mbtAdapter.setOnLongClickListener(this);
	}

	/**
	 *  if the getIntent() contains Gank entity, it indicates this activity is used to show one Day gank info
	 */
	private void prepareShowGankDetailView(){

	}

	private void getData() {
		Girl girl = (Girl) getIntent().getSerializableExtra(EXTRA_BUNDLE_GANK);
		if (girl == null) {
			mPresenter.getData(new Date(System.currentTimeMillis()));
		}else {
//			mPresenter.getData(girl.publishedAt);
		}
	}

	RecyclerView.OnScrollListener getScrollToBottomListener(final StaggeredGridLayoutManager staggeredGridLayoutManager){
		return new RecyclerView.OnScrollListener() {

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				//				super.onScrolled(recyclerView, dx, dy);
				Boolean isBottom = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1] >=
						mbtAdapter.getItemCount() - PRELOAD_SIZE;
				if(!mSwipeRefreshLayout.isRefreshing() && isBottom && mHasMoreData && isLoadMore){
					showRefresh();
					mPresenter.getDataMore();
				} else if(!mHasMoreData){
					hasNoMoreData();
				}
			}

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
			}
		};
	}

	@Override
	protected boolean prepareRefresh() {
		return mPresenter.shouldRefillData();
	}

	@Override
	public void fillData(List<Girl> data) {
		mbtAdapter.setDataAfterClear(data);
	}

	@Override
	public void appendMoreData(List<Girl> data) {
		mbtAdapter.setData(data);
	}

	@Override
	public void hasNoMoreData() {
		mHasMoreData = false;
	}

	@Override
	public void showChangeLogInfo(String info) {

	}

	@Override
	protected void initPresenter() {
		mPresenter = new MainPresenter(this);
	}

	@Override
	protected int getLayout() {
		return R.layout.activity_main;
	}

	@Override
	public void getDataFinish() {
		hideRefresh();
	}

	@Override
	public void showEmptyView() {

	}

	@Override
	public void showErrorView(Throwable throwable) {

	}

	@Override
	protected void requestDataRefresh() {
		getData();
	}
}
