package apidez.com.android_mvvm_sample.dependency.component;

import apidez.com.android_mvvm_sample.dependency.module.PlacesModule;
import apidez.com.android_mvvm_sample.dependency.module.StubPurchaseModule;
import dagger.Component;

/**
 * Created by nongdenchet on 10/2/15.
 */
@Component(modules = {PlacesModule.class, StubPurchaseModule.class})
public interface TestComponent extends ApplicationComponent {
}
