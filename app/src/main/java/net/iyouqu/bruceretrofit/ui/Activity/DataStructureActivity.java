package net.iyouqu.bruceretrofit.ui.Activity;

import android.os.Bundle;

import net.iyouqu.bruceretrofit.R;
import net.iyouqu.bruceretrofit.data.Queue;
import net.iyouqu.bruceretrofit.data.Stack;
import net.iyouqu.bruceretrofit.ui.Base.BaseActivity;

/**
 * Created by q on 2016/2/4.
 */
public class DataStructureActivity extends BaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.datastructure_layout);
		Queue.main(null);
		Stack.main(null);
	}
}
