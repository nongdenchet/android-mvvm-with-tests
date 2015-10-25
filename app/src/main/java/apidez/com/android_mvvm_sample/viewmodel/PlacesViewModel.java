package apidez.com.android_mvvm_sample.viewmodel;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import apidez.com.android_mvvm_sample.api.IPlacesApi;
import apidez.com.android_mvvm_sample.model.Place;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by nongdenchet on 10/21/15.
 */
public class PlacesViewModel implements IPlacesViewModel {

    private IPlacesApi mPlacesApi;
    private final int TIME_OUT = 5;
    private final int RETRY = 3;
    private List<Place> allPlaces = new ArrayList<>();

    public PlacesViewModel(@NonNull IPlacesApi placesApi) {
        mPlacesApi = placesApi;
    }

    // observable property
    private BehaviorSubject<List<Place>> mPlaces = BehaviorSubject.create();

    /**
     * Return an Observable that emits the current places
     */
    public Observable<List<Place>> currentPlaces() {
        return mPlaces.asObservable();
    }

    /**
     * Command fetching all places
     */
    @Override
    public Observable<Boolean> fetchAllPlaces(String location, double radius) {
        return mPlacesApi.placesResult(location, radius)
                .map(googleSearchResult -> {
                    allPlaces = googleSearchResult.results;
                    mPlaces.onNext(googleSearchResult.results);
                    return true;
                })
                .timeout(TIME_OUT, TimeUnit.SECONDS)
                .retry(RETRY);
    }

    /**
     * Command filtering places
     */
    @Override
    public void filterPlacesByType(String type) {
        if (type.equals("All")) {
            mPlaces.onNext(allPlaces);
        } else {
            List<Place> newPlaces = new ArrayList<>();
            Observable.from(allPlaces)
                    .filter(place -> place.getTypes().contains(getApiType(type)))
                    .subscribe(newPlaces::add);
            mPlaces.onNext(newPlaces);
        }
    }

    /**
     * Helpers change type to api_type
     */
    private String getApiType(String type) {
        type = type.toLowerCase();
        switch (type) {
            case "theater":
                return "movie_theater";
            default:
                return type;
        }
    }
}
