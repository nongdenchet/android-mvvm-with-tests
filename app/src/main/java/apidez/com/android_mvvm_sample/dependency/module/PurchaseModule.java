package apidez.com.android_mvvm_sample.dependency.module;

import apidez.com.android_mvvm_sample.api.ApiClient;
import apidez.com.android_mvvm_sample.viewmodel.IPurchaseViewModel;
import apidez.com.android_mvvm_sample.viewmodel.PurchaseViewModel;
import dagger.Module;
import dagger.Provides;

/**
 * Created by nongdenchet on 10/2/15.
 */
@Module
public class PurchaseModule {
    @Provides
    IPurchaseViewModel providePurchaseViewModel(ApiClient apiClient) {
        return new PurchaseViewModel(apiClient);
    }
}
