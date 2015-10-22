package apidez.com.android_mvvm_sample.dependency.module;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nongdenchet on 10/3/15.
 */
@Module
public class AppModule {
    @Singleton
    @Provides
    public Gson provideGson() {
        return new Gson();
    }
}
