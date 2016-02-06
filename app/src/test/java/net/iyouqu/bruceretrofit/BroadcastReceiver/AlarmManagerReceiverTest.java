package net.iyouqu.bruceretrofit.BroadcastReceiver;

import android.app.Application;
import android.content.Intent;

import net.iyouqu.bruceretrofit.BuildConfig;
import net.iyouqu.bruceretrofit.Service.SampleIntentService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by q on 2016/2/6.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class,sdk = 21)
public class AlarmManagerReceiverTest {
	//会 Error:(33, 25) Gradle: 错误: 无法访问AndroidHttpClient
	//找不到android.net.http.AndroidHttpClient的类文件 的错误
	//添加 android{useLibrary 'org.apache.http.legacy'} 解决
	@Test
	public void startServiceForTheScheduledAlarm(){
		Application application = RuntimeEnvironment.application;
		Intent expectedService = new Intent(application, SampleIntentService.class);

		AlarmManagerReceiver alarmManagerReceiver = new AlarmManagerReceiver();
		alarmManagerReceiver.onReceive(application, new Intent());

		Intent serviceIntent = shadowOf(application).getNextStartedService();
		assertNotNull(serviceIntent);
		assertEquals(serviceIntent.getComponent(),
				expectedService.getComponent());
	}

	@Test
	public void testOnReceive() throws Exception {

	}
}