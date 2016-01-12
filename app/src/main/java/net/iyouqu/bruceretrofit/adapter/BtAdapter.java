package net.iyouqu.bruceretrofit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SizeReadyCallback;

import net.iyouqu.bruceretrofit.Bean.Girl;
import net.iyouqu.bruceretrofit.R;
import net.iyouqu.bruceretrofit.interfacebt.OnBtTouchListener;
import net.iyouqu.bruceretrofit.interfacebt.OnLongClickListener;
import net.iyouqu.bruceretrofit.widget.RatioImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by q on 2015/9/27.
 */
public class BtAdapter extends RecyclerView.Adapter<BtAdapter.ViewHolder>{
	private static final String TAG = BtAdapter.class.getSimpleName();
	private Context mContext;
	private List<Girl> girlList = new ArrayList<>();
	private OnBtTouchListener onBtTouchListener;
	private OnLongClickListener onLongClickListener;
	public void setOnBtTouchListener(OnBtTouchListener onBtTouchListener){
		this.onBtTouchListener = onBtTouchListener;
	}

	public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
		this.onLongClickListener = onLongClickListener;
	}

	public BtAdapter(Context mContext) {
		this.mContext = mContext;
	}

	public BtAdapter(Context mContext,List<Girl> mList){
		this.mContext = mContext;
		this.girlList = mList;
	}
	@Override
	public BtAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bt_itemview,viewGroup,false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final BtAdapter.ViewHolder viewHolder, int i) {
		Girl girl = girlList.get(i);
		viewHolder.textView.setText(girl.who);
		viewHolder.girl = girl;
		Log.e(TAG,"girl.url:"+girl.url);
		Glide.with(mContext)
				.load(girl.url)
//				.diskCacheStrategy(DiskCacheStrategy.ALL)
				.centerCrop()
				.into(viewHolder.ratioImageView)
				.getSize(new SizeReadyCallback() {
					@Override
					public void onSizeReady(int width, int height) {
						if (!viewHolder.view.isShown()) {
							viewHolder.view.setVisibility(View.VISIBLE);
						}
					}
				});
	}

	@Override
	public int getItemCount() {
		return girlList.size();
	}

	public void setDataAfterClear(List<Girl> data) {
		girlList.clear();
		girlList.addAll(data);
		notifyDataSetChanged();
	}

	public void setData(List<Girl> data) {
		girlList.addAll(data);
		notifyDataSetChanged();
	}

	class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
		View view;
		TextView textView;
		RatioImageView ratioImageView;
		Girl girl;
		public ViewHolder(View itemView) {
			super(itemView);
			view = itemView.findViewById(R.id.linearlayout_card);
			textView = (TextView) itemView.findViewById(R.id.tv_title);
			ratioImageView = (RatioImageView) itemView.findViewById(R.id.iv_bt);
			ratioImageView.setOriginalSize(50,50);
			view.setOnClickListener(this);
			view.setOnLongClickListener(this);
//			ratioImageView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			if(onBtTouchListener != null){
				onBtTouchListener.onTouch(v, view, ratioImageView, girl);
			}
		}

		@Override
		public boolean onLongClick(View v) {
			if (onLongClickListener != null) {
				onLongClickListener.onLongClick();
			}
			return false;
		}
	}
}
