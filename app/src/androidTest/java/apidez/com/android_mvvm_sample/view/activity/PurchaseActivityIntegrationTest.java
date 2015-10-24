package apidez.com.android_mvvm_sample.view.activity;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.MediumTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import apidez.com.android_mvvm_sample.ComponentBuilder;
import apidez.com.android_mvvm_sample.R;
import apidez.com.android_mvvm_sample.api.PurchaseApi;
import apidez.com.android_mvvm_sample.dependency.component.AppComponent;
import apidez.com.android_mvvm_sample.dependency.component.PurchaseComponent;
import apidez.com.android_mvvm_sample.dependency.module.PurchaseModule;
import apidez.com.android_mvvm_sample.dependency.scope.ViewScope;
import apidez.com.android_mvvm_sample.model.Purchase;
import apidez.com.android_mvvm_sample.utils.ApplicationUtils;
import dagger.Provides;
import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static apidez.com.android_mvvm_sample.utils.MatcherEx.hasListener;
import static apidez.com.android_mvvm_sample.utils.MatcherEx.hasResId;
import static apidez.com.android_mvvm_sample.utils.MatcherEx.waitText;
import static org.hamcrest.Matchers.not;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by nongdenchet on 10/3/15.
 */

/**
 * Test the whole flow mocking long running task
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class PurchaseActivityIntegrationTest {

    @Rule
    public ActivityTestRule<PurchaseActivity> activityTestRule =
            new ActivityTestRule<>(PurchaseActivity.class, true, false);

    @Before
    public void setUp() throws Exception {
        PurchaseModule mockModule = new PurchaseModule() {
            @Provides
            @ViewScope
            public PurchaseApi providePurchasesApi() {
                PurchaseApi purchaseApi = Mockito.mock(PurchaseApi.class);
                when(purchaseApi.submitPurchase(any(Purchase.class))).thenReturn(Observable.just(true));
                return purchaseApi;
            }
        };

        // Setup test component
        AppComponent component = ApplicationUtils.application().component();
        ApplicationUtils.application().setComponentBuilder(new ComponentBuilder(component) {
            @Override
            public PurchaseComponent purchaseComponent() {
                return component.plus(mockModule);
            }
        });

        // Run the activity
        activityTestRule.launchActivity(new Intent());
    }

    @Test
    public void noErrorAtTheBeginning() throws Exception {
        onView(withText(R.string.error_credit_card)).check(doesNotExist());
        onView(withText(R.string.error_email)).check(doesNotExist());
    }

    @Test
    public void hasNoErrorCreditCard() throws Exception {
        onView((withId(R.id.creditCard))).perform(typeText("411111111111"));
        onView(withText(R.string.error_credit_card)).check(doesNotExist());
    }

    @Test
    public void hasErrorCreditCard() throws Exception {
        onView(withId(R.id.creditCard)).perform(typeText("abcdefabcdef"));
        onView(withText(R.string.error_credit_card)).check(matches(isDisplayed()));
    }

    @Test
    public void hasNoErrorEmail() throws Exception {
        onView(withId(R.id.email)).perform(typeText("abc@abc.com"));
        onView(withText(R.string.error_email)).check(doesNotExist());
    }

    @Test
    public void hasErrorEmail() throws Exception {
        onView(withId(R.id.email)).perform(typeText("abc___#!@...com"));
        onView(withText(R.string.error_email)).check(matches(isDisplayed()));
    }

    @Test
     public void cannotSubmitCreditCard() throws Exception {
        onView(withId(R.id.creditCard)).perform(typeText("1234"));
        onView(withId(R.id.email)).perform(typeText("ndc@gmail.com"));
        onView(withId(R.id.btnSubmit)).check(matches(not(hasListener())));
        onView(withId(R.id.btnSubmit)).check(matches(hasResId(R.drawable.bg_inactive_submit)));
    }

    @Test
    public void cannotSubmitEmptyCreditCard() throws Exception {
        onView(withId(R.id.email)).perform(typeText("ndc@gmail.com"));
        onView(withId(R.id.btnSubmit)).check(matches(not(hasListener())));
        onView(withId(R.id.btnSubmit)).check(matches(not(hasResId(R.drawable.bg_submit))));
    }

    @Test
    public void cannotSubmitEmail() throws Exception {
        onView(withId(R.id.email)).perform(typeText("123"));
        onView(withId(R.id.creditCard)).perform(typeText("1234"));
        onView(withId(R.id.btnSubmit)).check(matches(not(hasListener())));
        onView(withId(R.id.btnSubmit)).check(matches(hasResId(R.drawable.bg_inactive_submit)));
    }

    @Test
    public void cannotSubmitEmptyEmail() throws Exception {
        onView(withId(R.id.creditCard)).perform(typeText("411111111111"));
        onView(withId(R.id.btnSubmit)).check(matches(not(hasListener())));
        onView(withId(R.id.btnSubmit)).check(matches(not(hasResId(R.drawable.bg_submit))));
    }

    @Test
    public void canSubmit() throws Exception {
        onView(withId(R.id.creditCard)).perform(typeText("411111111111"));
        onView(withId(R.id.email)).perform(typeText("rain@gmail.com"));
        onView(withId(R.id.btnSubmit)).check(matches(hasListener()));
        onView(withId(R.id.btnSubmit)).check(matches(hasResId(R.drawable.bg_submit)));
    }

    @Test
    public void submitSuccess() throws Exception {
        onView(withId(R.id.creditCard)).perform(typeText("411111111111"));
        onView(withId(R.id.email)).perform(typeText("rain@gmail.com"));
        onView(withId(R.id.btnSubmit)).perform(click());
        onView(withText(R.string.loading)).check(matches(isDisplayed()));
        waitText("Success", 3000);
    }

    @Test
    public void submitFail() throws Exception {
        onView(withId(R.id.creditCard)).perform(typeText("411111111111"));
        onView(withId(R.id.email)).perform(typeText("apidez@gmail.com"));
        onView(withId(R.id.btnSubmit)).perform(click());
        onView(withText(R.string.loading)).check(matches(isDisplayed()));
        waitText("Error", 3000);
    }
}
