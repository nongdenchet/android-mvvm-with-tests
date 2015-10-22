package apidez.com.android_mvvm_sample.viewmodel;


import java.util.List;

import apidez.com.android_mvvm_sample.model.Place;
import rx.Observable;

/**
 * Created by nongdenchet on 10/21/15.
 */
public interface IPlacesViewModel {
    /**
     * Fetch all places from google
     */
    Observable<Boolean> fetchAllPlaces(String location, double radius);

    /**
     * Observe current places
     */
    Observable<List<Place>> currentPlaces();

    /**
     * Filter the places
     */
    void filterPlacesByType(String type);
}
