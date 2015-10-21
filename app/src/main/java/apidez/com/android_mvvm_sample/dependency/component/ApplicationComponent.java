package apidez.com.android_mvvm_sample.dependency.component;

import javax.inject.Singleton;

import apidez.com.android_mvvm_sample.dependency.module.CommonModule;
import apidez.com.android_mvvm_sample.dependency.module.PlacesModule;
import apidez.com.android_mvvm_sample.dependency.module.PurchaseModule;
import apidez.com.android_mvvm_sample.view.activity.PurchaseActivity;
import apidez.com.android_mvvm_sample.view.fragment.PlacesFragment;
import dagger.Component;

/**
 * Created by nongdenchet on 10/2/15.
 */
@Singleton
@Component(modules = {CommonModule.class, PlacesModule.class, PurchaseModule.class})
public interface ApplicationComponent {
    void inject(PurchaseActivity purchaseActivity);
    void inject(PlacesFragment placesFragment);
}
