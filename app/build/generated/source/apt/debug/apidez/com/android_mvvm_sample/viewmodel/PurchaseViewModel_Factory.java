package apidez.com.android_mvvm_sample.viewmodel;

import apidez.com.android_mvvm_sample.api.ApiClient;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class PurchaseViewModel_Factory implements Factory<PurchaseViewModel> {
  private final Provider<ApiClient> apiClientProvider;

  public PurchaseViewModel_Factory(Provider<ApiClient> apiClientProvider) {  
    assert apiClientProvider != null;
    this.apiClientProvider = apiClientProvider;
  }

  @Override
  public PurchaseViewModel get() {  
    return new PurchaseViewModel(apiClientProvider.get());
  }

  public static Factory<PurchaseViewModel> create(Provider<ApiClient> apiClientProvider) {  
    return new PurchaseViewModel_Factory(apiClientProvider);
  }
}

