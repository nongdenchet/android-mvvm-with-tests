package apidez.com.android_mvvm_sample.stub;

import java.util.Arrays;
import java.util.List;

import apidez.com.android_mvvm_sample.model.Place;
import apidez.com.android_mvvm_sample.utils.StringUtils;
import apidez.com.android_mvvm_sample.viewmodel.IPlacesViewModel;
import rx.Observable;

/**
 * Created by nongdenchet on 10/21/15.
 */
public class StubPlacesViewModel implements IPlacesViewModel {
    @Override
    public Observable<Boolean> fetchAllPlaces() {
        return Observable.just(true);
    }

    @Override
    public Observable<List<Place>> currentPlaces() {
        Place[] places = new Place[]{new Place.Builder().name(StringUtils.generateString("apidez", 500)).build()};
        return Observable.just(Arrays.asList(places));
    }

    @Override
    public void filterPlacesByType(String type) {
    }
}
