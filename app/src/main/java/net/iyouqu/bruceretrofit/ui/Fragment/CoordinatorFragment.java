package net.iyouqu.bruceretrofit.ui.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.iyouqu.bruceretrofit.R;

/**
 * Created by q on 2015/11/13.
 */
public class CoordinatorFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener {

	private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
	private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
	private static final int ALPHA_ANIMATIONS_DURATION              = 200;

	private boolean mIsTheTitleVisible          = false;
	private boolean mIsTheTitleContainerVisible = true;

	private LinearLayout mTitleContainer;
	private TextView mTitle;
	private AppBarLayout mAppBarLayout;
	private ImageView mImageparallax;
	private FrameLayout mFrameParallax;
	private Toolbar mToolbar;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.coordinator_fragment_layout, container, false);
		mToolbar        = (Toolbar) view.findViewById(R.id.main_toolbar);
		mTitle          = (TextView) view.findViewById(R.id.main_textview_title);
		mTitleContainer = (LinearLayout) view.findViewById(R.id.main_linearlayout_title);
		mAppBarLayout   = (AppBarLayout) view.findViewById(R.id.main_appbar);
		mImageparallax  = (ImageView) view.findViewById(R.id.main_imageview_placeholder);
		mFrameParallax  = (FrameLayout) view.findViewById(R.id.main_framelayout_title);

		mToolbar.setTitle("");
		mAppBarLayout.addOnOffsetChangedListener(this);

		((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
		startAlphaAnimation(mTitle, 0, View.INVISIBLE);
		initParallaxValues();
		return view;
	}



	private void initParallaxValues() {
		CollapsingToolbarLayout.LayoutParams petDetailsLp =
				(CollapsingToolbarLayout.LayoutParams) mImageparallax.getLayoutParams();

		CollapsingToolbarLayout.LayoutParams petBackgroundLp =
				(CollapsingToolbarLayout.LayoutParams) mFrameParallax.getLayoutParams();

		petDetailsLp.setParallaxMultiplier(0.9f);
		petBackgroundLp.setParallaxMultiplier(0.3f);

		mImageparallax.setLayoutParams(petDetailsLp);
		mFrameParallax.setLayoutParams(petBackgroundLp);
	}

	@Override
	public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
		int maxScroll = appBarLayout.getTotalScrollRange();
		float percentage = (float) Math.abs(offset) / (float) maxScroll;

		handleAlphaOnTitle(percentage);
		handleToolbarTitleVisibility(percentage);
	}

	private void handleToolbarTitleVisibility(float percentage) {
		if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

			if(!mIsTheTitleVisible) {
				startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
				mIsTheTitleVisible = true;
			}

		} else {

			if (mIsTheTitleVisible) {
				startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
				mIsTheTitleVisible = false;
			}
		}
	}

	private void handleAlphaOnTitle(float percentage) {
		if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
			if(mIsTheTitleContainerVisible) {
				startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
				mIsTheTitleContainerVisible = false;
			}

		} else {

			if (!mIsTheTitleContainerVisible) {
				startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
				mIsTheTitleContainerVisible = true;
			}
		}
	}

	public static void startAlphaAnimation (View v, long duration, int visibility) {
		AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
				? new AlphaAnimation(0f, 1f)
				: new AlphaAnimation(1f, 0f);

		alphaAnimation.setDuration(duration);
		alphaAnimation.setFillAfter(true);
		v.startAnimation(alphaAnimation);
	}

}
