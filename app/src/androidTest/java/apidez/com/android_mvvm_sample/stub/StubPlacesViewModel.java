package apidez.com.android_mvvm_sample.stub;

import java.util.List;

import apidez.com.android_mvvm_sample.model.Place;
import apidez.com.android_mvvm_sample.viewmodel.IPlacesViewModel;
import rx.Observable;

/**
 * Created by nongdenchet on 10/21/15.
 */
public class StubPlacesViewModel implements IPlacesViewModel {
    @Override
    public Observable<Boolean> fetchAllPlaces() {
        return null;
    }

    @Override
    public Observable<List<Place>> currentPlaces() {
        return null;
    }

    @Override
    public void filterPlacesByType(String type) {

    }
}
