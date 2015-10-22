package apidez.com.android_mvvm_sample.utils;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;

import apidez.com.android_mvvm_sample.application.DemoApplication;

/**
 * Created by nongdenchet on 10/22/15.
 */
public class ApplicationUtils {
    public static DemoApplication application() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        return (DemoApplication) instrumentation.getTargetContext().getApplicationContext();
    }
}
