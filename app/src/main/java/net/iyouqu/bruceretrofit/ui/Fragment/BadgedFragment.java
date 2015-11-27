package net.iyouqu.bruceretrofit.ui.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.iyouqu.bruceretrofit.R;
import net.iyouqu.bruceretrofit.widget.badgedview.BadgedFourThreeImageView;
import net.iyouqu.bruceretrofit.widget.badgedview.BadgedSquareImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by q on 2015/11/26.
 */
public class BadgedFragment extends Fragment implements View.OnClickListener{
	@Bind(R.id.badged_view)
	BadgedFourThreeImageView badgedFourThreeImageView;
	@Bind(R.id.badged_square_view)
	BadgedSquareImageView badgedSquareImageView;
	@Bind(R.id.badged_square_view2)
	BadgedSquareImageView badgedSquareImageView2;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.badged_layout, container, false);
		ButterKnife.bind(this,view);
		initView();
		return view;
	}

	private void initView() {
		badgedFourThreeImageView.showBadge(true);
		badgedSquareImageView.showBadge(true);
		badgedSquareImageView.setOnClickListener(this);
		badgedSquareImageView2.showBadge(true);
		badgedSquareImageView2.setBadgeText("JSON");
		badgedSquareImageView2.setBadgeColor(getResources().getColor(R.color.color_red_FF4081));
		badgedSquareImageView2.setOnClickListener(this);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.badged_square_view2:
				if (badgedSquareImageView2.isBadgeVisible()) {
					badgedSquareImageView2.showBadge(false);
				}else {
					badgedSquareImageView2.showBadge(true);
				}

//				Glide.get(getContext()).clearDiskCache();
				break;
			default:
		}
	}
}
