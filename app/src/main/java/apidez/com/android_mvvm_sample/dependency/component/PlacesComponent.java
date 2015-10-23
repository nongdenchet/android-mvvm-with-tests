package apidez.com.android_mvvm_sample.dependency.component;

import apidez.com.android_mvvm_sample.dependency.module.PlacesModule;
import apidez.com.android_mvvm_sample.dependency.scope.ViewScope;
import apidez.com.android_mvvm_sample.view.fragment.PlacesFragment;
import dagger.Subcomponent;

/**
 * Created by nongdenchet on 10/24/15.
 */
@ViewScope
@Subcomponent(modules = {PlacesModule.class})
public interface PlacesComponent {
    void inject(PlacesFragment placesFragment);
}
