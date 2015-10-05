package apidez.com.android_mvvm_sample.view.activity;

import apidez.com.android_mvvm_sample.viewmodel.IPurchaseViewModel;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class PurchaseActivity_MembersInjector implements MembersInjector<PurchaseActivity> {
  private final MembersInjector<BaseActivity> supertypeInjector;
  private final Provider<IPurchaseViewModel> viewModelProvider;

  public PurchaseActivity_MembersInjector(MembersInjector<BaseActivity> supertypeInjector, Provider<IPurchaseViewModel> viewModelProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert viewModelProvider != null;
    this.viewModelProvider = viewModelProvider;
  }

  @Override
  public void injectMembers(PurchaseActivity instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.viewModel = viewModelProvider.get();
  }

  public static MembersInjector<PurchaseActivity> create(MembersInjector<BaseActivity> supertypeInjector, Provider<IPurchaseViewModel> viewModelProvider) {  
      return new PurchaseActivity_MembersInjector(supertypeInjector, viewModelProvider);
  }
}

