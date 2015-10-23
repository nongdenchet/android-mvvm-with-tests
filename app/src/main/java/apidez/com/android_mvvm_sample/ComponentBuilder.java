package apidez.com.android_mvvm_sample;

import apidez.com.android_mvvm_sample.dependency.component.AppComponent;
import apidez.com.android_mvvm_sample.dependency.component.PlacesComponent;
import apidez.com.android_mvvm_sample.dependency.component.PurchaseComponent;
import apidez.com.android_mvvm_sample.dependency.module.PlacesModule;
import apidez.com.android_mvvm_sample.dependency.module.PurchaseModule;

/**
 * Created by nongdenchet on 10/24/15.
 */

/**
 * Use to build subcomponent
 */
public class ComponentBuilder {
    private AppComponent appComponent;

    public ComponentBuilder(AppComponent appComponent) {
        this.appComponent = appComponent;
    }

    public PlacesComponent placesComponent() {
        return appComponent.plus(new PlacesModule());
    }

    public PurchaseComponent purchaseComponent() {
        return appComponent.plus(new PurchaseModule());
    }
}
