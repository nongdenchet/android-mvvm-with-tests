package apidez.com.android_mvvm_sample;

import android.app.Application;

import apidez.com.android_mvvm_sample.dependency.component.AppComponent;
import apidez.com.android_mvvm_sample.dependency.component.DaggerAppComponent;
import apidez.com.android_mvvm_sample.dependency.module.AppModule;

/**
 * Created by nongdenchet on 10/2/15.
 */
public class MyApplication extends Application {

    protected AppComponent mAppComponent;
    protected ComponentBuilder mComponentBuilder;

    @Override
    public void onCreate() {
        super.onCreate();

        // Create app component
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .build();

        // Create component builder
        mComponentBuilder = new ComponentBuilder(mAppComponent);
    }

    public AppComponent component() {
        return mAppComponent;
    }

    public ComponentBuilder builder() {
        return mComponentBuilder;
    }
}