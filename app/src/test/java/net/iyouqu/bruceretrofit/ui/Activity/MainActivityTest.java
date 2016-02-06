package net.iyouqu.bruceretrofit.ui.Activity;

import android.widget.TextView;

import junit.framework.Assert;

import net.iyouqu.bruceretrofit.BuildConfig;
import net.iyouqu.bruceretrofit.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 * Created by q on 2016/2/6.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class,sdk = 21)
public class MainActivityTest {

	private MainActivity mainActivity;
	private TextView textView;
	@Before
	public void setUp() throws Exception {
		// 获取需要测试的Activity
		mainActivity = Robolectric.setupActivity(MainActivity.class);
		// 初始化控件
		textView = (TextView) mainActivity.findViewById(R.id.textView);
		mainActivity.setTitle(R.string.app_name);
	}
	//测试初始化结果
	@Test
	public void textInitView() throws Exception{
		assertNotNull(mainActivity);
		assertNotNull(textView);
		// 判断包名
		assertEquals("net.iyouqu.bruceretrofit",mainActivity.getPackageName());
//		assertEquals("net.iyouqu.bruceretrofit.ui.Activity.MainActivity",mainActivity.getPackageName());
		assertEquals("Hello world!", textView.getText().toString());
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void titleIsCollect() throws Exception{
		Assert.assertTrue(mainActivity.getTitle().toString().equals("BruceRetrofit"));
	}

	@Test
	public void testFail()throws Exception {
	}

	@Test
	public void testOnCreate() throws Exception {

	}

	@Test
	public void testOnPostResume() throws Exception {

	}

	@Test
	public void testRequestDataRefresh() throws Exception {

	}

	@Test
	public void testSetRefreshing() throws Exception {

	}

	@Test
	public void testGetScrollToBottomListener() throws Exception {

	}

	@Test
	public void testOnCreateOptionsMenu() throws Exception {

	}

	@Test
	public void testOnOptionsItemSelected() throws Exception {

	}

	@Test
	public void testOnDestroy() throws Exception {

	}

	@Test
	public void testOnTouch() throws Exception {

	}

	@Test
	public void testOnLongClick() throws Exception {

	}
}