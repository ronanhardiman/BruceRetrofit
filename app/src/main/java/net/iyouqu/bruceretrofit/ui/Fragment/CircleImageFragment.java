package net.iyouqu.bruceretrofit.ui.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.iyouqu.bruceretrofit.R;
import net.iyouqu.bruceretrofit.network.BruceFactory;
import net.iyouqu.bruceretrofit.util.glide.CircleTransform;
import net.iyouqu.bruceretrofit.widget.badgedview.BadgedFourThreeImageView;
import net.iyouqu.bruceretrofit.widget.badgedview.BadgedSquareImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by q on 2015/11/26.
 */
public class CircleImageFragment extends Fragment{

	@Bind(R.id.badged_view)
	BadgedFourThreeImageView badgedFourThreeImageView;
	@Bind(R.id.badged_square_view)
	BadgedSquareImageView badgedSquareImageView;
	@Bind(R.id.badged_square_view2)
	BadgedSquareImageView badgedSquareImageView2;
	@Bind(R.id.badged_square_view3)
	BadgedSquareImageView badgedSquareImageView3;
	@Bind(R.id.badged_square_view4)
	BadgedSquareImageView badgedSquareImageView4;

	private CircleTransform circleCrop;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.badged_layout, container, false);
		ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		circleCrop = new CircleTransform(getContext());
		Glide.with(this)
				.load(BruceFactory.image_urllist[0])
				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.into(badgedFourThreeImageView)
				;
		Glide.with(getContext())
				.load(BruceFactory.image_urllist[1])
				.transform(circleCrop)
				.into(badgedSquareImageView3);

		Glide.with(getContext())
				.load(BruceFactory.image_urllist[3])
				.placeholder(R.drawable.dog)
				.transform(circleCrop)
				.into(badgedSquareImageView4);
	}
}
