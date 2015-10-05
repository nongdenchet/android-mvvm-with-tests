package apidez.com.android_mvvm_sample.api;

import com.google.gson.Gson;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class ApiClient_Factory implements Factory<ApiClient> {
  private final Provider<Gson> gsonProvider;

  public ApiClient_Factory(Provider<Gson> gsonProvider) {  
    assert gsonProvider != null;
    this.gsonProvider = gsonProvider;
  }

  @Override
  public ApiClient get() {  
    return new ApiClient(gsonProvider.get());
  }

  public static Factory<ApiClient> create(Provider<Gson> gsonProvider) {  
    return new ApiClient_Factory(gsonProvider);
  }
}

