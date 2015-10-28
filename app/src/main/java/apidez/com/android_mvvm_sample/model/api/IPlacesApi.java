package apidez.com.android_mvvm_sample.model.api;

import apidez.com.android_mvvm_sample.model.entity.GoogleSearchResult;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by nongdenchet on 10/21/15.
 */
public interface IPlacesApi {
    @GET("nearbysearch/json?types=food&key=AIzaSyBk3A8Q3pqVWYYmZhODbE-D2lf2ZHEoKuo")
    Observable<GoogleSearchResult> placesResult(
            @Query("location") String location,
            @Query("radius") double radius
    );
}
