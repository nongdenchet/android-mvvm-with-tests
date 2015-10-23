package apidez.com.android_mvvm_sample.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import apidez.com.android_mvvm_sample.view.custom.MyTextView;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * Created by nongdenchet on 10/3/15.
 */
public class MatcherEx {

    /**
     * Check toast with messId
     */
    public static void checkToast(int messId, Activity activity) {
        onView(withText(messId)).inRoot(withDecorView(not(activity.getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    /**
     * Check toast with string
     */
    public static void checkToast(String mess, Activity activity) {
        onView(withText(mess)).inRoot(withDecorView(not(activity.getWindow().getDecorView())))
                .check(matches(isDisplayed()));
    }

    /**
     * Returns a matcher that matches {@link MyTextView}s resourceId
     */
    public static Matcher<View> hasResId(int resId) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("has resId");
            }

            @Override
            public boolean matchesSafely(View view) {
                try {
                    return ((MyTextView) view).getBackgroundResource() == resId;
                } catch (Exception exception) {
                    return false;
                }
            }
        };
    }

    /**
     * Returns a matcher that matches {@link View}s has listener
     */
    public static Matcher<View> hasListener() {
        return new TypeSafeMatcher<View>() {
            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
            @Override
            protected boolean matchesSafely(View view) {
                return view.hasOnClickListeners();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("has onclick listener");
            }
        };
    }

    /**
     * Returns a matcher that matches {@link TextView}s has maxLines
     */
    public static Matcher<View> hasMaxLines(int max) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("has max lines");
            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean matchesSafely(View view) {
                try {
                    return ((TextView) view).getMaxLines() <= max;
                } catch (Exception exception) {
                    return false;
                }
            }
        };
    }

    /**
     * Returns a matcher that matches {@link RecyclerView}s has exactly item counts
     */
    public static Matcher<View> hasItemCount(int count) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("has items");
            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean matchesSafely(View view) {
                try {
                    return ((RecyclerView) view).getAdapter().getItemCount() == count;
                } catch (Exception exception) {
                    return false;
                }
            }
        };
    }
}
