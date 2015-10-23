package apidez.com.android_mvvm_sample.dependency.component;

import javax.inject.Singleton;

import apidez.com.android_mvvm_sample.dependency.module.AppModule;
import apidez.com.android_mvvm_sample.dependency.module.PlacesModule;
import apidez.com.android_mvvm_sample.dependency.module.PurchaseModule;
import dagger.Component;

/**
 * Created by nongdenchet on 10/2/15.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    PlacesComponent plus(PlacesModule placesModule);
    PurchaseComponent plus(PurchaseModule purchaseModule);
}
