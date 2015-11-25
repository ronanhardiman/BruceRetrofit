package net.iyouqu.bruceretrofit.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.LinearLayout;

import net.iyouqu.bruceretrofit.R;
import net.iyouqu.bruceretrofit.ui.Fragment.CoordinatorFragment;
import net.iyouqu.bruceretrofit.ui.Fragment.MyFragment;

/**
 * Created by q on 2015/11/13.
 */
public class CoordinatorActivity2 extends AppCompatActivity{

	LinearLayout coordinator_layout;
	FragmentManager supportFragmentManager;
	private CoordinatorFragment mCoordinatorFragment;
	private MyFragment myFragment;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coordinator_activity_layout);
		initView();

		supportFragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
//		hideFragment(fragmentTransaction);
		showCoordinatorFragment2(fragmentTransaction);
		fragmentTransaction.commitAllowingStateLoss();

	}

	private void showCoordinatorFragment2(FragmentTransaction fragmentTransaction) {
		if (myFragment == null) {
			myFragment = new MyFragment();
			fragmentTransaction.add(R.id.coordinator_content,myFragment,"mCoordinatorFragment");
		}else {
			fragmentTransaction.show(myFragment);
		}
	}

	private void showCoordinatorFragment(FragmentTransaction fragmentTransaction) {
		if (mCoordinatorFragment == null) {
			mCoordinatorFragment = new CoordinatorFragment();
			fragmentTransaction.add(R.id.coordinator_content,mCoordinatorFragment,"mCoordinatorFragment");
		}else {
			fragmentTransaction.show(mCoordinatorFragment);
		}
	}

	private void hideFragment(FragmentTransaction fragmentTransaction) {
		if (mCoordinatorFragment != null) {
			fragmentTransaction.hide(mCoordinatorFragment);
		}
	}

	private void initView() {
		coordinator_layout = (LinearLayout) findViewById(R.id.coordinator_layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}


}
