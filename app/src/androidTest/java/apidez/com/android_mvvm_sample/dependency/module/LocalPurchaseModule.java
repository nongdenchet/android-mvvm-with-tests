package apidez.com.android_mvvm_sample.dependency.module;

import org.mockito.Mockito;

import apidez.com.android_mvvm_sample.api.PurchaseApi;
import apidez.com.android_mvvm_sample.model.Purchase;
import apidez.com.android_mvvm_sample.viewmodel.IPurchaseViewModel;
import apidez.com.android_mvvm_sample.viewmodel.PurchaseViewModel;
import dagger.Module;
import dagger.Provides;
import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by nongdenchet on 10/22/15.
 */
@Module
public class LocalPurchaseModule {
    @Provides
    public IPurchaseViewModel providePurchaseViewModel() {
        PurchaseApi purchaseApi = Mockito.mock(PurchaseApi.class);
        when(purchaseApi.submitPurchase(any(Purchase.class))).thenReturn(Observable.just(true));
        return new PurchaseViewModel(purchaseApi);
    }
}
