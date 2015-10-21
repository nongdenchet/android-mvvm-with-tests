package apidez.com.android_mvvm_sample.api;

import android.test.suitebuilder.annotation.SmallTest;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import apidez.com.android_mvvm_sample.model.Purchase;

import static junit.framework.Assert.assertTrue;

/**
 * Created by nongdenchet on 10/3/15.
 */

@SmallTest
@RunWith(JUnit4.class)
public class PurcharseApiTest {

    private PurchaseApi apiClient;
    private Gson gson;

    @Before
    public void setUp() {
        gson = new Gson();
        apiClient = new PurchaseApi(gson);
    }

    @Test
    public void submitPurchase() {
        Purchase purchase = new Purchase("411111111111", "123@123.com");
        boolean success = apiClient.submitPurchase(purchase).toBlocking().first();
        assertTrue(success);
    }
}
