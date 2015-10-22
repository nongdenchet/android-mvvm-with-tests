package apidez.com.android_mvvm_sample.dependency.module;

import org.mockito.Mockito;

import apidez.com.android_mvvm_sample.api.PlacesApi;
import apidez.com.android_mvvm_sample.utils.TestDataUtils;
import apidez.com.android_mvvm_sample.viewmodel.IPlacesViewModel;
import apidez.com.android_mvvm_sample.viewmodel.PlacesViewModel;
import dagger.Module;
import dagger.Provides;
import rx.Observable;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by nongdenchet on 10/22/15.
 */
@Module
public class LocalPlacesModule {
    @Provides
    public IPlacesViewModel providePlacesViewModel() {
        PlacesApi placesApi = Mockito.mock(PlacesApi.class);
        when(placesApi.placesResult(any(String.class), any(Double.class)))
                .thenReturn(Observable.just(TestDataUtils.nearByData()));
        return new PlacesViewModel(placesApi);
    }
}
