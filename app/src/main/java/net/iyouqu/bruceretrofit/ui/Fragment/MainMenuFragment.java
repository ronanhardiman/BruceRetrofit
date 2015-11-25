package net.iyouqu.bruceretrofit.ui.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.iyouqu.bruceretrofit.Bean.MenuItem;
import net.iyouqu.bruceretrofit.R;
import net.iyouqu.bruceretrofit.ui.HomeActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by q on 2015/11/24.
 */
public class MainMenuFragment extends Fragment {
	@Bind(R.id.recycler_view)
	RecyclerView mRecyclerView;
	private LinearLayoutManager mLayoutManager;
	private MenuAdapter mAdapter;
	private MenuItem.FragmentType currentFragment = MenuItem.FragmentType.FreshNews;
	private HomeActivity mHomeActivity;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof HomeActivity) {
			mHomeActivity = (HomeActivity) context;
		} else {
			throw new IllegalArgumentException("The activity must be a HomeActivity !");
		}
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_drawer, container, false);
		ButterKnife.bind(this, view);
		mLayoutManager = new LinearLayoutManager(getActivity());
		mRecyclerView.setLayoutManager(mLayoutManager);
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mAdapter = new MenuAdapter();
		addAllMenuItems(mAdapter);
		mRecyclerView.setAdapter(mAdapter);
	}

	private void addAllMenuItems(MenuAdapter mAdapter) {
		mAdapter.menuItems.clear();
		mAdapter.menuItems.add(new MenuItem("新鲜事", R.drawable.ic_explore_white_24dp, MenuItem.FragmentType.FreshNews, CoordinatorFragment.class));
		mAdapter.menuItems.add(new MenuItem("无聊图", R.drawable.ic_explore_white_24dp, MenuItem.FragmentType.BoringPicture, DataSetFragment.class));
		mAdapter.menuItems.add(new MenuItem("妹子图", R.drawable.ic_explore_white_24dp, MenuItem.FragmentType.Sister, CoordinatorFragment.class));
		mAdapter.menuItems.add(new MenuItem("段子", R.drawable.ic_explore_white_24dp, MenuItem.FragmentType.Joke, CoordinatorFragment.class));
		mAdapter.menuItems.add(new MenuItem("小电影", R.drawable.ic_explore_white_24dp, MenuItem.FragmentType.Video, CoordinatorFragment.class));
	}

	private class MenuAdapter extends RecyclerView.Adapter<ViewHolder> {

		private ArrayList<MenuItem> menuItems;

		public MenuAdapter() {
			menuItems = new ArrayList<>();
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item, parent, false);
			return new ViewHolder(view);
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, int position) {
			final MenuItem menuItem = menuItems.get(position);

			holder.tv_title.setText(menuItem.getTitle());
			holder.img_menu.setImageResource(menuItem.getResourceId());
			holder.rl_container.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					try {
						if (currentFragment != menuItem.getType()) {
							Fragment fragment = (Fragment) Class.forName(menuItem.getFragment().getName()).newInstance();
							mHomeActivity.replaceFragment(R.id.frame_container, fragment);
							currentFragment = menuItem.getType();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					mHomeActivity.closeDrawer();
				}
			});
		}

		@Override
		public int getItemCount() {
			return menuItems.size();
		}
	}

	private static class ViewHolder extends RecyclerView.ViewHolder {

		private ImageView img_menu;
		private TextView tv_title;
		private RelativeLayout rl_container;


		public ViewHolder(View itemView) {
			super(itemView);
			img_menu = (ImageView) itemView.findViewById(R.id.img_menu);
			tv_title = (TextView) itemView.findViewById(R.id.tv_title);
			rl_container = (RelativeLayout) itemView.findViewById(R.id.rl_container);
		}
	}
}
