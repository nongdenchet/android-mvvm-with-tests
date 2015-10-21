package apidez.com.android_mvvm_sample.dependency.module;

import apidez.com.android_mvvm_sample.stub.StubPurchaseViewModel;
import apidez.com.android_mvvm_sample.viewmodel.IPurchaseViewModel;
import dagger.Module;
import dagger.Provides;

/**
 * Created by nongdenchet on 10/2/15.
 */
@Module
public class StubPurchaseModule {
    @Provides
    public IPurchaseViewModel providePurchaseViewModel() {
        return new StubPurchaseViewModel();
    }
}
