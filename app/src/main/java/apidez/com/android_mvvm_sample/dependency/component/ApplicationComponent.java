package apidez.com.android_mvvm_sample.dependency.component;

import javax.inject.Singleton;

import apidez.com.android_mvvm_sample.dependency.module.ApiModule;
import apidez.com.android_mvvm_sample.dependency.module.PurchaseModule;
import apidez.com.android_mvvm_sample.view.activity.PurchaseActivity;
import dagger.Component;

/**
 * Created by nongdenchet on 10/2/15.
 */
@Singleton
@Component(modules = {ApiModule.class, PurchaseModule.class})
public interface ApplicationComponent {
    void inject(PurchaseActivity purchaseActivity);
}
