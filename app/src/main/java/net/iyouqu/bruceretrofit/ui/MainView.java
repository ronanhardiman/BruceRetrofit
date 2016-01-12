package net.iyouqu.bruceretrofit.ui;

import java.util.List;

/**
 * Created by q on 2016/1/9.
 */
public interface MainView<T> extends SwipeRefreshView{

	void fillData(List<T> data);

	void appendMoreData(List<T> data);

	void hasNoMoreData();

	void showChangeLogInfo(String info);
}
