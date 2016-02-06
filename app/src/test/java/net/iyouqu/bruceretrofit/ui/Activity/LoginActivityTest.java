package net.iyouqu.bruceretrofit.ui.Activity;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;

import net.iyouqu.bruceretrofit.BuildConfig;
import net.iyouqu.bruceretrofit.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by q on 2016/2/6.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class,sdk = 21)
public class LoginActivityTest {
	private EditText emailView;
	private EditText passwordView;
	private Button button;
	private Activity activity;

	@Before
	public void setUp() {
		activity = Robolectric.setupActivity(LoginActivity.class);
		button = (Button) activity.findViewById(R.id.email_sign_in_button);
		emailView = (EditText) activity.findViewById(R.id.email);
		passwordView = (EditText) activity.findViewById(R.id.password);
	}

	@Test
	public void loginSuccess() {
		emailView.setText("foo@example.com");
		passwordView.setText("foo");
		button.performClick();

		ShadowApplication application = shadowOf(RuntimeEnvironment.application);
		assertThat("Next activity has started", application.getNextStartedActivity(), is(notNullValue()));
	}

	@Test
	public void testOnCreate() throws Exception {

	}
}