package apidez.com.android_mvvm_sample.util;

import android.view.View;

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
                description.appendText(" has resId");
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
            @Override
            protected boolean matchesSafely(View view) {
                return view.hasOnClickListeners();
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(" has onclick listener");
            }
        };
    }
}
