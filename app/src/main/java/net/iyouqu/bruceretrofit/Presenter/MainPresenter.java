package net.iyouqu.bruceretrofit.Presenter;

import net.iyouqu.bruceretrofit.Bean.DataSet;
import net.iyouqu.bruceretrofit.Bean.Girl;
import net.iyouqu.bruceretrofit.ui.MainView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by q on 2016/1/9.
 */
public class MainPresenter extends Presenter<MainView> {

	private static final int DAY_OF_MILLISECOND = 24*60*60*1000;
	private Date mCurrentDate;
	List<Girl> girlList = new ArrayList<>();
	private boolean hasLoadMoreData = false;

	private int mCountOfGetMoreDataEmpty = 0;

	public MainPresenter(MainView mView) {
		super(mView);
	}

	public void checkAutoUpdateByUmeng() {

	}

	//check version info ,if the version info has changed,we need pop a dialog to show change log info
	public void checkVersionInfo() {

	}

	/**
	 * @return
	 */
	public boolean shouldRefillData(){
		return !hasLoadMoreData;
	}

	public void getData(final Date date) {
		mCurrentDate = date;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		mBRUCE_API.getDataObservable(year,month,day)
				.observeOn(AndroidSchedulers.mainThread())
				.map(new Func1<DataSet, DataSet.Result>() {
					@Override
					public DataSet.Result call(DataSet dataSet) {
						return dataSet.results;
					}
				})
				.map(new Func1<DataSet.Result, List<Girl>>() {
					@Override
					public List<Girl> call(DataSet.Result result) {
						return addAllResults(result);
					}
				})
				.subscribe(new Subscriber<List<Girl>>() {
					@Override
					public void onCompleted() {
						// after get data complete, need put off time one day
						mCurrentDate = new Date(date.getTime()-DAY_OF_MILLISECOND);
					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onNext(List<Girl> girls) {
						// some day the data will be return empty like sunday, so we need get after day data
						if (girls.isEmpty()) {
							getData(new Date(date.getTime()-DAY_OF_MILLISECOND));
						} else {
							mCountOfGetMoreDataEmpty = 0;
							mView.fillData(girls);
						}
						mView.getDataFinish();
					}
				});
	}

	public void getDataMore() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(mCurrentDate);
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH)+1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		mBRUCE_API.getDataObservable(year, month, day)
		.observeOn(AndroidSchedulers.mainThread())
		.map(new Func1<DataSet, DataSet.Result>() {
			@Override
			public DataSet.Result call(DataSet dataSet) {
				return dataSet.results;
			}
		})
		.map(new Func1<DataSet.Result, List<Girl>>() {
			@Override
			public List<Girl> call(DataSet.Result result) {
				return addAllResults(result);
			}
		})
		.subscribe(new Subscriber<List<Girl>>() {
			@Override
			public void onCompleted() {
				// after get data complete, need put off time one day
				mCurrentDate = new Date(mCurrentDate.getTime()-DAY_OF_MILLISECOND);
				// now user has execute getMoreData so this flag will be set true
				//and now when user pull down list we would not refill data
				hasLoadMoreData = true;
			}

			@Override
			public void onError(Throwable e) {

			}

			@Override
			public void onNext(List<Girl> girls) {
				//when this day is weekend , the list will return empty(weekend has not gank info,the editors need rest)
				if (girls.isEmpty()) {
					//record count of empty day
					mCountOfGetMoreDataEmpty += 1;
					//if empty day is more than five,it indicate has no more data to show
					if(mCountOfGetMoreDataEmpty>=5){
						mView.hasNoMoreData();
					}else{
						// we need look forward data
						getDataMore();
					}
				} else {
					mCountOfGetMoreDataEmpty = 0;
					mView.appendMoreData(girls);
				}
				mView.getDataFinish();
			}
		})
		;
	}

	private List<Girl> addAllResults(DataSet.Result result) {
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
}
