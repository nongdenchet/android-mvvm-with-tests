package apidez.com.android_mvvm_sample.dependency.module;

import apidez.com.android_mvvm_sample.stub.StubPlacesViewModel;
import apidez.com.android_mvvm_sample.viewmodel.IPlacesViewModel;
import dagger.Module;
import dagger.Provides;

/**
 * Created by nongdenchet on 10/21/15.
 */
@Module
public class StubPlacesModule {
    @Provides
    public IPlacesViewModel providePlacesViewModel() {
        return new StubPlacesViewModel();
    }
}
