package apidez.com.android_mvvm_sample.api;

import apidez.com.android_mvvm_sample.model.GoogleSearchResult;
import retrofit.http.GET;
import rx.Observable;

/**
 * Created by nongdenchet on 10/21/15.
 */
public interface PlacesApi {
    @GET("nearbysearch/json?location=10.7864422,106.677516&radius=500&types=food&key=AIzaSyBk3A8Q3pqVWYYmZhODbE-D2lf2ZHEoKuo")
    Observable<GoogleSearchResult> placesResult();
}
