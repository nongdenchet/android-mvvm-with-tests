package apidez.com.android_mvvm_sample.view.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.MediumTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import apidez.com.android_mvvm_sample.R;
import apidez.com.android_mvvm_sample.application.DemoApplication;
import apidez.com.android_mvvm_sample.dependency.component.DaggerTestComponent;
import apidez.com.android_mvvm_sample.dependency.component.TestComponent;
import apidez.com.android_mvvm_sample.dependency.module.StubPurchaseModule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static apidez.com.android_mvvm_sample.util.MatcherEx.hasListener;
import static apidez.com.android_mvvm_sample.util.MatcherEx.hasResId;
import static org.hamcrest.Matchers.not;

/**
 * Created by nongdenchet on 10/2/15.
 */

/**
 * Test the UI implementation
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class PurchaseActivityTest {

    @Rule
    public ActivityTestRule<PurchaseActivity> activityTestRule =
            new ActivityTestRule<>(PurchaseActivity.class, true, false);

    @Before
    public void setUp() throws Exception {
        // Set up the application
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        DemoApplication app = (DemoApplication) instrumentation.getTargetContext().getApplicationContext();

        // Setup test component
        TestComponent component = DaggerTestComponent.builder()
                .stubPurchaseModule(new StubPurchaseModule())
                .build();
        app.setComponent(component);
        component.inject(this);

        // Run the activity
        activityTestRule.launchActivity(new Intent());
    }

    @Test
    public void hasNoErrorCreditCard() throws Exception {
        onView((withId(R.id.creditCard))).perform(typeText("I am"));
        onView(withText(R.string.error_credit_card)).check(doesNotExist());
    }

    @Test
    public void hasErrorCreditCard() throws Exception {
        onView(withId(R.id.creditCard)).perform(typeText("age"));
        onView(withText(R.string.error_credit_card)).check(matches(isDisplayed()));
    }

    @Test
    public void hasNoErrorEmail() throws Exception {
        onView(withId(R.id.email)).perform(typeText("I am"));
        onView(withText(R.string.error_email)).check(doesNotExist());
    }

    @Test
    public void hasErrorEmail() throws Exception {
        onView(withId(R.id.email)).perform(typeText("age"));
        onView(withText(R.string.error_email)).check(matches(isDisplayed()));
    }

    @Test
    public void cannotSubmitCreditCard() throws Exception {
        onView(withId(R.id.creditCard)).perform(typeText("123"));
        onView(withId(R.id.email)).perform(typeText("1234"));
        onView(withId(R.id.btnSubmit)).check(matches(not(hasListener())));
        onView(withId(R.id.btnSubmit)).check(matches(hasResId(R.drawable.bg_inactive_submit)));
    }

    @Test
    public void cannotSubmitEmail() throws Exception {
        onView(withId(R.id.email)).perform(typeText("123"));
        onView(withId(R.id.creditCard)).perform(typeText("1234"));
        onView(withId(R.id.btnSubmit)).check(matches(not(hasListener())));
        onView(withId(R.id.btnSubmit)).check(matches(hasResId(R.drawable.bg_inactive_submit)));
    }

    @Test
    public void canSubmit() throws Exception {
        onView(withId(R.id.email)).perform(typeText("I am"));
        onView(withId(R.id.creditCard)).perform(typeText("I am"));
        onView(withId(R.id.btnSubmit)).check(matches(hasListener()));
        onView(withId(R.id.btnSubmit)).check(matches(hasResId(R.drawable.bg_submit)));
        onView(withId(R.id.btnSubmit)).perform(click());
    }

    // Uncomment to run test with life-cycle
    // @Test
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void testImpl() throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);
        activityTestRule.getActivity().getApplication().registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (activityTestRule.getActivity() == activity) {
                    latch.countDown();
                    activityTestRule.getActivity().getApplication().unregisterActivityLifecycleCallbacks(this);
                }
            }
        });
        latch.await();
    }
}