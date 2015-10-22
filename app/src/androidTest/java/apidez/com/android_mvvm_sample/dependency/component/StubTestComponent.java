package apidez.com.android_mvvm_sample.dependency.component;

import apidez.com.android_mvvm_sample.dependency.module.StubPlacesModule;
import apidez.com.android_mvvm_sample.dependency.module.StubPurchaseModule;
import dagger.Component;

/**
 * Created by nongdenchet on 10/2/15.
 */
@Component(modules = {StubPlacesModule.class, StubPurchaseModule.class})
public interface StubTestComponent extends AppComponent {
}
