package apidez.com.android_mvvm_sample.viewmodel;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import apidez.com.android_mvvm_sample.api.ApiClient;
import apidez.com.android_mvvm_sample.model.Purchase;
import apidez.com.android_mvvm_sample.util.NumericUtils;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by nongdenchet on 10/2/15.
 */
public class PurchaseViewModel implements IPurchaseViewModel {

    private ApiClient mApiClient;
    private final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private final int TIME_OUT = 5;
    private final int RETRY = 3;

    @Inject
    public PurchaseViewModel(@NonNull ApiClient apiClient) {
        mApiClient = apiClient;
        purchase = new Purchase();
    }

    // observable property
    private BehaviorSubject<CharSequence> mCreditCard = BehaviorSubject.create();
    private BehaviorSubject<CharSequence> mEmail = BehaviorSubject.create();

    // model
    public Purchase purchase;

    public Purchase getPurchase() {
        return purchase;
    }

    /**
     * Return an observable that emit the validation of credit card
     */
    public Observable<Boolean> creditCardValid() {
        return mCreditCard.map(inputText -> (inputText.length() == 12 && NumericUtils.isInteger(inputText)));
    }

    /**
     * Return an observable that emit the validation of email
     */
    public Observable<Boolean> emailValid() {
        return mEmail.map(inputText -> (inputText.toString().matches(EMAIL_REGEX)));
    }

    /**
     * Return an observable that emit the validation submit button
     */
    public Observable<Boolean> canSubmit() {
        return Observable.combineLatest(creditCardValid(), emailValid(),
                (validCreditCard, validEmail) -> validCreditCard && validEmail);
    }

    /***** Commands *****/

    /**
     * Update the credit card
     */
    public void nextCreditCard(CharSequence creditCard) {
        mCreditCard.onNext(creditCard);
    }

    /**
     * Update the email
     */
    public void nextEmail(CharSequence email) {
        mEmail.onNext(email);
    }

    /**
     * Command submit
     */
    public Observable<Boolean> submit() {
        purchase.setCreditCard(mCreditCard.getValue().toString());
        purchase.setEmail(mEmail.getValue().toString());
        return mApiClient.submitPurchase(purchase)
                .timeout(TIME_OUT, TimeUnit.SECONDS)
                .retry(RETRY);
    }
}
