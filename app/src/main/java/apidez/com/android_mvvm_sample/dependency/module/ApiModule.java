package apidez.com.android_mvvm_sample.dependency.module;

import com.google.gson.Gson;

import javax.inject.Singleton;

import apidez.com.android_mvvm_sample.api.ApiClient;
import dagger.Module;
import dagger.Provides;

/**
 * Created by nongdenchet on 10/3/15.
 */
@Module
public class ApiModule {
    @Provides
    public Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    public ApiClient provideApiClient(Gson gson) {
        return new ApiClient(gson);
    }
}
