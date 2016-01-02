package net.iyouqu.bruceretrofit.ui.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.iyouqu.bruceretrofit.R;

import butterknife.ButterKnife;

/**
 */
public class LikeAnimationFragment extends Fragment implements View.OnClickListener{

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.animation_layout, container, false);
		ButterKnife.bind(this,view);
		initView();
		return view;
	}

	private void initView() {
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onClick(View v) {

	}
}
