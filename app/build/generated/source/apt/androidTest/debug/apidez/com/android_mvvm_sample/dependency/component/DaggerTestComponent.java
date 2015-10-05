package apidez.com.android_mvvm_sample.dependency.component;

import apidez.com.android_mvvm_sample.dependency.module.StubPurchaseModule;
import apidez.com.android_mvvm_sample.dependency.module.StubPurchaseModule_ProvidePurchaseModuleFactory;
import apidez.com.android_mvvm_sample.view.activity.PurchaseActivity;
import apidez.com.android_mvvm_sample.view.activity.PurchaseActivity_MembersInjector;
import apidez.com.android_mvvm_sample.viewmodel.IPurchaseViewModel;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class DaggerTestComponent implements TestComponent {
  private Provider<IPurchaseViewModel> providePurchaseModuleProvider;
  private MembersInjector<PurchaseActivity> purchaseActivityMembersInjector;

  private DaggerTestComponent(Builder builder) {  
    assert builder != null;
    initialize(builder);
  }

  public static Builder builder() {  
    return new Builder();
  }

  public static TestComponent create() {  
    return builder().build();
  }

  private void initialize(final Builder builder) {  
    this.providePurchaseModuleProvider = StubPurchaseModule_ProvidePurchaseModuleFactory.create(builder.stubPurchaseModule);
    this.purchaseActivityMembersInjector = PurchaseActivity_MembersInjector.create((MembersInjector) MembersInjectors.noOp(), providePurchaseModuleProvider);
  }

  @Override
  public void inject(PurchaseActivity arg0) {  
    purchaseActivityMembersInjector.injectMembers(arg0);
  }

  public static final class Builder {
    private StubPurchaseModule stubPurchaseModule;
  
    private Builder() {  
    }
  
    public TestComponent build() {  
      if (stubPurchaseModule == null) {
        this.stubPurchaseModule = new StubPurchaseModule();
      }
      return new DaggerTestComponent(this);
    }
  
    public Builder stubPurchaseModule(StubPurchaseModule stubPurchaseModule) {  
      if (stubPurchaseModule == null) {
        throw new NullPointerException("stubPurchaseModule");
      }
      this.stubPurchaseModule = stubPurchaseModule;
      return this;
    }
  }
}

