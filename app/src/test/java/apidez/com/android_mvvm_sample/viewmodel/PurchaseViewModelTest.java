package apidez.com.android_mvvm_sample.viewmodel;

import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

import apidez.com.android_mvvm_sample.api.PurchaseApi;
import apidez.com.android_mvvm_sample.model.Purchase;
import rx.Observable;
import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@SmallTest
@RunWith(JUnit4.class)
public class PurchaseViewModelTest {

    private PurchaseViewModel purchaseViewModel;
    private PurchaseApi purchaseApi;
    private TestSubscriber<Boolean> testSubscriber;

    @Before
    public void setUpViewModel() {
        purchaseApi = Mockito.mock(PurchaseApi.class);
        purchaseViewModel = new PurchaseViewModel(purchaseApi);
        testSubscriber = TestSubscriber.create();
    }

    @Test
    public void inputValidCreditCard() throws Exception {
        purchaseViewModel.creditCardValid().subscribe(testSubscriber);
        purchaseViewModel.nextCreditCard("412341234123");
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(Collections.singletonList(true));
    }

    @Test
    public void inputInvalidCreditCard() throws Exception {
        purchaseViewModel.creditCardValid().subscribe(testSubscriber);
        purchaseViewModel.nextCreditCard("4123");
        purchaseViewModel.nextCreditCard("abcd");
        purchaseViewModel.nextCreditCard("abcdabcdabcd");
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(Arrays.asList(false, false, false));
    }

    @Test
    public void inputValidEmail() throws Exception {
        purchaseViewModel.emailValid().subscribe(testSubscriber);
        purchaseViewModel.nextEmail("ndc@gmail.com");
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(Collections.singletonList(true));
    }

    @Test
    public void inputInvalidEmail() throws Exception {
        purchaseViewModel.emailValid().subscribe(testSubscriber);
        purchaseViewModel.nextEmail("4123");
        purchaseViewModel.nextEmail("iamdeveloper@");
        purchaseViewModel.nextEmail("__123$$@gm.co");
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(Arrays.asList(false, false, false));
    }

    @Test
    public void canSubmit() throws Exception {
        purchaseViewModel.canSubmit().subscribe(testSubscriber);
        purchaseViewModel.nextCreditCard("412341234123");
        purchaseViewModel.nextEmail("ndc@gmail.com");
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(Collections.singletonList(true));
    }

    @Test
    public void cannotSubmitInvalidEmail() throws Exception {
        purchaseViewModel.canSubmit().subscribe(testSubscriber);
        purchaseViewModel.nextCreditCard("412341234123");
        purchaseViewModel.nextEmail("ndc###@gmail.com");
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(Collections.singletonList(false));
    }

    @Test
    public void cannotSubmitInvalidCreditCard() throws Exception {
        purchaseViewModel.canSubmit().subscribe(testSubscriber);
        purchaseViewModel.nextCreditCard("412123123341234123");
        purchaseViewModel.nextEmail("ndc@gmail.com");
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(Collections.singletonList(false));
    }

    @Test
    public void cannotSubmitEmptyEmail() throws Exception {
        purchaseViewModel.canSubmit().subscribe(testSubscriber);
        purchaseViewModel.nextCreditCard("412341234123");
        purchaseViewModel.nextEmail("");
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(Collections.singletonList(false));
    }

    @Test
    public void cannotSubmitEmptyCreditCard() throws Exception {
        purchaseViewModel.canSubmit().subscribe(testSubscriber);
        purchaseViewModel.nextEmail("ndc@gmail.com");
        purchaseViewModel.nextCreditCard("");
        testSubscriber.assertNoErrors();
        testSubscriber.assertReceivedOnNext(Collections.singletonList(false));
    }

    @Test
    public void submit() throws Exception {
        purchaseViewModel.nextCreditCard("412123123341234123");
        purchaseViewModel.nextEmail("ndc@gmail.com");
        Purchase purchase = purchaseViewModel.getPurchase();
        when(purchaseApi.submitPurchase(purchase)).thenReturn(Observable.just(true));
        purchaseViewModel.submit().subscribe(testSubscriber);
        testSubscriber.assertReceivedOnNext(Collections.singletonList(true));
        verify(purchaseApi).submitPurchase(purchase);
    }

    @Test
    public void submitTimeout() throws Exception {
        purchaseViewModel.nextCreditCard("412123123341234123");
        purchaseViewModel.nextEmail("ndc@gmail.com");
        Purchase purchase = purchaseViewModel.getPurchase();
        when(purchaseApi.submitPurchase(purchase)).thenReturn(
                Observable.create(subscriber -> {
                    try {
                        Thread.sleep(5100);
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                    } catch (InterruptedException e) {
                        subscriber.onError(e);
                    }
                })
        );
        try {
            boolean success = purchaseViewModel.submit().toBlocking().first();
            if (success) fail("Should be timeout");
        } catch (Exception ignored) {
            // The test pass here, it should be timeout
        }
    }

    @Test
    public void submitOnTime() throws Exception {
        purchaseViewModel.nextCreditCard("412123123341234123");
        purchaseViewModel.nextEmail("ndc@gmail.com");
        Purchase purchase = purchaseViewModel.getPurchase();
        when(purchaseApi.submitPurchase(purchase)).thenReturn(
                Observable.create(subscriber -> {
                    try {
                        Thread.sleep(4900);
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                    } catch (InterruptedException e) {
                        subscriber.onError(e);
                    }
                })
        );
        try {
            boolean success = purchaseViewModel.submit().toBlocking().first();
            assertTrue(success);
        } catch (Exception ignored) {
            // The test pass here, it should be timeout
            fail("Should be on time");
        }
    }

    @Test
    public void submitRetry() throws Exception {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        purchaseViewModel.nextCreditCard("412123123341234123");
        purchaseViewModel.nextEmail("ndc@gmail.com");
        Purchase purchase = purchaseViewModel.getPurchase();
        when(purchaseApi.submitPurchase(purchase)).thenReturn(
                Observable.create(subscriber -> {
                    if (atomicInteger.getAndIncrement() < 3) {
                        subscriber.onError(new Exception());
                    } else {
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                    }
                })
        );
        try {
            boolean success = purchaseViewModel.submit().toBlocking().first();
            verify(purchaseApi).submitPurchase(purchase);
            assertTrue(success);
        } catch (Exception ignored) {
            fail("Have to retry three times");
        }
    }

    @Test
    public void submitExceedRetry() throws Exception {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        purchaseViewModel.nextCreditCard("412123123341234123");
        purchaseViewModel.nextEmail("ndc@gmail.com");
        Purchase purchase = purchaseViewModel.getPurchase();
        when(purchaseApi.submitPurchase(purchase)).thenReturn(
                Observable.create(subscriber -> {
                    if (atomicInteger.getAndIncrement() < 4) {
                        subscriber.onError(new Exception());
                    } else {
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                    }
                })
        );
        try {
            purchaseViewModel.submit().toBlocking().first();
            fail("Should be out of retry");
        } catch (Exception ignored) {
            // Test pass here
        }
    }
}