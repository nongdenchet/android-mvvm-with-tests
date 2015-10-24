package apidez.com.android_mvvm_sample.api;

import apidez.com.android_mvvm_sample.model.Purchase;
import rx.Observable;

/**
 * Created by nongdenchet on 10/24/15.
 */
public interface IPurchaseApi {
    Observable<Boolean> submitPurchase(Purchase purchase);
}
