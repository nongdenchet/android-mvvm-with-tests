package apidez.com.android_mvvm_sample.dependency.component;

import apidez.com.android_mvvm_sample.dependency.module.StubPurchaseModule;
import apidez.com.android_mvvm_sample.view.activity.PurchaseActivityTest;
import dagger.Component;

/**
 * Created by nongdenchet on 10/2/15.
 */
@Component(modules = StubPurchaseModule.class)
public interface TestComponent extends ApplicationComponent {
    void inject(PurchaseActivityTest purchaseActivityTest);
}
