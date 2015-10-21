package apidez.com.android_mvvm_sample.dependency.module;

import apidez.com.android_mvvm_sample.api.PlacesApi;
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
    IPlacesViewModel providePlacesViewModel() {
        return new PlacesViewModel(RetrofitUtils.create(PlacesApi.class, "https://maps.googleapis.com/maps/api/place/"));
    }
}