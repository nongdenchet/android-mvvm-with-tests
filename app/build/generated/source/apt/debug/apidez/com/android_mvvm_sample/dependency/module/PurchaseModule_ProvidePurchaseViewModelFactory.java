package apidez.com.android_mvvm_sample.dependency.module;

import apidez.com.android_mvvm_sample.api.ApiClient;
import apidez.com.android_mvvm_sample.viewmodel.IPurchaseViewModel;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class PurchaseModule_ProvidePurchaseViewModelFactory implements Factory<IPurchaseViewModel> {
  private final PurchaseModule module;
  private final Provider<ApiClient> apiClientProvider;

  public PurchaseModule_ProvidePurchaseViewModelFactory(PurchaseModule module, Provider<ApiClient> apiClientProvider) {  
    assert module != null;
    this.module = module;
    assert apiClientProvider != null;
    this.apiClientProvider = apiClientProvider;
  }

  @Override
  public IPurchaseViewModel get() {  
    IPurchaseViewModel provided = module.providePurchaseViewModel(apiClientProvider.get());
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<IPurchaseViewModel> create(PurchaseModule module, Provider<ApiClient> apiClientProvider) {  
    return new PurchaseModule_ProvidePurchaseViewModelFactory(module, apiClientProvider);
  }
}

