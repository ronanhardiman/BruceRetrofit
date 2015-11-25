package net.iyouqu.bruceretrofit.ui.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.iyouqu.bruceretrofit.R;

/**
 * Created by q on 2015/11/13.
 */
public class MyFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener {
	private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
	private boolean mIsAvatarShown = true;

//	private CircularImageView mProfileImage;
	private ImageView materialup_profile_imageview;
	private int mMaxScrollSize;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.my_fragment_layout,container,false);
		CollapsingToolbarLayout main_collapsing = (CollapsingToolbarLayout) view.findViewById(R.id.main_collapsing);

		TabLayout tabLayout = (TabLayout) view.findViewById(R.id.materialup_tabs);
		ViewPager viewPager  = (ViewPager) view.findViewById(R.id.materialup_viewpager);
		AppBarLayout appbarLayout = (AppBarLayout) view.findViewById(R.id.materialup_appbar);
//		mProfileImage = (CircularImageView) view.findViewById(R.id.materialup_profile_image);
		materialup_profile_imageview = (ImageView) view.findViewById(R.id.materialup_profile_imageview);
		Toolbar toolbar = (Toolbar) view.findViewById(R.id.materialup_toolbar);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				onBackPressed();
			}
		});

		appbarLayout.addOnOffsetChangedListener(this);
		mMaxScrollSize = appbarLayout.getTotalScrollRange();

		viewPager.setAdapter(new TabsAdapter(getActivity().getSupportFragmentManager()));
		tabLayout.setupWithViewPager(viewPager);

		return view;
	}

	@Override
	public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
		if (mMaxScrollSize == 0)
			mMaxScrollSize = appBarLayout.getTotalScrollRange();

		int percentage = (Math.abs(i)) * 100 / mMaxScrollSize;

		if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
			mIsAvatarShown = false;
			materialup_profile_imageview.animate().scaleY(0).scaleX(0).setDuration(200).start();
		}

		if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
			mIsAvatarShown = true;

			materialup_profile_imageview.animate()
					.scaleY(1).scaleX(1)
					.start();
		}
	}

	class TabsAdapter extends FragmentPagerAdapter {
		public TabsAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public Fragment getItem(int i) {
			switch(i) {
//				case 0: return new ConcernedFragment();
//				case 1: return new HistoryFragment();
				case 0: return MaterialUpConceptFakePage.newInstance();
				case 1: return MaterialUpConceptFakePage.newInstance();
			}
			return null;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch(position) {
				case 0: return "Tab 1";
				case 1: return "Tab 2";
			}
			return "";
		}
	}

}
