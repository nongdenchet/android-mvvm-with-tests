package apidez.com.android_mvvm_sample.utils;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.internal.widget.TintManager;
import android.view.View;

/**
 * Created by nongdenchet on 10/2/15.
 */
public class UiUtils {
    public static void resetTintColor(Context context, View view) {
        TintManager tintManager = TintManager.get(context);
        ViewCompat.setBackgroundTintList(view,
                tintManager.getTintList(android.support.design.R.drawable.abc_edit_text_material));
    }
}
