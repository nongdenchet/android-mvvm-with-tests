package apidez.com.android_mvvm_sample.viewmodel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import apidez.com.android_mvvm_sample.model.api.IPlacesApi;
import apidez.com.android_mvvm_sample.model.entity.GoogleSearchResult;
import apidez.com.android_mvvm_sample.model.entity.Place;
import apidez.com.android_mvvm_sample.utils.TestDataUtils;
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
    private IPlacesApi placesApi;
    private TestSubscriber<Boolean> testSubscriber;
    private TestSubscriber<List<Place>> testSubscriberPlaces;
    private String LOCATION = "location";
    private int RADIUS = 100;

    @Before
    public void setUpViewModel() {
        placesApi = Mockito.mock(IPlacesApi.class);
        placesViewModel = new PlacesViewModel(placesApi);
        testSubscriber = TestSubscriber.create();
        testSubscriberPlaces = TestSubscriber.create();
        when(placesApi.placesResult(LOCATION, RADIUS)).thenReturn(testDataObservable());
    }

    @Test
    public void fetchAllPlacesSuccess() {
        placesViewModel.fetchAllPlaces(LOCATION, RADIUS).subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
        testSubscriber.assertCompleted();
        testSubscriber.assertReceivedOnNext(Collections.singletonList(true));
    }

    @Test
    public void fetchAllPlaces() {
        placesViewModel.fetchAllPlaces(LOCATION, RADIUS).subscribe();
        placesViewModel.currentPlaces().subscribe(testSubscriberPlaces);
        testSubscriberPlaces.assertNoErrors();
        List<List<Place>> lists = testSubscriberPlaces.getOnNextEvents();
        assertEquals(lists.size(), 1);
        assertEquals(lists.get(0).size(), 10);
    }

    @Test
    public void filterAll() {
        assertEquals(getAndFilterWith("all").size(), 10);
        getAndFilterWith("cafe");
        assertEquals(getAndFilterWith("all").size(), 10);
    }

    @Test
    public void filterFood() {
        assertEquals(getAndFilterWith("food").size(), 4);
        getAndFilterWith("cafe");
        assertEquals(getAndFilterWith("food").size(), 4);
    }

    @Test
    public void filterCafe() {
        assertEquals(getAndFilterWith("cafe").size(), 5);
    }

    @Test
    public void filterStore() {
        assertEquals(getAndFilterWith("store").size(), 4);
    }

    @Test
    public void filterRestaurant() {
        assertEquals(getAndFilterWith("restaurant").size(), 3);
        getAndFilterWith("cafe");
        assertEquals(getAndFilterWith("restaurant").size(), 3);
    }

    @Test
    public void filterTheater() {
        assertEquals(getAndFilterWith("theater").size(), 3);
    }

    @Test
    public void fetchTimeout() throws Exception {
        when(placesApi.placesResult(LOCATION, RADIUS)).thenReturn(
                Observable.create(subscriber -> {
                    try {
                        Thread.sleep(5100);
                        subscriber.onNext(TestDataUtils.nearByData());
                        subscriber.onCompleted();
                    } catch (InterruptedException e) {
                        subscriber.onError(e);
                    }
                })
        );
        try {
            boolean success = placesViewModel.fetchAllPlaces(LOCATION, RADIUS).toBlocking().first();
            if (success) fail("Should be timeout");
        } catch (Exception ignored) {
            // The test pass here, it should be timeout
        }
    }

    @Test
    public void fetchOnTime() throws Exception {
        when(placesApi.placesResult(LOCATION, RADIUS)).thenReturn(
                Observable.create(subscriber -> {
                    try {
                        Thread.sleep(4900);
                        subscriber.onNext(TestDataUtils.nearByData());
                        subscriber.onCompleted();
                    } catch (InterruptedException e) {
                        subscriber.onError(e);
                    }
                })
        );
        try {
            boolean success = placesViewModel.fetchAllPlaces(LOCATION, RADIUS).toBlocking().first();
            assertTrue(success);
        } catch (Exception ignored) {
            fail("Should be on time");
        }
    }

    @Test
    public void fetchRetry() throws Exception {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        when(placesApi.placesResult(LOCATION, RADIUS)).thenReturn(
                Observable.create(subscriber -> {
                    if (atomicInteger.getAndIncrement() < 3) {
                        subscriber.onError(new Exception());
                    } else {
                        subscriber.onNext(TestDataUtils.nearByData());
                        subscriber.onCompleted();
                    }
                })
        );
        try {
            boolean success = placesViewModel.fetchAllPlaces(LOCATION, RADIUS).toBlocking().first();
            verify(placesApi).placesResult(LOCATION, RADIUS);
            assertTrue(success);
        } catch (Exception ignored) {
            fail("Have to retry three times");
        }
    }

    @Test
    public void fetchExceedRetry() throws Exception {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        when(placesApi.placesResult(LOCATION, RADIUS)).thenReturn(
                Observable.create(subscriber -> {
                    if (atomicInteger.getAndIncrement() < 4) {
                        subscriber.onError(new Exception());
                    } else {
                        subscriber.onNext(TestDataUtils.nearByData());
                        subscriber.onCompleted();
                    }
                })
        );
        try {
            placesViewModel.fetchAllPlaces(LOCATION, RADIUS).toBlocking().first();
            fail("Should be out of retry");
        } catch (Exception ignored) {
            // Test pass here
        }
    }

    private List<Place> getAndFilterWith(String type) {
        placesViewModel.fetchAllPlaces(LOCATION, RADIUS).subscribe();
        placesViewModel.filterPlacesByType(type);
        placesViewModel.currentPlaces().subscribe(testSubscriberPlaces);
        testSubscriberPlaces.assertNoErrors();
        List<List<Place>> lists = testSubscriberPlaces.getOnNextEvents();
        return lists.get(lists.size() - 1);
    }

    private Observable<GoogleSearchResult> testDataObservable() {
        return Observable.just(TestDataUtils.nearByData());
    }
}