package net.iyouqu.bruceretrofit.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.iyouqu.bruceretrofit.R;
import net.iyouqu.bruceretrofit.ui.Activity.MVPActivity;

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

	@OnClick(R.id.btn_mvp)
	void OnItemClick(View view){
		startActivity(new Intent(getContext(), MVPActivity.class));
	}
}
