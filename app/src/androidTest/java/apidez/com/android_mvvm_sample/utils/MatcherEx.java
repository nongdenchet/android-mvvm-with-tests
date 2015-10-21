package apidez.com.android_mvvm_sample.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import apidez.com.android_mvvm_sample.view.custom.MyTextView;

/**
 * Created by nongdenchet on 10/3/15.
 */
public class MatcherEx {

    /**
     * Returns a matcher that matches {@link View}s resourceId
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

    public static Matcher<View> hasMaxLines(int max) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("has max lines");
            }

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
}
