package apidez.com.android_mvvm_sample.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by nongdenchet on 10/3/15.
 */
public class MyTextView extends TextView {

    private int resId;

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setBackgroundResource(int resId) {
        super.setBackgroundResource(resId);
        this.resId = resId;
    }

    public int getBackgroundResource() {
        return resId;
    }
}
