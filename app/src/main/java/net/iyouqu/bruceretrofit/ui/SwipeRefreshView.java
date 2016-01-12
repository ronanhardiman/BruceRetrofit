package net.iyouqu.bruceretrofit.ui;

import net.iyouqu.bruceretrofit.ui.view.BaseView;

/**
 * Created by q on 2016/1/9.
 */
public interface SwipeRefreshView extends BaseView{

	void getDataFinish();

	void showEmptyView();

	void showErrorView(Throwable throwable);

	void showRefresh();

	void hideRefresh();

}
