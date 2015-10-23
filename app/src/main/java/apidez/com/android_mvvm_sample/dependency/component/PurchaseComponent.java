package apidez.com.android_mvvm_sample.dependency.component;

import apidez.com.android_mvvm_sample.dependency.module.PurchaseModule;
import apidez.com.android_mvvm_sample.dependency.scope.ViewScope;
import apidez.com.android_mvvm_sample.view.activity.PurchaseActivity;
import dagger.Subcomponent;

/**
 * Created by nongdenchet on 10/24/15.
 */
@ViewScope
@Subcomponent(modules = {PurchaseModule.class})
public interface PurchaseComponent {
    void inject(PurchaseActivity purchaseActivity);
}
