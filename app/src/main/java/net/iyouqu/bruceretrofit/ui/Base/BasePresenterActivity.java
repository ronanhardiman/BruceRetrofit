package net.iyouqu.bruceretrofit.ui.Base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import net.iyouqu.bruceretrofit.Presenter.Presenter;

import butterknife.ButterKnife;

/**
 * Created by q on 2016/1/9.
 */
public abstract class BasePresenterActivity<P extends Presenter> extends AppCompatActivity {

	/**
	 * the presenter of this Activity
	 */
	protected P mPresenter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayout());
		ButterKnife.bind(this);
		initPresenter();
		checkPresenterIsNull();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				//don't use finish() and use onBackPressed() will be a good practice , trust me ! bg gu dong
				onBackPressed();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void checkPresenterIsNull() {
		if (mPresenter == null) {
			throw new IllegalStateException("please init mPresenter in initPresenter() method");
		}
	}

	protected abstract void initPresenter();

	protected abstract int getLayout();

}
