package apidez.com.android_mvvm_sample.viewmodel;

import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.concurrent.atomic.AtomicInteger;

import apidez.com.android_mvvm_sample.api.ApiClient;
import apidez.com.android_mvvm_sample.model.Purchase;
import rx.Observable;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@SmallTest
@RunWith(JUnit4.class)
public class PurchaseViewModelUnitTest {

    private PurchaseViewModel purchaseViewModel;
    private ApiClient apiClient;

    @Before
    public void setUpViewModel() {
        apiClient = Mockito.mock(ApiClient.class);
        purchaseViewModel = new PurchaseViewModel(apiClient);
    }

    @Test
    public void inputValidCreditCard() throws Exception {
        purchaseViewModel.nextCreditCard("412341234123");
        assertTrue(purchaseViewModel.creditCardValid().toBlocking().first());
    }

    @Test
    public void inputInvalidCreditCard() throws Exception {
        purchaseViewModel.nextCreditCard("4123");
        assertFalse(purchaseViewModel.creditCardValid().toBlocking().first());
        purchaseViewModel.nextCreditCard("abcd");
        assertFalse(purchaseViewModel.creditCardValid().toBlocking().first());
        purchaseViewModel.nextCreditCard("abcdabcdabcd");
        assertFalse(purchaseViewModel.creditCardValid().toBlocking().first());
    }

    @Test
    public void inputValidEmail() throws Exception {
        purchaseViewModel.nextEmail("ndc@gmail.com");
        assertTrue(purchaseViewModel.emailValid().toBlocking().first());
    }

    @Test
    public void inputInvalidEmail() throws Exception {
        purchaseViewModel.nextEmail("4123");
        assertFalse(purchaseViewModel.emailValid().toBlocking().first());
        purchaseViewModel.nextEmail("iamdeveloper@");
        assertFalse(purchaseViewModel.emailValid().toBlocking().first());
        purchaseViewModel.nextEmail("__123$$@gm.co");
        assertFalse(purchaseViewModel.emailValid().toBlocking().first());
    }

    @Test
    public void canSubmit() throws Exception {
        purchaseViewModel.nextCreditCard("412341234123");
        purchaseViewModel.nextEmail("ndc@gmail.com");
        assertTrue(purchaseViewModel.canSubmit().toBlocking().first());
    }

    @Test
    public void cannotSubmitInvalidEmail() throws Exception {
        purchaseViewModel.nextCreditCard("412341234123");
        purchaseViewModel.nextEmail("ndc###@gmail.com");
        assertFalse(purchaseViewModel.canSubmit().toBlocking().first());
    }

    @Test
    public void cannotSubmitInvalidCreditCard() throws Exception {
        purchaseViewModel.nextCreditCard("412123123341234123");
        purchaseViewModel.nextEmail("ndc@gmail.com");
        assertFalse(purchaseViewModel.canSubmit().toBlocking().first());
    }

    @Test
    public void cannotSubmitEmptyEmail() throws Exception {
        purchaseViewModel.nextCreditCard("412341234123");
        purchaseViewModel.nextEmail("");
        assertFalse(purchaseViewModel.canSubmit().toBlocking().firstOrDefault(false));
    }

    @Test
    public void cannotSubmitEmptyCreditCard() throws Exception {
        purchaseViewModel.nextEmail("ndc@gmail.com");
        purchaseViewModel.nextCreditCard("");
        assertFalse(purchaseViewModel.canSubmit().toBlocking().firstOrDefault(false));
    }

    @Test
    public void submit() throws Exception {
        purchaseViewModel.nextCreditCard("412123123341234123");
        purchaseViewModel.nextEmail("ndc@gmail.com");
        Purchase purchase = purchaseViewModel.getPurchase();
        when(apiClient.submitPurchase(purchase)).thenReturn(Observable.just(true));
        boolean success = purchaseViewModel.submit().toBlocking().first();
        verify(apiClient).submitPurchase(purchase);
        assertTrue(success);
    }

    @Test
    public void submitTimeout() throws Exception {
        purchaseViewModel.nextCreditCard("412123123341234123");
        purchaseViewModel.nextEmail("ndc@gmail.com");
        Purchase purchase = purchaseViewModel.getPurchase();
        when(apiClient.submitPurchase(purchase)).thenReturn(
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
            verify(apiClient).submitPurchase(purchase);
            if (success) fail("Should be timeout");
        } catch (Exception ignored) {
            // The test pass here, it should be timeout
        }
    }

    @Test
    public void submitRetry() throws Exception {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        purchaseViewModel.nextCreditCard("412123123341234123");
        purchaseViewModel.nextEmail("ndc@gmail.com");
        Purchase purchase = purchaseViewModel.getPurchase();
        when(apiClient.submitPurchase(purchase)).thenReturn(
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
            verify(apiClient).submitPurchase(purchase);
            assertTrue(success);
        } catch (Exception ignored) {
            fail("Have to retry three times");
        }
    }
}