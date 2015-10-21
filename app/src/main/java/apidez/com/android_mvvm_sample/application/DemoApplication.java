package apidez.com.android_mvvm_sample.application;

import android.app.Application;

import apidez.com.android_mvvm_sample.dependency.component.ApplicationComponent;
import apidez.com.android_mvvm_sample.dependency.component.DaggerApplicationComponent;
import apidez.com.android_mvvm_sample.dependency.module.CommonModule;
import apidez.com.android_mvvm_sample.dependency.module.PlacesModule;
import apidez.com.android_mvvm_sample.dependency.module.PurchaseModule;

/**
 * Created by nongdenchet on 10/2/15.
 */
public class DemoApplication extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        if (component == null) {
            component = DaggerApplicationComponent.builder()
                    .commonModule(new CommonModule())
                    .purchaseModule(new PurchaseModule())
                    .placesModule(new PlacesModule())
                    .build();
        }
    }

    public void setComponent(ApplicationComponent component) {
        this.component = component;
    }

    public ApplicationComponent component() {
        return component;
    }
}