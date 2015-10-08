package apidez.com.android_mvvm_sample.view.activity;

import android.support.v7.app.AppCompatActivity;

import rx.subjects.BehaviorSubject;

/**
 * Created by nongdenchet on 10/1/15.
 */
public abstract class BaseActivity extends AppCompatActivity{

    private final BehaviorSubject<BaseActivity> preDestroy = BehaviorSubject.create();

    protected BehaviorSubject<BaseActivity> preDestroy() {
        return preDestroy;
    }

    @Override
    protected void onDestroy() {
        preDestroy.onNext(this);
        super.onDestroy();
    }
}
