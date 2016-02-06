package net.iyouqu.bruceretrofit.ui.Activity;

import net.iyouqu.bruceretrofit.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class HomeActivityTest {
	private HomeActivity homeActivity;
	@Before
	public void setUp() {
		homeActivity = Robolectric.setupActivity(HomeActivity.class);
	}

	@Test
	public void addition_isCorrect() throws Exception {
		assertEquals(4, 2 + 2);
	}
	@Test
	public void testHomeActivity() {
		HomeActivity homeActivity = Robolectric.setupActivity(HomeActivity.class);
		assertNotNull(homeActivity);
		assertEquals(homeActivity.getTitle(),"HomeActivity");
	}
}