package apidez.com.android_mvvm_sample;

import apidez.com.android_mvvm_sample.dependency.component.AppComponent;

/**
 * Created by nongdenchet on 10/23/15.
 */
public class TestApplication extends MyApplication {
    public void setComponent(AppComponent appComponent) {
        this.appComponent = appComponent;
    }
}
