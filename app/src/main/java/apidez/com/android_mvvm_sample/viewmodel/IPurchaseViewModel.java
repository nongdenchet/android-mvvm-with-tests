package apidez.com.android_mvvm_sample.viewmodel;

import rx.Observable;

/**
 * Created by nongdenchet on 10/2/15.
 */
public interface IPurchaseViewModel {
    /**
     * Return observable that check valid credit card
     */
    Observable<Boolean> creditCardValid();

    /**
     * Return observable check valid email
     */
    Observable<Boolean> emailValid();

    /**
     * update credit card
     */
    void nextCreditCard(CharSequence creditCard);

    /**
     * update email
     */
    void nextEmail(CharSequence email);

    /**
     * Create observable check be able to submit?
     */
    Observable<Boolean> canSubmit();

    /**
     * Command submit
     */
    Observable<Boolean> submit();
}
