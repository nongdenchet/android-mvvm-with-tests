package apidez.com.android_mvvm_sample.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import apidez.com.android_mvvm_sample.model.entity.GoogleSearchResult;
import apidez.com.android_mvvm_sample.model.entity.Place;

/**
 * Created by nongdenchet on 10/22/15.
 */
public class TestDataUtils {

    /**
     * Google search nearby test data
     */
    public static GoogleSearchResult nearByData() {
        List<Place> places = new ArrayList<>();
        places.add(new Place.Builder().name("A").types(Arrays.asList("food", "cafe")).build());
        places.add(new Place.Builder().name("B").types(Arrays.asList("food", "movie_theater")).build());
        places.add(new Place.Builder().name("C").types(Arrays.asList("store")).build());
        places.add(new Place.Builder().name("D").types(Arrays.asList("store")).build());
        places.add(new Place.Builder().name("E").types(Arrays.asList("cafe")).build());
        places.add(new Place.Builder().name("F").types(Arrays.asList("food", "store", "cafe", "movie_theater")).build());
        places.add(new Place.Builder().name("G").types(Arrays.asList("restaurant", "store")).build());
        places.add(new Place.Builder().name("H").types(Arrays.asList("restaurant", "cafe")).build());
        places.add(new Place.Builder().name("I").types(Arrays.asList("restaurant")).build());
        places.add(new Place.Builder().name("K").types(Arrays.asList("movie_theater", "cafe", "food")).build());
        GoogleSearchResult googleSearchResult = new GoogleSearchResult();
        googleSearchResult.status = "OK";
        googleSearchResult.results = places;
        return googleSearchResult;
    }
}
