package apidez.com.android_mvvm_sample.util;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.widget.TextView;

import rx.Observable;

/**
 * Created by nongdenchet on 10/2/15.
 */
public class RxTextViewEx {

    /**
     * Create an observable of character sequences for text changes on {@code view}.
     * <p>
     * <em>Warning:</em> The created observable keeps a strong reference to {@code view}. Unsubscribe
     * to free this reference.
     * <p>
     * <em>Note:</em> A value will be emitted immediately on subscribe.
     */
    @CheckResult
    @NonNull
    public static Observable<CharSequence> textChanges(@NonNull TextView view) {
        return Observable.create(new TextViewSubscribeUnInit(view));
    }
}
