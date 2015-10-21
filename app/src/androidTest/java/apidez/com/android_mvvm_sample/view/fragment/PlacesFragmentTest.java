package apidez.com.android_mvvm_sample.view.fragment;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import apidez.com.android_mvvm_sample.R;
import apidez.com.android_mvvm_sample.application.DemoApplication;
import apidez.com.android_mvvm_sample.dependency.component.DaggerTestComponent;
import apidez.com.android_mvvm_sample.dependency.component.TestComponent;
import apidez.com.android_mvvm_sample.dependency.module.StubPlacesModule;
import apidez.com.android_mvvm_sample.view.activity.EmptyActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static apidez.com.android_mvvm_sample.utils.MatcherEx.hasMaxLines;

/**
 * Created by nongdenchet on 10/21/15.
 */
@SmallTest
@RunWith(JUnit4.class)
public class PlacesFragmentTest {

    @Rule
    public ActivityTestRule<EmptyActivity> activityTestRule =
            new ActivityTestRule<>(EmptyActivity.class, true, false);

    @Before
    public void setUp() throws Exception {
        // Set up the application
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        DemoApplication app = (DemoApplication) instrumentation.getTargetContext().getApplicationContext();

        // Setup test component
        TestComponent component = DaggerTestComponent.builder()
                .stubPlacesModule(new StubPlacesModule())
                .build();
        app.setComponent(component);

        activityTestRule.launchActivity(new Intent());
        activityTestRule.getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, PlacesFragment.newInstance())
                .commit();
    }

    @Test
    public void maxLinesOfTextView() throws Exception {
        onView(withId(R.id.name)).check(matches(isDisplayed()));
        onView(withId(R.id.name)).check(matches(hasMaxLines(2)));
    }
}