package apidez.com.android_mvvm_sample.dependency.module;

import apidez.com.android_mvvm_sample.api.IPlacesApi;
import apidez.com.android_mvvm_sample.dependency.scope.ViewScope;
import apidez.com.android_mvvm_sample.utils.RetrofitUtils;
import apidez.com.android_mvvm_sample.viewmodel.IPlacesViewModel;
import apidez.com.android_mvvm_sample.viewmodel.PlacesViewModel;
import dagger.Module;
import dagger.Provides;

/**
 * Created by nongdenchet on 10/21/15.
 */
@Module
public class PlacesModule {
    @Provides
    @ViewScope
    public IPlacesApi providePlacesApi() {
        return RetrofitUtils.create(IPlacesApi.class, "https://maps.googleapis.com/maps/api/place/");
    }

    @Provides
    @ViewScope
    public IPlacesViewModel providePlacesViewModel(IPlacesApi placesApi) {
        return new PlacesViewModel(placesApi);
    }
}