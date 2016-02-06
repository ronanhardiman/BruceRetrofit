package net.iyouqu.bruceretrofit.ui.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.iyouqu.bruceretrofit.Bean.DataSet;
import net.iyouqu.bruceretrofit.Bean.Girl;
import net.iyouqu.bruceretrofit.R;
import net.iyouqu.bruceretrofit.network.BruceFactory;
import net.iyouqu.bruceretrofit.ui.Activity.MainActivity;
import net.iyouqu.bruceretrofit.util.StringUtil;
import net.iyouqu.bruceretrofit.util.disk.DiskLruCacheHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by q on 2015/11/24.
 */
public class DataSetFragment extends Fragment {

	private static final String TAG = DataSetFragment.class.getSimpleName();
	private static final String ARG_YEAR = "year";
	private static final String ARG_MONTH = "month";
	private static final String ARG_DAY = "day";

	@Bind(R.id.rv_view)
	RecyclerView mRecyclerView;
	private DataAdapter mDataAdapter;
	private List<Girl> mGirlList = new ArrayList<>();
	private int mYear, mMonth, mDay;

	public static DataSetFragment newInstance(int year,int month,int day){
		DataSetFragment dataSetFragment = new DataSetFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(ARG_YEAR,year);
		bundle.putInt(ARG_MONTH,month);
		bundle.putInt(ARG_DAY,day);
		dataSetFragment.setArguments(bundle);
		return dataSetFragment;
	}

	public DataSetFragment() {

	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDataAdapter = new DataAdapter(mGirlList);
		initArguments();
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.data_layout, container, false);
		ButterKnife.bind(this, view);
		initView();
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		loadData();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private void initArguments() {
		Bundle arguments = getArguments();
		if (arguments != null) {
			this.mYear = arguments.getInt(ARG_YEAR);
			this.mMonth = arguments.getInt(ARG_MONTH);
			this.mDay = arguments.getInt(ARG_DAY);
		}else {
			this.mYear = 2015;
			this.mMonth = 11;
			this.mDay = 23;
		}
	}

	private void initView() {
		LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
		mRecyclerView.setLayoutManager(layoutManager);
		mRecyclerView.setAdapter(mDataAdapter);
	}

	private void loadData(){
		BruceFactory.getSingLeton().getDataSet(mYear, mMonth, mDay, new Callback<DataSet>() {
			@Override
			public void success(DataSet dataSet, Response response) {
				Log.e(TAG, "getDataSet success:" + response);
				if (dataSet != null) {
					mGirlList.clear();
					initList(dataSet.results);
					mDataAdapter.notifyDataSetChanged();
					try {
						DiskLruCacheHelper diskLruCacheHelper = new DiskLruCacheHelper(getContext());
						diskLruCacheHelper.put("dataSet",dataSet);

						DataSet dataSet1 = diskLruCacheHelper.getAsSerializable("dataSet");
						if (dataSet1 != null) {
							Log.e(TAG,"dataSet1:"+dataSet1.category.size());
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void failure(RetrofitError error) {
				Log.e(TAG,"getDataSet failure:"+ error.getMessage());
			}
		});
	}

	private void initList(DataSet.Result results) {
		if(results.androidList != null) mGirlList.addAll(results.androidList);
		if(results.iOSList != null) mGirlList.addAll(results.iOSList);
		if(results.recommendList != null) mGirlList.addAll(results.recommendList);
		if(results.resourceList != null) mGirlList.addAll(results.resourceList);
		if(results.restList != null) mGirlList.addAll(results.restList);
		if(results.welfareList != null) mGirlList.addAll(results.welfareList);
	}


	public class DataAdapter extends RecyclerView.Adapter<ViewHolder> {
		private List<Girl> mGrilList;

		public DataAdapter(List<Girl> girlList) {
			this.mGrilList = girlList;
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gank, parent, false);
			return new ViewHolder(view);
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			Girl girl = mGrilList.get(position);
			if (position == 0) {
				showCategory(holder);
			}else {
				boolean theCategoryOfLastEqualsToThis = mGrilList.get(
						position - 1).type.equals(mGrilList.get(position).type);
				if (!theCategoryOfLastEqualsToThis) {
					showCategory(holder);
				} else {
					hideCategory(holder);
				}
			}
			holder.tv_category.setText(girl.type);
			SpannableStringBuilder builder = new SpannableStringBuilder(girl.desc).append(
					StringUtil.format(holder.tv_title.getContext(), " (via. " +
							girl.who +
							")", R.style.ViaTextAppearance));
			CharSequence gankText = builder.subSequence(0, builder.length());

			holder.tv_title.setText(gankText);
		}

		@Override
		public int getItemCount() {
			return mGrilList.size();
		}
	}

	private void showCategory(ViewHolder holder) {
		if (!isVisibleOf(holder.tv_category)) holder.tv_category.setVisibility(View.VISIBLE);
	}


	private void hideCategory(ViewHolder holder) {
		if (isVisibleOf(holder.tv_category)) holder.tv_category.setVisibility(View.GONE);
	}


	/**
	 * view.isShown() is a kidding...
	 */
	private boolean isVisibleOf(View view) {
		return view.getVisibility() == View.VISIBLE;
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		@Bind(R.id.tv_category)
		TextView tv_category;
		@Bind(R.id.tv_title)
		TextView tv_title;

		public ViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this,itemView);
		}

		@OnClick(R.id.ll_parent)
		void onItemClick(View v) {
			v.getContext().startActivity(new Intent(v.getContext(), MainActivity.class));
			Toast.makeText(v.getContext(), "ll_parent onClick", Toast.LENGTH_SHORT).show();
		}
	}
}
