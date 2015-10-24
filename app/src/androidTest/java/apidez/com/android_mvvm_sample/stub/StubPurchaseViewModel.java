package apidez.com.android_mvvm_sample.stub;

import apidez.com.android_mvvm_sample.viewmodel.IPurchaseViewModel;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by nongdenchet on 10/2/15.
 */
public class StubPurchaseViewModel implements IPurchaseViewModel {

    private BehaviorSubject<Boolean> creditCardValid = BehaviorSubject.create();
    private BehaviorSubject<Boolean> emailValid = BehaviorSubject.create();

    @Override
    public Observable<Boolean> creditCardValid() {
        return creditCardValid.asObservable();
    }

    @Override
    public Observable<Boolean> emailValid() {
        return emailValid.asObservable();
    }

    @Override
    public void nextCreditCard(CharSequence creditCard) {
        creditCardValid.onNext(creditCard.length() % 2 == 0);
    }

    @Override
    public void nextEmail(CharSequence email) {
        emailValid.onNext(email.length() % 2 == 0);
    }

    @Override
    public Observable<Boolean> canSubmit() {
        return creditCardValid.asObservable();
    }

    @Override
    public Observable<Boolean> submit() {
        return Observable.create(subscriber -> {
            try {
                Thread.sleep(2000);
                int foo = (emailValid.getValue()) ? 1 : 0;
                foo = 12 / foo;
                subscriber.onNext(true);
                subscriber.onCompleted();
            } catch (Exception ex) {
                subscriber.onError(ex);
            }
        });
    }
}
