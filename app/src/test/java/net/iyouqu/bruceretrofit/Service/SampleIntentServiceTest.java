package net.iyouqu.bruceretrofit.Service;

import android.content.Context;
import android.content.Intent;

import junit.framework.Assert;

import net.iyouqu.bruceretrofit.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboSharedPreferences;

import static org.junit.Assert.assertNotSame;

/**
 * Created by q on 2016/2/6.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class,sdk=21)
public class SampleIntentServiceTest {

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void addsDataToSharedPreference(){
		RoboSharedPreferences preferences = (RoboSharedPreferences) RuntimeEnvironment.application
				.getSharedPreferences("example", Context.MODE_PRIVATE);
		Intent intent =  new Intent(RuntimeEnvironment.application,SampleIntentService.class);
		SampleIntentService registrationService = new SampleIntentService();

		registrationService.onHandleIntent(intent);

		assertNotSame(preferences.getString("SAMPLE_DATA", ""), "");
		Assert.assertEquals(preferences.getString("SAMPLE_DATA", ""), "sample data");
	}

	@Test
	public void testOnHandleIntent() throws Exception {

	}
}