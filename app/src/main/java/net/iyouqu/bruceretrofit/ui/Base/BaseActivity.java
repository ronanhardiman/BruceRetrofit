package net.iyouqu.bruceretrofit.ui.Base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import net.iyouqu.bruceretrofit.R;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * 基类 SwipeBack
 * Created by q on 2015/9/27.
 */
public class BaseActivity extends AppCompatActivity implements SwipeBackActivityBase {
	private SwipeBackActivityHelper mHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.slideright, 0);
		mHelper = new SwipeBackActivityHelper(this);
		mHelper.onActivityCreate();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mHelper.onPostCreate();
	}

	@Override
	public View findViewById(int id) {
		View v = super.findViewById(id);
		if (v == null && mHelper != null)
			return mHelper.findViewById(id);
		return v;
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(0, R.anim.fade_out);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public SwipeBackLayout getSwipeBackLayout() {
		return mHelper.getSwipeBackLayout();
	}

	@Override
	public void setSwipeBackEnable(boolean b) {
		getSwipeBackLayout().setEnableGesture(b);
	}

	@Override
	public void scrollToFinishActivity() {
		Utils.convertActivityToTranslucent(this);
		getSwipeBackLayout().scrollToFinishActivity();
	}
}
