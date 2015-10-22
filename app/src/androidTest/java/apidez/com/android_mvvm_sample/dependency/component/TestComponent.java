package apidez.com.android_mvvm_sample.dependency.component;

import apidez.com.android_mvvm_sample.dependency.module.LocalPlacesModule;
import apidez.com.android_mvvm_sample.dependency.module.LocalPurchaseModule;
import dagger.Component;

/**
 * Created by nongdenchet on 10/22/15.
 */
@Component(modules = {LocalPlacesModule.class, LocalPurchaseModule.class})
public interface TestComponent extends AppComponent {
}
