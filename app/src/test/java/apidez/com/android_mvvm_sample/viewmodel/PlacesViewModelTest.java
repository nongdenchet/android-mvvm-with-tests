package apidez.com.android_mvvm_sample.viewmodel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import apidez.com.android_mvvm_sample.api.PlacesApi;
import apidez.com.android_mvvm_sample.model.GoogleSearchResult;
import apidez.com.android_mvvm_sample.model.Place;
import rx.Observable;
import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by nongdenchet on 10/21/15.
 */
public class PlacesViewModelTest {
    private PlacesViewModel placesViewModel;
    private PlacesApi placesApi;
    private TestSubscriber<Boolean> testSubscriber;
    private TestSubscriber<List<Place>> testSubscriberPlaces;

    @Before
    public void setUpViewModel() {
        placesApi = Mockito.mock(PlacesApi.class);
        placesViewModel = new PlacesViewModel(placesApi);
        testSubscriber = TestSubscriber.create();
        testSubscriberPlaces = TestSubscriber.create();
        when(placesApi.placesResult()).thenReturn(testDataObservable());
    }

    @Test
    public void fetchAllPlacesSuccess() {
        placesViewModel.fetchAllPlaces().subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        testSubscriber.assertReceivedOnNext(Collections.singletonList(true));
    }

    @Test
    public void fetchAllPlaces() {
        placesViewModel.fetchAllPlaces().subscribe();
        placesViewModel.currentPlaces().subscribe(testSubscriberPlaces);
        testSubscriberPlaces.assertNoErrors();
        List<List<Place>> lists = testSubscriberPlaces.getOnNextEvents();
        assertEquals(lists.size(), 1);
        assertEquals(lists.get(0).size(), 10);
    }

    @Test
    public void filterAll() {
        List<List<Place>> lists = getAndFilterWith("all");
        assertEquals(lists.size(), 1);
        assertEquals(lists.get(0).size(), 10);
    }

    @Test
    public void filterFood() {
        List<List<Place>> lists = getAndFilterWith("food");
        assertEquals(lists.size(), 1);
        assertEquals(lists.get(0).size(), 4);
    }

    @Test
    public void filterCafe() {
        List<List<Place>> lists = getAndFilterWith("cafe");
        assertEquals(lists.size(), 1);
        assertEquals(lists.get(0).size(), 5);
    }

    @Test
    public void filterStore() {
        List<List<Place>> lists = getAndFilterWith("store");
        assertEquals(lists.size(), 1);
        assertEquals(lists.get(0).size(), 4);
    }

    @Test
    public void filterRestaurant() {
        List<List<Place>> lists = getAndFilterWith("restaurant");
        assertEquals(lists.size(), 1);
        assertEquals(lists.get(0).size(), 3);
    }

    @Test
    public void filterTheater() {
        List<List<Place>> lists = getAndFilterWith("theater");
        assertEquals(lists.size(), 1);
        assertEquals(lists.get(0).size(), 3);
    }

    @Test
    public void fetchTimeout() throws Exception {
        when(placesApi.placesResult()).thenReturn(
                Observable.create(subscriber -> {
                    try {
                        Thread.sleep(5100);
                        subscriber.onNext(testData());
                        subscriber.onCompleted();
                    } catch (InterruptedException e) {
                        subscriber.onError(e);
                    }
                })
        );
        try {
            boolean success = placesViewModel.fetchAllPlaces().toBlocking().first();
            if (success) fail("Should be timeout");
        } catch (Exception ignored) {
            // The test pass here, it should be timeout
        }
    }

    @Test
    public void fetchOnTime() throws Exception {
        when(placesApi.placesResult()).thenReturn(
                Observable.create(subscriber -> {
                    try {
                        Thread.sleep(4900);
                        subscriber.onNext(testData());
                        subscriber.onCompleted();
                    } catch (InterruptedException e) {
                        subscriber.onError(e);
                    }
                })
        );
        try {
            boolean success = placesViewModel.fetchAllPlaces().toBlocking().first();
            assertTrue(success);
        } catch (Exception ignored) {
            fail("Should be on time");
        }
    }

    @Test
    public void fetchRetry() throws Exception {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        when(placesApi.placesResult()).thenReturn(
                Observable.create(subscriber -> {
                    if (atomicInteger.getAndIncrement() < 3) {
                        subscriber.onError(new Exception());
                    } else {
                        subscriber.onNext(testData());
                        subscriber.onCompleted();
                    }
                })
        );
        try {
            boolean success = placesViewModel.fetchAllPlaces().toBlocking().first();
            verify(placesApi).placesResult();
            assertTrue(success);
        } catch (Exception ignored) {
            fail("Have to retry three times");
        }
    }

    @Test
    public void fetchExceedRetry() throws Exception {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        when(placesApi.placesResult()).thenReturn(
                Observable.create(subscriber -> {
                    if (atomicInteger.getAndIncrement() < 4) {
                        subscriber.onError(new Exception());
                    } else {
                        subscriber.onNext(testData());
                        subscriber.onCompleted();
                    }
                })
        );
        try {
            placesViewModel.fetchAllPlaces().toBlocking().first();
            fail("Should be out of retry");
        } catch (Exception ignored) {
            // Test pass here
        }
    }

    private List<List<Place>> getAndFilterWith(String type) {
        placesViewModel.fetchAllPlaces().subscribe();
        placesViewModel.filterPlacesByType(type);
        placesViewModel.currentPlaces().subscribe(testSubscriberPlaces);
        testSubscriberPlaces.assertNoErrors();
        return testSubscriberPlaces.getOnNextEvents();
    }

    private GoogleSearchResult testData() {
        List<Place> places = new ArrayList<>();
        places.add(new Place.Builder().types(Arrays.asList("food", "cafe")).build());
        places.add(new Place.Builder().types(Arrays.asList("food", "movie_theater")).build());
        places.add(new Place.Builder().types(Arrays.asList("store")).build());
        places.add(new Place.Builder().types(Arrays.asList("store")).build());
        places.add(new Place.Builder().types(Arrays.asList("cafe")).build());
        places.add(new Place.Builder().types(Arrays.asList("food", "store", "cafe", "movie_theater")).build());
        places.add(new Place.Builder().types(Arrays.asList("restaurant", "store")).build());
        places.add(new Place.Builder().types(Arrays.asList("restaurant", "cafe")).build());
        places.add(new Place.Builder().types(Arrays.asList("restaurant")).build());
        places.add(new Place.Builder().types(Arrays.asList("movie_theater", "cafe", "food")).build());

        GoogleSearchResult googleSearchResult = new GoogleSearchResult();
        googleSearchResult.status = "OK";
        googleSearchResult.results = places;
        return googleSearchResult;
    }

    private Observable<GoogleSearchResult> testDataObservable() {
        return Observable.just(testData());
    }
}