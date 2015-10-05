package apidez.com.android_mvvm_sample.dependency.module;

import apidez.com.android_mvvm_sample.viewmodel.IPurchaseViewModel;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class StubPurchaseModule_ProvidePurchaseModuleFactory implements Factory<IPurchaseViewModel> {
  private final StubPurchaseModule module;

  public StubPurchaseModule_ProvidePurchaseModuleFactory(StubPurchaseModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public IPurchaseViewModel get() {  
    IPurchaseViewModel provided = module.providePurchaseModule();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<IPurchaseViewModel> create(StubPurchaseModule module) {  
    return new StubPurchaseModule_ProvidePurchaseModuleFactory(module);
  }
}

