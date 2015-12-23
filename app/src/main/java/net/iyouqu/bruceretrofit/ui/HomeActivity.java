package net.iyouqu.bruceretrofit.ui;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import net.iyouqu.bruceretrofit.R;
import net.iyouqu.bruceretrofit.ui.Fragment.CoordinatorFragment;
import net.iyouqu.bruceretrofit.ui.Fragment.MainMenuFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by q on 2015/11/23.
 */
public class HomeActivity extends AppCompatActivity{
	@Bind(R.id.drawer)
	DrawerLayout drawer;
	@Bind(R.id.toolbar)
	Toolbar mToolbar;
	private ActionBarDrawerToggle mActionBarDrawerToggle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		ButterKnife.bind(this);

		initView();
//		ThreadPoolUtils.getInstance(this).init();
	}

	private void initView() {
		mToolbar.setTitleTextColor(Color.WHITE);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mActionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, mToolbar, R.string.app_name,
				R.string.app_name) {
			@Override
			public void onDrawerClosed(View drawerView) {
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu();
			}
		};

		mActionBarDrawerToggle.syncState();
		drawer.setDrawerListener(mActionBarDrawerToggle);
		replaceFragment(R.id.frame_container,new CoordinatorFragment());
		replaceFragment(R.id.drawer_container,new MainMenuFragment());

		drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
			}

			@Override
			public void onDrawerOpened(View drawerView) {
			}

			@Override
			public void onDrawerClosed(View drawerView) {
			}

			@Override
			public void onDrawerStateChanged(int newState) {
			}
		});
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mActionBarDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mActionBarDrawerToggle.onConfigurationChanged(newConfig);
	}

	public void closeDrawer() {
		drawer.closeDrawers();
	}

	public void replaceFragment(int frame_container, Fragment fragment) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.replace(frame_container, fragment);
		transaction.commit();
	}
}
