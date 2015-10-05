package apidez.com.android_mvvm_sample.dependency.module;

import com.google.gson.Gson;
import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class ApiModule_ProvideGsonFactory implements Factory<Gson> {
  private final ApiModule module;

  public ApiModule_ProvideGsonFactory(ApiModule module) {  
    assert module != null;
    this.module = module;
  }

  @Override
  public Gson get() {  
    Gson provided = module.provideGson();
    if (provided == null) {
      throw new NullPointerException("Cannot return null from a non-@Nullable @Provides method");
    }
    return provided;
  }

  public static Factory<Gson> create(ApiModule module) {  
    return new ApiModule_ProvideGsonFactory(module);
  }
}

