package apidez.com.android_mvvm_sample.dependency.component;

import apidez.com.android_mvvm_sample.api.ApiClient;
import apidez.com.android_mvvm_sample.dependency.module.ApiModule;
import apidez.com.android_mvvm_sample.dependency.module.ApiModule_ProvideApiClientFactory;
import apidez.com.android_mvvm_sample.dependency.module.ApiModule_ProvideGsonFactory;
import apidez.com.android_mvvm_sample.dependency.module.PurchaseModule;
import apidez.com.android_mvvm_sample.dependency.module.PurchaseModule_ProvidePurchaseViewModelFactory;
import apidez.com.android_mvvm_sample.view.activity.PurchaseActivity;
import apidez.com.android_mvvm_sample.view.activity.PurchaseActivity_MembersInjector;
import apidez.com.android_mvvm_sample.viewmodel.IPurchaseViewModel;
import com.google.gson.Gson;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DaggerApplicationComponent implements ApplicationComponent {
  private Provider<Gson> provideGsonProvider;
  private Provider<ApiClient> provideApiClientProvider;
  private Provider<IPurchaseViewModel> providePurchaseViewModelProvider;
  private MembersInjector<PurchaseActivity> purchaseActivityMembersInjector;

  private DaggerApplicationComponent(Builder builder) {  
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {  
    return new Builder();
  }

  public static ApplicationComponent create() {  
    return builder().build();
  }

  private void initialize(final Builder builder) {  
    this.provideGsonProvider = ApiModule_ProvideGsonFactory.create(builder.apiModule);
    this.provideApiClientProvider = ScopedProvider.create(ApiModule_ProvideApiClientFactory.create(builder.apiModule, provideGsonProvider));
    this.providePurchaseViewModelProvider = PurchaseModule_ProvidePurchaseViewModelFactory.create(builder.purchaseModule, provideApiClientProvider);
    this.purchaseActivityMembersInjector = PurchaseActivity_MembersInjector.create((MembersInjector) MembersInjectors.noOp(), providePurchaseViewModelProvider);
  }

  @Override
  public void inject(PurchaseActivity purchaseActivity) {  
    purchaseActivityMembersInjector.injectMembers(purchaseActivity);
  }

  public static final class Builder {
    private ApiModule apiModule;
    private PurchaseModule purchaseModule;
  
    private Builder() {  
    }
  
    public ApplicationComponent build() {  
      if (apiModule == null) {
        this.apiModule = new ApiModule();
      }
      if (purchaseModule == null) {
        this.purchaseModule = new PurchaseModule();
      }
      return new DaggerApplicationComponent(this);
    }
  
    public Builder apiModule(ApiModule apiModule) {  
      if (apiModule == null) {
        throw new NullPointerException("apiModule");
      }
      this.apiModule = apiModule;
      return this;
    }
  
    public Builder purchaseModule(PurchaseModule purchaseModule) {  
      if (purchaseModule == null) {
        throw new NullPointerException("purchaseModule");
      }
      this.purchaseModule = purchaseModule;
      return this;
    }
  }
}

