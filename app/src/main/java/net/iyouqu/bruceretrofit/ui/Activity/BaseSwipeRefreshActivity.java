package net.iyouqu.bruceretrofit.ui.Activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

import net.iyouqu.bruceretrofit.Presenter.Presenter;
import net.iyouqu.bruceretrofit.R;
import net.iyouqu.bruceretrofit.ui.Base.BasePresenterActivity;
import net.iyouqu.bruceretrofit.ui.SwipeRefreshView;
import net.iyouqu.bruceretrofit.widget.MultiSwipeRefreshLayout;

import butterknife.Bind;

/**
 * Created by q on 2016/1/9.
 */
public abstract class BaseSwipeRefreshActivity<P extends Presenter> extends BasePresenterActivity<P> implements SwipeRefreshView{

	@Bind(R.id.swipe_refresh_layout)
	protected MultiSwipeRefreshLayout mSwipeRefreshLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initSwipeLayout();
	}

	private void initSwipeLayout() {
		if (mSwipeRefreshLayout != null) {
			mSwipeRefreshLayout.setColorSchemeResources(R.color.refresh_progress_3,
					R.color.refresh_progress_2, R.color.refresh_progress_1);
			// do not use lambda!!
			mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
				@Override public void onRefresh() {
					if (prepareRefresh()) {
						requestDataRefresh();
					}else {
						hideRefresh();
					}
				}
			});
		}
	}

	@Override
	public void hideRefresh() {
		if (mSwipeRefreshLayout == null) {
			return;
		}
		// 防止刷新消失太快，让子弹飞一会儿. do not use lambda!!
		mSwipeRefreshLayout.postDelayed(new Runnable() {
			@Override public void run() {
				mSwipeRefreshLayout.setRefreshing(false);
			}
		}, 1000);
	}

	@Override
	public void showRefresh() {
		mSwipeRefreshLayout.setRefreshing(true);
	}

	protected abstract void requestDataRefresh();

	/**
	 * check data status
	 * @return return true indicate it should load data really else indicate don't refresh
	 */
	protected boolean prepareRefresh(){
		return true;
	}


}
