package apidez.com.android_mvvm_sample.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by nongdenchet on 10/2/15.
 */
public class ToastUtils {

    public static void showShortToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(Context context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(Context context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
