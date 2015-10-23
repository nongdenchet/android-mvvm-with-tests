package apidez.com.android_mvvm_sample;

import android.app.Application;

import apidez.com.android_mvvm_sample.dependency.component.AppComponent;
import apidez.com.android_mvvm_sample.dependency.component.DaggerAppComponent;
import apidez.com.android_mvvm_sample.dependency.module.AppModule;
import apidez.com.android_mvvm_sample.dependency.module.PlacesModule;
import apidez.com.android_mvvm_sample.dependency.module.PurchaseModule;

/**
 * Created by nongdenchet on 10/2/15.
 */
public class MyApplication extends Application {

    protected AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // Create app component
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule())
                    .purchaseModule(new PurchaseModule())
                    .placesModule(new PlacesModule())
                    .build();
        }
    }

    public AppComponent component() {
        return appComponent;
    }
}