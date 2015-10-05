package apidez.com.android_mvvm_sample.dependency.module;

import apidez.com.android_mvvm_sample.api.ApiClient;
import com.google.gson.Gson;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class ApiModule_ProvideApiClientFactory implements Factory<ApiClient> {
  private final ApiModule module;
  private final Provider<Gson> gsonProvider;

  public ApiModule_ProvideApiClientFactory(ApiModule module, Provider<Gson> gsonProvider) {  
    assert module != null;
    this.module = module;
    assert gsonProvider != null;
    this.gsonProvider = gsonProvider;
  }

  @Override
  public ApiClient get() {  
    ApiClient provided = module.provideApiClient(gsonProvider.get());
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<ApiClient> create(ApiModule module, Provider<Gson> gsonProvider) {  
    return new ApiModule_ProvideApiClientFactory(module, gsonProvider);
  }
}

