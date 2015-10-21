package apidez.com.android_mvvm_sample.dependency.module;

import com.google.gson.Gson;

import javax.inject.Singleton;

import apidez.com.android_mvvm_sample.api.PurchaseApi;
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
    @Singleton
    public PurchaseApi provideApiClient(Gson gson) {
        return new PurchaseApi(gson);
    }

    @Provides
    IPurchaseViewModel providePurchaseViewModel(PurchaseApi purchaseApi) {
        return new PurchaseViewModel(purchaseApi);
    }
}
