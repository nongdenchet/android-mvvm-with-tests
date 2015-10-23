package apidez.com.android_mvvm_sample.view.fragment;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import apidez.com.android_mvvm_sample.R;
import apidez.com.android_mvvm_sample.api.PlacesApi;
import apidez.com.android_mvvm_sample.dependency.component.AppComponent;
import apidez.com.android_mvvm_sample.dependency.component.DaggerAppComponent;
import apidez.com.android_mvvm_sample.dependency.module.PlacesModule;
import apidez.com.android_mvvm_sample.stub.StubPlacesViewModel;
import apidez.com.android_mvvm_sample.utils.ApplicationUtils;
import apidez.com.android_mvvm_sample.view.activity.EmptyActivity;
import apidez.com.android_mvvm_sample.viewmodel.IPlacesViewModel;
import dagger.Provides;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
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
        // Setup test component
        AppComponent component = DaggerAppComponent.builder()
                .placesModule(new PlacesModule() {
                    @Provides
                    public IPlacesViewModel providePlacesViewModel(PlacesApi placesApi) {
                        return new StubPlacesViewModel();
                    }
                })
                .build();
        ApplicationUtils.application().setComponent(component);

        activityTestRule.launchActivity(new Intent());
        activityTestRule.getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, PlacesFragment.newInstance())
                .commit();

        // TODO: fix this problem
        // Sometime espresso cannot find the view with id: "R.id.action_sort"
        // Maybe the view has not been completed rendered
        // Currently put it to sleep 1000ms
        Thread.sleep(1000);
    }

    @Test
    public void maxLinesOfTextView() throws Exception {
        onView(withId(R.id.name)).check(matches(isDisplayed()));
        onView(withId(R.id.name)).check(matches(hasMaxLines(2)));
    }

    @Test
    public void optionMenus() throws Exception {
        onView(withId(R.id.action_sort)).check(matches(isDisplayed()));
        onView(withId(R.id.action_sort)).perform(click());
        onView(withText("Food")).check(matches(isDisplayed()));
        onView(withText("Cafe")).check(matches(isDisplayed()));
        onView(withText("Store")).check(matches(isDisplayed()));
        onView(withText("Theater")).check(matches(isDisplayed()));
        onView(withText("All")).check(matches(isDisplayed()));
    }
}