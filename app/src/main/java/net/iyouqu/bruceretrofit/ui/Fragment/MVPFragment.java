package net.iyouqu.bruceretrofit.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.iyouqu.bruceretrofit.R;
import net.iyouqu.bruceretrofit.ui.Activity.DataStructureActivity;
import net.iyouqu.bruceretrofit.ui.Activity.MVPActivity;
import net.iyouqu.bruceretrofit.ui.Activity.OptimizeActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by q on 2016/1/11.
 */
public class MVPFragment extends Fragment{

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mvp_layout, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@OnClick({R.id.btn_mvp,R.id.btn_optimize_background,R.id.btn_data_background})
	void OnItemClick(View view) {
		switch (view.getId()) {
			case R.id.btn_mvp:
				startActivity(new Intent(getContext(), MVPActivity.class));
				break;
			case R.id.btn_optimize_background:
				startActivity(new Intent(getContext(), OptimizeActivity.class));
				break;
			case R.id.btn_data_background:
				startActivity(new Intent(getContext(), DataStructureActivity.class));
				break;
		}
	}
}
