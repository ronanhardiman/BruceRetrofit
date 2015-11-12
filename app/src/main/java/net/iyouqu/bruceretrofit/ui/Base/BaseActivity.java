package net.iyouqu.bruceretrofit.ui.Base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 基类
 * Created by q on 2015/9/27.
 */
public class BaseActivity extends AppCompatActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
